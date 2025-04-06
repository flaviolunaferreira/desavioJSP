<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/views/partials/header.jsp" %>

<div class="container-fluid">
    <div class="row min-vh-100">
        <%@ include file="/WEB-INF/views/partials/project-sidebar.jsp" %>

        <main class="col-md-9 col-lg-10 px-md-4 py-3 bg-vscode-main">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom border-vscode-border">
                <h1 class="h2"><i class="bi bi-kanban"></i> <span id="projectName">Detalhes do Projeto</span></h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <button type="button" class="btn btn-sm btn-outline-vscode-blue"
                            data-bs-toggle="modal" data-bs-target="#crudModal"
                            data-type="projeto" data-action="editar" data-id="${param.id}">
                        <i class="bi bi-pencil"></i> Editar Projeto
                    </button>
                </div>
            </div>

            <!-- Informações do Projeto -->
            <div class="row mb-4">
                <div class="col-md-4">
                    <div class="card bg-vscode-card project-info-card">
                        <div class="card-body">
                            <h5 class="card-title">Status</h5>
                            <p class="card-text"><span id="projectStatus" class="badge bg-vscode-blue">Carregando...</span></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card bg-vscode-card project-info-card">
                        <div class="card-body">
                            <h5 class="card-title">Risco</h5>
                            <p class="card-text"><span id="projectRisk" class="badge bg-vscode-blue">Carregando...</span></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card bg-vscode-card project-info-card">
                        <div class="card-body">
                            <h5 class="card-title">Progresso</h5>
                            <div class="progress mt-2" style="height: 20px;">
                                <div id="projectProgress" class="progress-bar bg-vscode-blue" role="progressbar" style="width: 0">0%</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Detalhes do Projeto -->
            <div class="row mb-4">
                <div class="col-md-6">
                    <div class="card bg-vscode-card">
                        <div class="card-header bg-vscode-card-header">
                            <h5><i class="bi bi-info-circle"></i> Informações</h5>
                        </div>
                        <div class="card-body">
                            <p id="projectDescription">Carregando...</p>
                            <p><strong>Data de Início:</strong> <span id="projectStartDate">Carregando...</span></p>
                            <p><strong>Data Prevista de Término:</strong> <span id="projectEndDate">Carregando...</span></p>
                            <p><strong>Gerente:</strong> <span id="projectManager">Carregando...</span></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card bg-vscode-card">
                        <div class="card-header bg-vscode-card-header">
                            <h5><i class="bi bi-people-fill"></i> Membros</h5>
                        </div>
                        <div class="card-body">
                            <div id="projectMembers">
                                <p>Carregando membros...</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Quadro Kanban -->
            <div class="row">
                <div class="col-12">
                    <h4><i class="bi bi-kanban"></i> Quadro Kanban</h4>
                </div>

                <!-- Coluna Pendente -->
                <div class="col-md-3">
                    <div class="status-column" data-status="PENDENTE">
                        <h5 class="status-title text-center"><i class="bi bi-hourglass"></i> Pendente</h5>
                        <div id="pending-tasks" class="tasks-container"></div>
                    </div>
                </div>

                <!-- Coluna Em Andamento -->
                <div class="col-md-3">
                    <div class="status-column" data-status="EM_ANDAMENTO">
                        <h5 class="status-title text-center"><i class="bi bi-arrow-repeat"></i> Em Andamento</h5>
                        <div id="in-progress-tasks" class="tasks-container"></div>
                    </div>
                </div>

                <!-- Coluna Bloqueada -->
                <div class="col-md-3">
                    <div class="status-column" data-status="BLOQUEADA">
                        <h5 class="status-title text-center"><i class="bi bi-exclamation-octagon"></i> Bloqueada</h5>
                        <div id="blocked-tasks" class="tasks-container"></div>
                    </div>
                </div>

                <!-- Coluna Concluída -->
                <div class="col-md-3">
                    <div class="status-column" data-status="CONCLUIDA">
                        <h5 class="status-title text-center"><i class="bi bi-check-circle"></i> Concluída</h5>
                        <div id="completed-tasks" class="tasks-container"></div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<%@ include file="/WEB-INF/views/partials/modal.jsp" %>
<%@ include file="/WEB-INF/views/partials/footer.jsp" %>

<script>
    // Inicializa a seção de detalhes do projeto com o ID da URL
    $(document).ready(() => {
        // Pega o ID da URL formatada como "/projetos/1"
        const pathParts = window.location.pathname.split('/');
        const projectId = pathParts[pathParts.length - 1];

        if (projectId) {
            carregarDadosProjeto(projectId);
            window.projectDetails = new ProjectDetailsSection(projectId);
            window.projectDetails.init();
        } else {
            console.error('ID do projeto não encontrado na URL');
            window.location.href = '/';
        }
    });

    function carregarDadosProjeto(id) {
        $.get(`/api/projetos/${id}`)
            .done(projeto => {
                // Preenche os dados na página
                $('#projectName').text(projeto.nome);
                $('#projectDescription').text(projeto.descricao);
                $('#projectStatus').text(projeto.status);
                // ... preencha outros campos conforme necessário

                // Carrega membros
                carregarMembros(projectId);
                // Carrega tarefas
                carregarTarefas(projectId);
            })
            .fail(() => {
                alert('Erro ao carregar projeto');
                window.location.href = '/';
            });
    }

    function carregarMembros(projectId) {
        $.get(`/api/projetos/${projectId}/membros`)
            .done(membros => {
                const $container = $('#projectMembers').empty();
                membros.forEach(membro => {
                    $container.append(`
                        <div class="mb-2">
                            <span class="fw-bold">${membro.nome}</span>
                            <span class="badge bg-secondary ms-2">${membro.funcao}</span>
                        </div>
                    `);
                });
            });
    }

</script>