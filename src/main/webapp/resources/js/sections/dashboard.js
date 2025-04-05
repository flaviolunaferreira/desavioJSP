class DashboardSection {
    constructor() {
        this.charts = {
            status: null,
            risk: null
        };
    }

    init() {
        this.initCharts();
        this.loadKanbanTasks().then(r => console.log('r Kanban tasks loaded', r));
        this.setupEventListeners();
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
                    statusData.ANALISE_REALIZADA || 0,
                    statusData.ANALISE_REALIZADA || 0,
                    // ... outros status
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
            const tasks = await ApiService.get('/api/tarefas/recentes');
            this.renderKanbanTasks(tasks);
        } catch (error) {
            console.error('Erro ao carregar tarefas do Kanban:', error);
            Ui.showError('Erro ao carregar tarefas');
        }
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
        return `
            <div class="card task-card mb-3 ${Ui.getStatusColorClass(task.status)}" 
                 id="task-${task.id}" data-id="${task.id}">
                <div class="card-header bg-vscode-card-header">
                    <h6 class="card-title mb-0">${task.titulo}</h6>
                </div>
                <div class="card-body">
                    <p class="card-text small">${task.descricao || 'Sem descrição'}</p>
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="badge bg-vscode-gray">
                            ${task.projeto?.nome || 'N/A'}
                        </span>
                        <div>
                            <button class="btn btn-sm btn-outline-vscode-blue" 
                                    onclick="app.formManager.openForm('tarefa', 'editar', ${task.id})">
                                <i class="bi bi-pencil"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }

    setupEventListeners() {
        $(document).on('formSaved', (e, data) => {
            if (data.type === 'tarefa') {
                this.loadKanbanTasks().then(r => console.log("problema de carregamento lina 152!"));
            }
            if (data.type === 'projeto') {
                this.updateCharts().then(r => console.log("problema de carregamento linha 155!"));
            }
        });
    }
}