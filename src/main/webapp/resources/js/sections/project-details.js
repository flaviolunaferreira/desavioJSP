class ProjectDetailsSection {
    constructor(projectId) {
        this.projectId = projectId;
    }

    init() {
        this.loadProjectDetails();
        this.setupEventListeners();
    }

    async loadProjectDetails() {
        try {
            const project = await ApiService.get(`/api/projetos/${this.projectId}`);
            this.renderProjectDetails(project);
            this.loadProjectMembers();
            this.loadProjectTasks();
        } catch (error) {
            console.error('Erro ao carregar projeto:', error);
            Ui.showError('Erro ao carregar detalhes do projeto');
            window.location.href = '/';
        }
    }

    renderProjectDetails(project) {
        $('#projectName').text(project.nome);
        $('#projectDescription').text(project.descricao || 'Sem descrição');
        $('#projectStartDate').text(new Date(project.dataInicio).toLocaleDateString());
        $('#projectEndDate').text(new Date(project.dataPrevisaoFim).toLocaleDateString());
        $('#projectManager').text(project.gerente.nome);

        // Status
        const statusBadge = $('#projectStatus');
        statusBadge.text(project.status.replace('_', ' '))
            .removeClass('bg-vscode-blue bg-vscode-green bg-vscode-gray')
            .addClass(Ui.getStatusColorClass(project.status));

        // Risco
        const riskBadge = $('#projectRisk');
        riskBadge.text(project.risco || 'Não definido')
            .removeClass('bg-vscode-blue bg-vscode-green bg-vscode-red bg-vscode-yellow')
            .addClass(Ui.getRiskColorClass(project.risco));
    }

    async loadProjectMembers() {
        try {
            const members = await ApiService.get(`/api/membros/projeto/${this.projectId}`);
            this.renderProjectMembers(members);
        } catch (error) {
            console.error('Erro ao carregar membros:', error);
            Ui.showError('Erro ao carregar membros do projeto');
        }
    }

    renderProjectMembers(members) {
        const container = $('#projectMembers');
        container.empty();

        if (members.length === 0) {
            container.append('<p>Nenhum membro associado a este projeto</p>');
            return;
        }

        const list = $('<ul class="list-group"></ul>');

        members.forEach(member => {
            list.append(`
                <li class="list-group-item bg-vscode-card d-flex justify-content-between align-items-center">
                    ${member.pessoa.nome}
                    <span class="badge bg-vscode-blue">${member.funcao || 'Sem função'}</span>
                </li>
            `);
        });

        container.append(list);
    }

    async loadProjectTasks() {
        try {
            const tasks = await ApiService.get(`/api/tarefas?projetoId=${this.projectId}`);
            this.renderTasks(tasks);
            this.calculateProgress(tasks);
            this.initDragAndDrop();
        } catch (error) {
            console.error('Erro ao carregar tarefas:', error);
            Ui.showError('Erro ao carregar tarefas do projeto');
        }
    }

    renderTasks(tasks) {
        const containers = {
            'PENDENTE': $('#pending-tasks'),
            'EM_ANDAMENTO': $('#in-progress-tasks'),
            'BLOQUEADA': $('#blocked-tasks'),
            'CONCLUIDA': $('#completed-tasks')
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
                        <span class="badge bg-vscode-gray">${task.responsavel?.nome || 'N/A'}</span>
                        <div>
                            <button class="btn btn-sm btn-outline-vscode-blue" 
                                    onclick="app.formManager.openForm('tarefa', 'editar', ${task.id}, { projetoId: ${this.projectId} })">
                                <i class="bi bi-pencil"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }

    calculateProgress(tasks) {
        if (tasks.length === 0) {
            $('#projectProgress').css('width', '0%').text('0%');
            return;
        }

        const completed = tasks.filter(t => t.status === 'CONCLUIDA').length;
        const progress = Math.round((completed / tasks.length) * 100);
        $('#projectProgress').css('width', `${progress}%`).text(`${progress}%`);
    }

    initDragAndDrop() {
        $('.status-column').on('mousedown', '.task-card', function() {
            $(this).draggable({
                revert: 'invalid',
                cursor: 'move',
                opacity: 0.7,
                zIndex: 100,
                helper: 'clone'
            });
        });

        $('.status-column').droppable({
            accept: '.task-card',
            hoverClass: 'bg-vscode-selection',
            drop: (event, ui) => {
                const taskId = ui.draggable.data('id');
                const newStatus = $(event.target).closest('.status-column').data('status');
                this.updateTaskStatus(taskId, newStatus, ui.draggable);
            }
        });
    }

    async updateTaskStatus(taskId, newStatus, taskElement) {
        try {
            await ApiService.patch(`/api/tarefas/${taskId}/status`, { status: newStatus });

            // Move o cartão para a nova coluna
            const containerId = `${newStatus.toLowerCase().replace('_', '-')}-tasks`;
            $(`#${containerId}`).append(taskElement);

            // Atualiza o status no cartão
            taskElement.removeClass('bg-vscode-blue bg-vscode-green bg-vscode-red bg-vscode-gray')
                .addClass(Ui.getStatusColorClass(newStatus));

            // Recalcula o progresso
            this.loadProjectTasks();
        } catch (error) {
            console.error('Erro ao atualizar status:', error);
            Ui.showError('Erro ao atualizar status da tarefa');
        }
    }

    setupEventListeners() {
        $(document).on('formSaved', (e, data) => {
            if (data.type === 'tarefa' || data.type === 'membro') {
                this.loadProjectTasks();
            }
            if (data.type === 'projeto') {
                this.loadProjectDetails();
            }
        });
    }
}