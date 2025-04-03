<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Projetos</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Ícones Bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">

    <!-- CSS Customizado -->
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">

    <!-- Fonte do VS Code -->
    <link href="https://fonts.googleapis.com/css2?family=Consolas&display=swap" rel="stylesheet">
</head>
<body class="bg-dark text-light">
<div class="container-fluid">
    <div class="row min-vh-100">
        <!-- Sidebar -->
        <div class="col-md-3 col-lg-2 bg-vscode-sidebar p-0">
            <div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-3">
                <a href="${pageContext.request.contextPath}/" class="d-flex align-items-center pb-3 mb-md-0 me-md-auto text-decoration-none">
                        <span class="fs-5 d-none d-sm-inline text-vscode-blue">
                            <i class="bi bi-code-square"></i> Gestão de Projetos
                        </span>
                </a>
                <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start w-100" id="menu">
                    <li class="nav-item w-100">
                        <a href="${pageContext.request.contextPath}/" class="nav-link px-0 align-middle active">
                            <i class="bi bi-kanban-fill"></i> <span class="ms-1 d-none d-sm-inline">Projetos</span>
                        </a>
                    </li>
                    <li class="nav-item w-100">
                        <a href="#" class="nav-link px-0 align-middle" data-bs-toggle="modal" data-bs-target="#crudModal" data-type="projeto" data-action="criar">
                            <i class="bi bi-plus-circle"></i> <span class="ms-1 d-none d-sm-inline">Novo Projeto</span>
                        </a>
                    </li>
                    <li class="nav-item w-100">
                        <a href="#" class="nav-link px-0 align-middle" data-bs-toggle="modal" data-bs-target="#crudModal" data-type="pessoa" data-action="criar">
                            <i class="bi bi-person-plus"></i> <span class="ms-1 d-none d-sm-inline">Nova Pessoa</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>

        <!-- Conteúdo Principal -->
        <main class="col-md-9 col-lg-10 px-md-4 py-3 bg-vscode-main">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom border-vscode-border">
                <h1 class="h2"><i class="bi bi-kanban"></i> Projetos</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="btn-group me-2">
                        <button type="button" class="btn btn-sm btn-outline-vscode-blue" data-bs-toggle="modal" data-bs-target="#crudModal" data-type="tarefa" data-action="criar">
                            <i class="bi bi-plus-lg"></i> Nova Tarefa
                        </button>
                    </div>
                </div>
            </div>

            <!-- Lista de Projetos -->
            <div class="row">
                <c:forEach items="${listaProjetos}" var="projeto">
                    <div class="col-md-4 mb-4">
                        <div class="card bg-vscode-card project-card" data-id="${projeto.id}">
                            <div class="card-header bg-vscode-card-header">
                                <h5 class="card-title mb-0">${projeto.nome}</h5>
                            </div>
                            <div class="card-body">
                                <p class="card-text text-vscode-text">${projeto.descricao}</p>
                                <div class="d-flex justify-content-between align-items-center">
                                        <span class="badge bg-${projeto.status == 'EM_ANDAMENTO' ? 'vscode-blue' :
                                                              projeto.status == 'CONCLUIDO' ? 'vscode-green' :
                                                              'vscode-gray'}">
                                                ${projeto.status.replace('_', ' ')}
                                        </span>
                                    <a href="${pageContext.request.contextPath}/api/projetos/${projeto.id}"
                                       class="btn btn-sm btn-outline-vscode-blue">
                                        <i class="bi bi-arrow-right"></i> Detalhes
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </main>
    </div>
</div>

<!-- Modal CRUD -->
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

<!-- Bootstrap Bundle com Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- JavaScript Customizado -->
<script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
</body>
</html>