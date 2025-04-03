// app.js - Lógica principal da aplicação
$(document).ready(function() {
    // Variáveis globais
    let currentProjectId = null;

    // Inicialização
    initEventListeners();

    function initEventListeners() {
        // Evento de clique nos cards de projeto
        $(document).on('click', '.project-card', function() {
            const projectId = $(this).data('id');
            loadProjectDetails(projectId);
        });

        // Evento de clique no botão Salvar do modal
        $('#saveButton').click(handleSave);

        // Evento quando o modal é aberto
        $('#crudModal').on('show.bs.modal', function(event) {
            const button = $(event.relatedTarget);
            const type = button.data('type');
            const action = button.data('action') || 'criar';
            const id = button.data('id');

            loadCrudModal(type, action, id);
        });

        // Evento para mudança de status (arrastar cartão)
        initDragAndDrop();
    }

    function loadProjectDetails(projectId) {
        currentProjectId = projectId;

        // Requisição AJAX para buscar detalhes do projeto
        $.ajax({
            url: `/api/projetos/${projectId}`,
            method: 'GET',
            success: function(project) {
                // Redireciona para a página de detalhes ou carrega um modal
                window.location.href = `/projeto-detalhes.jsp?id=${projectId}`;
            },
            error: function(error) {
                showError('Erro ao carregar projeto');
                console.error(error);
            }
        });
    }

    function loadCrudModal(type, action, id) {
        // Define o título do modal
        const titles = {
            'projeto': 'Projeto',
            'pessoa': 'Pessoa',
            'tarefa': 'Tarefa',
            'membro': 'Membro'
        };

        $('#crudModalLabel').text(`${action === 'criar' ? 'Novo' : 'Editar'} ${titles[type]}`);

        // Carrega o formulário apropriado
        let url = `/api/${type}s`;
        if (action === 'editar' && id) {
            url += `/${id}`;
        }

        $.ajax({
            url: url,
            method: action === 'editar' ? 'GET' : 'OPTIONS',
            success: function(data) {
                let formHtml = '';

                switch(type) {
                    case 'projeto':
                        formHtml = buildProjetoForm(data, action);
                        break;
                    case 'pessoa':
                        formHtml = buildPessoaForm(data, action);
                        break;
                    case 'tarefa':
                        formHtml = buildTarefaForm(data, action);
                        break;
                    case 'membro':
                        formHtml = buildMembroForm(data, action);
                        break;
                }

                $('#crudModalBody').html(formHtml);

                // Se for edição, preenche os valores
                if (action === 'editar') {
                    populateForm(data, type);
                }
            },
            error: function(error) {
                showError(`Erro ao carregar formulário de ${type}`);
                console.error(error);
            }
        });
    }

    function buildProjetoForm(data, action) {
        return `
        <form id="projetoForm">
            <input type="hidden" name="id" id="projetoId">
            
            <div class="mb-3">
                <label for="nome" class="form-label">Nome *</label>
                <input type="text" class="form-control bg-vscode-card text-light" id="nome" name="nome" required>
            </div>
            
            <div class="mb-3">
                <label for="descricao" class="form-label">Descrição</label>
                <textarea class="form-control bg-vscode-card text-light" id="descricao" name="descricao" rows="3"></textarea>
            </div>
            
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="dataInicio" class="form-label">Data de Início *</label>
                    <input type="date" class="form-control bg-vscode-card text-light" id="dataInicio" name="dataInicio" required>
                </div>
                
                <div class="col-md-6 mb-3">
                    <label for="dataFim" class="form-label">Data de Término *</label>
                    <input type="date" class="form-control bg-vscode-card text-light" id="dataFim" name="dataFim" required>
                </div>
            </div>
            
            ${action === 'editar' ? `
            <div class="mb-3">
                <label for="status" class="form-label">Status *</label>
                <select class="form-select bg-vscode-card text-light" id="status" name="status" required>
                    ${Object.values(StatusProjeto).map(status => `
                        <option value="${status}">${status.replace('_', ' ')}</option>
                    `).join('')}
                </select>
            </div>
            
            <div class="mb-3">
                <label for="risco" class="form-label">Risco *</label>
                <select class="form-select bg-vscode-card text-light" id="risco" name="risco" required>
                    ${Object.values(RiscoProjeto).map(risco => `
                        <option value="${risco}">${risco}</option>
                    `).join('')}
                </select>
            </div>
            ` : ''}
            
            <div class="alert alert-danger d-none" id="formError"></div>
        </form>
    `;
    }

    function buildTarefaForm(data, action) {
        return `
            <form id="tarefaForm">
                <input type="hidden" name="id" id="tarefaId">
                
                <div class="mb-3">
                    <label for="nome" class="form-label">Nome</label>
                    <input type="text" class="form-control bg-vscode-card text-light" id="nome" name="nome" required>
                </div>
                
                <div class="mb-3">
                    <label for="descricao" class="form-label">Descrição</label>
                    <textarea class="form-control bg-vscode-card text-light" id="descricao" name="descricao" rows="3"></textarea>
                </div>
                
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="projetoId" class="form-label">Projeto</label>
                        <select class="form-select bg-vscode-card text-light" id="projetoId" name="projetoId" required>
                            <option value="">Selecione um projeto</option>
                            <!-- Opções serão preenchidas via AJAX -->
                        </select>
                    </div>
                    
                    <div class="col-md-6 mb-3">
                        <label for="responsavelId" class="form-label">Responsável</label>
                        <select class="form-select bg-vscode-card text-light" id="responsavelId" name="responsavelId" required>
                            <option value="">Selecione um responsável</option>
                            <!-- Opções serão preenchidas via AJAX -->
                        </select>
                    </div>
                </div>
                
                ${action === 'editar' ? `
                <div class="mb-3">
                    <label for="status" class="form-label">Status</label>
                    <select class="form-select bg-vscode-card text-light" id="status" name="status">
                        <option value="PENDENTE">Pendente</option>
                        <option value="EM_ANDAMENTO">Em Andamento</option>
                        <option value="BLOQUEADA">Bloqueada</option>
                        <option value="CONCLUIDA">Concluída</option>
                        <option value="CANCELADA">Cancelada</option>
                    </select>
                </div>
                ` : ''}
            </form>
        `;
    }

    function handleSave() {
        const modal = $('#crudModal');
        const type = modal.data('type');
        const action = modal.data('action');
        const id = modal.data('id');

        const formData = getFormData(type);
        if (!formData) return;

        // Adiciona projetoId às tarefas (se necessário)
        if (type === 'tarefa' && modal.data('projetoId')) {
            formData.projetoId = modal.data('projetoId');
        }

        $.ajax({
            url: `/api/${type}s${action === 'editar' ? `/${id}` : ''}`,
            method: action === 'criar' ? 'POST' : 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                showSuccess(`${type} ${action === 'criar' ? 'criado' : 'atualizado'} com sucesso!`);
                $('#crudModal').modal('hide');
                if (type === 'projeto') {
                    window.location.reload(); // Recarrega a página de projetos
                } else if (type === 'tarefa') {
                    loadProjectTasks(); // Atualiza apenas as tarefas (se estiver na página de detalhes)
                }
            },
            error: function(error) {
                let errorMsg = error.responseJSON?.message || `Erro ao ${action} ${type}`;
                $('#formError').removeClass('d-none').text(errorMsg);
            }
        });
    }

    function initDragAndDrop() {
        // Implementação do arrastar e soltar para mudar status
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

                updateTaskStatus(taskId, newStatus);
            }
        });
    }

    function updateTaskStatus(taskId, newStatus) {
        $.ajax({
            url: `/api/tarefas/${taskId}/status`,
            method: 'PATCH',
            contentType: 'application/json',
            data: JSON.stringify({ status: newStatus }),
            success: function(response) {
                showSuccess('Status da tarefa atualizado!');
                // Atualiza a UI sem recarregar a página
                $(`#task-${taskId}`).find('.task-status').text(newStatus.replace('_', ' '));
                $(`#task-${taskId}`).removeClass('bg-vscode-blue bg-vscode-green bg-vscode-gray')
                    .addClass(getStatusColorClass(newStatus));
            },
            error: function(error) {
                showError('Erro ao atualizar status da tarefa');
                console.error(error);
            }
        });
    }

    // Funções auxiliares
    function getFormData(type) {
        const form = $(`#${type}Form`);

        if (form.length === 0 || !form[0].checkValidity()) {
            form.addClass('was-validated');
            return null;
        }

        const formData = {};
        form.serializeArray().forEach(item => {
            formData[item.name] = item.value;
        });

        return formData;
    }

    function populateForm(data, type) {
        for (const key in data) {
            if (data.hasOwnProperty(key)) {
                const input = $(`#${type}Form #${key}`);
                if (input.length) {
                    if (input.is('select')) {
                        input.val(data[key]).trigger('change');
                    } else {
                        input.val(data[key]);
                    }
                }
            }
        }
    }

    function getStatusColorClass(status) {
        switch(status) {
            case 'EM_ANDAMENTO': return 'bg-vscode-blue';
            case 'CONCLUIDA': return 'bg-vscode-green';
            default: return 'bg-vscode-gray';
        }
    }

    function showSuccess(message) {
        // Implementação de notificação de sucesso
        console.log('Sucesso:', message);
    }

    function showError(message) {
        // Implementação de notificação de erro
        console.error('Erro:', message);
    }
});