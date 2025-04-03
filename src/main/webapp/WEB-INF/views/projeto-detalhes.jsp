<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Projeto</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Consolas&display=swap" rel="stylesheet">
    <style>
        .status-column {
            min-height: 70vh;
            border-radius: 5px;
            padding: 15px;
            background-color: var(--vscode-card);
            border: 1px solid var(--vscode-border);
        }
        .task-card {
            cursor: move;
            margin-bottom: 15px;
            transition: all 0.3s ease;
        }
        .task-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.3);
        }
        .status-title {
            font-weight: bold;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid var(--vscode-border);
        }
    </style>
</head>
<body class="bg-dark text-light">
<div class="container-fluid">
    <div class="row min-vh-100">
        <!-- Sidebar (igual ao index.jsp) -->
        <div class="col-md-3 col-lg-2 bg-vscode-sidebar p-0">
            <div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-3">
                <a href="${pageContext.request.contextPath}/" class="d-flex align-items-center pb-3 mb-md-0 me-md-auto text-decoration-none">
                        <span class="fs-5 d-none d-sm-inline text-vscode-blue">
                            <i class="bi bi-code-square"></i> Gestão de Projetos
                        </span>
                </a>
                <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start w-100" id="menu">
                    <li class="nav-item w-100">
                        <a href="${pageContext.request.contextPath}/" class="nav-link px-0 align-middle">
                            <i class="bi bi-kanban-fill"></i> <span class="ms-1 d-none d-sm-inline">Projetos</span>
                        </a>
                    </li>
                    <li class="nav-item w-100">
                        <a href="#" class="nav-link px-0 align-middle" data-bs-toggle="modal" data-bs-target="#crudModal" data-type="tarefa" data-action="criar" data-projeto-id="${param.id}">
                            <i class="bi bi-plus-circle"></i> <span class="ms-1 d-none d-sm-inline">Nova Tarefa</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>

        <!-- Conteúdo Principal -->
        <main class="col-md-9 col-lg-10 px-md-4 py-3 bg-vscode-main">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom border-vscode-border">
                <h1 class="h2"><i class="bi bi-kanban"></i> <span id="projectName">Detalhes do Projeto</span></h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="btn-group me-2">
                        <button type="button" class="btn btn-sm btn-outline-vscode-blue" data-bs-toggle="modal" data-bs-target="#crudModal" data-type="projeto" data-action="editar" data-id="${param.id}">
                            <i class="bi bi-pencil"></i> Editar Projeto
                        </button>
                    </div>
                </div>
            </div>

            <!-- Informações do Projeto -->
            <div class="row mb-4">
                <div class="col-md-4">
                    <div class="card bg-vscode-card">
                        <div class="card-body">
                            <h5 class="card-title">Status</h5>
                            <p class="card-text"><span id="projectStatus" class="badge bg-vscode-blue">Carregando...</span></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card bg-vscode-card">
                        <div class="card-body">
                            <h5 class="card-title">Risco</h5>
                            <p class="card-text"><span id="projectRisk" class="badge bg-vscode-blue">Carregando...</span></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card bg-vscode-card">
                        <div class="card-body">
                            <h5 class="card-title">Progresso</h5>
                            <div class="progress mt-2" style="height: 20px;">
                                <div id="projectProgress" class="progress-bar bg-vscode-blue" role="progressbar" style="width: 0%">0%</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Quadro Kanban -->
            <div class="row">
                <!-- Coluna Pendente -->
                <div class="col-md-3">
                    <div class="status-column" data-status="PENDENTE">
                        <h5 class="status-title text-center"><i class="bi bi-hourglass"></i> Pendente</h5>
                        <div id="pending-tasks" class="tasks-container">
                            <!-- Tarefas serão carregadas via JavaScript -->
                        </div>
                    </div>
                </div>

                <!-- Coluna Em Andamento -->
                <div class="col-md-3">
                    <div class="status-column" data-status="EM_ANDAMENTO">
                        <h5 class="status-title text-center"><i class="bi bi-arrow-repeat"></i> Em Andamento</h5>
                        <div id="in-progress-tasks" class="tasks-container">
                            <!-- Tarefas serão carregadas via JavaScript -->
                        </div>
                    </div>
                </div>

                <!-- Coluna Bloqueada -->
                <div class="col-md-3">
                    <div class="status-column" data-status="BLOQUEADA">
                        <h5 class="status-title text-center"><i class="bi bi-exclamation-octagon"></i> Bloqueada</h5>
                        <div id="blocked-tasks" class="tasks-container">
                            <!-- Tarefas serão carregadas via JavaScript -->
                        </div>
                    </div>
                </div>

                <!-- Coluna Concluída -->
                <div class="col-md-3">
                    <div class="status-column" data-status="CONCLUIDA">
                        <h5 class="status-title text-center"><i class="bi bi-check-circle"></i> Concluída</h5>
                        <div id="completed-tasks" class="tasks-container">
                            <!-- Tarefas serão carregadas via JavaScript -->
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<!-- Modal CRUD (similar ao index.jsp) -->
<div class="modal fade" id="crudModal" tabindex="-1" aria-labelledby="crudModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content bg-vscode-card">
            <div class="modal-header border-vscode-border">
                <h5 class="modal-title text-vscode-blue" id="crudModalLabel"></h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="crudModalBody">
                <!-- O conteúdo será carregado dinamicamente via JavaScript -->
            </div>
            <div class="modal-footer border-vscode-border">
                <button type="button" class="btn btn-vscode-secondary" data-bs-dismiss="modal">Fechar</button>
                <button type="button" class="btn btn-vscode-blue" id="saveButton">Salvar</button>
            </div>
        </div>
    </div>
</div>

<!-- jQuery + jQuery UI (para drag and drop) -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.min.js"></script>

<!-- Bootstrap Bundle com Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- JavaScript Customizado -->
<script src="${pageContext.request.contextPath}/resources/js/projeto-detalhes.js"></script>
</body>
</html>