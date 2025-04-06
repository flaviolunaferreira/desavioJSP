class DashboardSection {
    constructor() {
        this.charts = {
            status: null,
            risk: null
        };
        this.dragInitialized = false;
    }

    init() {
        this.initCharts();
        this.loadKanbanTasks().then(r => console.log('r Kanban tasks loaded', r));
        this.setupEventListeners();
    }

    // Atualize o método que usa BAIXO/MEDIO/ALTO
    getRiskLevel(diasAtraso) {
        if (diasAtraso > 30) return 'ALTO';
        if (diasAtraso > 15) return 'MEDIO';
        return 'BAIXO';
    }

    initCharts() {
        const statusCtx = document.getElementById('projectsStatusChart');
        const riskCtx = document.getElementById('projectsRiskChart');

        if (statusCtx) {
            this.charts.status = new Chart(statusCtx, {
                type: 'doughnut',
                data: {
                    labels: ['Em Análise', 'Análise Realizada', 'Análise Aprovada', 'Iniciado',
                        'Planejado', 'Em Andamento', 'Encerrado', 'Cancelado'],
                    datasets: [{
                        data: [0, 0, 0, 0, 0, 0, 0, 0],
                        backgroundColor: [
                            '#569cd6', '#9cdcfe', '#4ec9b0', '#d7ba7d',
                            '#b5cea8', '#ce9178', '#608b4e', '#f44747'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: { position: 'right' }
                    }
                }
            });
        }

        if (riskCtx) {
            this.charts.risk = new Chart(riskCtx, {
                type: 'bar',
                data: {
                    labels: ['Baixo', 'Médio', 'Alto'],
                    datasets: [{
                        label: 'Projetos por Risco',
                        data: [0, 0, 0],
                        backgroundColor: ['#608b4e', '#dcdcaa', '#f44747'],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: { y: { beginAtZero: true } }
                }
            });
        }

        this.updateCharts();
    }

    async updateCharts() {
        try {
            const [statusData, riskData] = await Promise.all([
                ProjetoApi.countByStatus().catch(() => ({})),
                ProjetoApi.countByRisk().catch(() => ({}))
            ]);

            if (this.charts.status) {
                this.charts.status.data.datasets[0].data = [
                    statusData.PENDENTE || 0,
                    statusData.EM_ANDAMENTO || 0,
                    statusData.BLOQUEADA || 0,
                    statusData.CONCLUIDA || 0,
                ];
                this.charts.status.update();
            }

            if (this.charts.risk) {
                this.charts.risk.data.datasets[0].data = [
                    riskData.BAIXO || 0,
                    riskData.MEDIO || 0,
                    riskData.ALTO || 0
                ];
                this.charts.risk.update();
            }
        } catch (error) {
            console.error('Erro ao atualizar gráficos:', error);
            Ui.showError('Erro ao carregar dados dos gráficos');
        }
    }

    async loadKanbanTasks() {
        try {
            Ui.showLoading(true);
            const tasks = await ApiService.get('/api/tarefas/recentes?limit=20');

            if (!tasks || tasks.length === 0) {
                this.showNoTasksMessage();
                return;
            }

            this.renderKanbanTasks(tasks);

            // Inicializa o drag and drop APÓS renderizar as tarefas
            if (!this.dragInitialized) {
                this.initDragAndDrop();
                this.dragInitialized = true;
            }
        } catch (error) {
            console.error('Erro ao carregar tarefas:', error);
            Ui.showError('Erro ao carregar tarefas do dashboard');
        } finally {
            Ui.showLoading(false);
        }
    }

    showNoTasksMessage() {
        const container = $('#kanban-pending');
        container.html(`
            <div class="alert alert-info">
                Nenhuma tarefa encontrada. Crie uma nova tarefa para começar.
            </div>
        `);
    }

    renderKanbanTasks(tasks) {
        const containers = {
            'PENDENTE': $('#kanban-pending'),
            'EM_ANDAMENTO': $('#kanban-in-progress'),
            'BLOQUEADA': $('#kanban-blocked'),
            'CONCLUIDA': $('#kanban-completed')
        };

        // Limpa os containers
        Object.values(containers).forEach(container => container.empty());

        // Agrupa e renderiza as tarefas
        tasks.forEach(task => {
            if (containers[task.status]) {
                containers[task.status].append(this.createTaskCard(task));
            }
        });
    }

    createTaskCard(task) {
        const titulo = task.titulo || 'Tarefa sem título';
        const descricao = task.descricao || 'Sem descrição';
        const dataLimite = task.dataLimite ? new Date(task.dataLimite).toLocaleDateString() : 'Sem prazo';
        const projetoNome = (task.projeto && task.projeto.nome) ? task.projeto.nome : 'Sem projeto';
        const status = task.status || 'PENDENTE';

        return `
            <div class="task-card" data-id="${task.id}" data-status="${status}">
                <div class="task-card-header">
                    <h6 class="task-card-title">${titulo}</h6>
                    <span class="task-card-deadline">${dataLimite}</span>
                </div>
                <div class="task-card-body">
                    ${descricao}
                </div>
                <div class="task-card-footer">
                    <span class="task-card-project status-${status.toLowerCase()}">
                        ${projetoNome}
                    </span>
                    <div class="task-card-actions">
                        <button class="btn btn-sm btn-outline-vscode-blue btn-edit-task" 
                                data-id="${task.id}">
                            <i class="bi bi-pencil"></i>
                        </button>
                    </div>
                </div>
            </div>
        `;
    }

    initDragAndDrop() {
        // Verifica se jQuery UI está disponível
        if (typeof $.ui === 'undefined') {
            console.error('jQuery UI não está carregado');
            return;
        }

        // Configura colunas como áreas de soltar
        $('.kanban-column').droppable({
            accept: '.task-card',
            hoverClass: 'drop-hover',
            tolerance: 'pointer',
            drop: (event, ui) => {
                const taskCard = ui.draggable;
                const newStatus = $(event.target).closest('.kanban-column').data('status');
                const taskId = taskCard.data('id');

                this.updateTaskStatus(taskId, newStatus, taskCard);
            }
        });

        // Configura tarefas como arrastáveis
        $('.kanban-tasks-container').on('mousedown', '.task-card', function() {
            $(this).draggable({
                revert: 'invalid',
                cursor: 'move',
                opacity: 0.7,
                zIndex: 100,
                containment: '.kanban-container',
                helper: 'clone',
                start: function() {
                    $(this).addClass('dragging-active');
                },
                stop: function() {
                    $(this).removeClass('dragging-active');
                }
            });
        });

        console.log('Drag and Drop inicializado com sucesso');
    }

    async updateTaskStatus(taskId, newStatus, taskElement) {
        try {
            const validStatus = ['PENDENTE', 'EM_ANDAMENTO', 'BLOQUEADA', 'CONCLUIDA'];
            if (!validStatus.includes(newStatus)) {
                throw new Error('Status inválido');
            }

            await ApiService.patch(`/api/tarefas/${taskId}/status`, {
                novoStatus: newStatus,
                comentario: "Status alterado via drag-and-drop"
            });

            // Atualiza visualmente
            taskElement.attr('data-status', newStatus)
                .find('.task-card-project')
                .removeClass('status-pendente status-andamento status-bloqueada status-concluida')
                .addClass(`status-${newStatus.toLowerCase()}`);

            Ui.showSuccess(`Status atualizado para ${newStatus}`);
        } catch (error) {
            console.error('Erro ao atualizar status:', error);
            Ui.showError('Falha ao atualizar status');
            taskElement.effect('shake', { times: 3 }, 500);
        }
    }

    setupEventListeners() {
        $(document).on('click', '.btn-edit-task', (e) => {
            const taskId = $(e.currentTarget).data('id');
            window.app.formManager.openForm('tarefa', 'editar', taskId).then(r => console.log("problema de carregamento linha 256!"));
        });

        $(document).on('formSaved', (e, data) => {
            if (data.type === 'tarefa') {
                this.loadKanbanTasks();
            }
            if (data.type === 'projeto') {
                this.updateCharts().then(r => console.log("problema de carregamento linha 264!"));
            }
        });
    }
}