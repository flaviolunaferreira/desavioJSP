// projeto-detalhes.js - Lógica específica para a página de detalhes
$(document).ready(function() {
    const projetoId = new URLSearchParams(window.location.search).get('id');

    // Carrega os dados do projeto
    function loadProjectDetails() {
        $.ajax({
            url: `/api/projetos/${projetoId}`,
            method: 'GET',
            success: function(projeto) {
                $('#projectName').text(projeto.nome);
                $('#projectStatus').text(projeto.status.replace('_', ' '))
                    .removeClass('bg-vscode-blue bg-vscode-green bg-vscode-gray')
                    .addClass(getStatusColorClass(projeto.status));
                $('#projectRisk').text(projeto.risco)
                    .removeClass('bg-vscode-blue bg-vscode-green bg-vscode-gray')
                    .addClass(getRiskColorClass(projeto.risco));

                loadProjectTasks();
            },
            error: function(error) {
                console.error('Erro ao carregar projeto:', error);
            }
        });
    }

    // Carrega as tarefas do projeto
    function loadProjectTasks() {
        $.ajax({
            url: `/api/tarefas?projetoId=${projetoId}`,
            method: 'GET',
            success: function(tarefas) {
                renderTasks(tarefas);
                calculateProgress(tarefas);
                initDragAndDrop();
            },
            error: function(error) {
                console.error('Erro ao carregar tarefas:', error);
            }
        });
    }

    // Renderiza as tarefas nas colunas apropriadas
    function renderTasks(tarefas) {
        // Limpa os containers
        $('.tasks-container').empty();

        // Agrupa tarefas por status
        const tasksByStatus = {
            'PENDENTE': [],
            'EM_ANDAMENTO': [],
            'BLOQUEADA': [],
            'CONCLUIDA': []
        };

        tarefas.forEach(tarefa => {
            tasksByStatus[tarefa.status]?.push(tarefa);
        });

        // Renderiza cada grupo
        for (const [status, tasks] of Object.entries(tasksByStatus)) {
            const containerId = `${status.toLowerCase().replace('_', '-')}-tasks`;
            const container = $(`#${containerId}`);

            tasks.forEach(tarefa => {
                container.append(createTaskCard(tarefa));
            });
        }
    }

    // Cria o HTML de um cartão de tarefa
    function createTaskCard(tarefa) {
        return `
            <div class="card task-card mb-3 ${getStatusColorClass(tarefa.status)}" 
                 id="task-${tarefa.id}" data-id="${tarefa.id}">
                <div class="card-header bg-vscode-card-header">
                    <h6 class="card-title mb-0">${tarefa.nome}</h6>
                </div>
                <div class="card-body">
                    <p class="card-text small">${tarefa.descricao || 'Sem descrição'}</p>
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="badge bg-vscode-gray task-status">
                            ${tarefa.status.replace('_', ' ')}
                        </span>
                        <div>
                            <button class="btn btn-sm btn-outline-vscode-blue" 
                                    onclick="editTask(${tarefa.id})">
                                <i class="bi bi-pencil"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }

    // Configura o drag and drop
    function initDragAndDrop() {
        $('.task-card').draggable({
            revert: 'invalid',
            cursor: 'move',
            opacity: 0.7,
            zIndex: 100,
            helper: 'clone'
        });

        $('.status-column').droppable({
            accept: '.task-card',
            hoverClass: 'bg-vscode-selection',
            drop: function(event, ui) {
                const taskId = ui.draggable.data('id');
                const newStatus = $(this).data('status');

                updateTaskStatus(taskId, newStatus, ui.draggable);
            }
        });
    }

    // Atualiza o status de uma tarefa
    function updateTaskStatus(taskId, newStatus, taskElement) {
        $.ajax({
            url: `/api/tarefas/${taskId}/status`,
            method: 'PATCH',
            contentType: 'application/json',
            data: JSON.stringify({ status: newStatus }),
            success: function(response) {
                // Move o cartão para a nova coluna
                const containerId = `${newStatus.toLowerCase().replace('_', '-')}-tasks`;
                $(`#${containerId}`).append(taskElement);

                // Atualiza o status no cartão
                taskElement.find('.task-status').text(newStatus.replace('_', ' '));

                // Atualiza a cor do cartão
                taskElement.removeClass('bg-vscode-blue bg-vscode-green bg-vscode-gray')
                    .addClass(getStatusColorClass(newStatus));

                // Recalcula o progresso
                loadProjectTasks();
            },
            error: function(error) {
                console.error('Erro ao atualizar status:', error);
            }
        });
    }

    // Calcula o progresso do projeto baseado nas tarefas concluídas
    function calculateProgress(tarefas) {
        if (tarefas.length === 0) {
            $('#projectProgress').css('width', '0%').text('0%');
            return;
        }

        const completed = tarefas.filter(t => t.status === 'CONCLUIDA').length;
        const progress = Math.round((completed / tarefas.length) * 100);

        $('#projectProgress').css('width', `${progress}%`).text(`${progress}%`);
    }

    // Funções auxiliares
    function getStatusColorClass(status) {
        switch(status) {
            case 'EM_ANDAMENTO': return 'bg-vscode-blue';
            case 'CONCLUIDA': return 'bg-vscode-green';
            case 'BLOQUEADA': return 'bg-vscode-red';
            default: return 'bg-vscode-gray';
        }
    }

    function getRiskColorClass(risco) {
        switch(risco) {
            case 'ALTO': return 'bg-vscode-red';
            case 'MEDIO': return 'bg-vscode-yellow';
            default: return 'bg-vscode-green';
        }
    }

    // Função global para edição de tarefa
    window.editTask = function(taskId) {
        $('#crudModal').modal('show')
            .data('type', 'tarefa')
            .data('action', 'editar')
            .data('id', taskId);
    };

    // Inicializa a página
    loadProjectDetails();
});