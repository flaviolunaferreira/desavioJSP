<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/views/partials/header.jsp" %>

<div class="container-fluid">
    <div class="row min-vh-100">
        <%@ include file="/WEB-INF/views/partials/sidebar.jsp" %>

        <main class="col-md-9 col-lg-10 px-md-4 py-3 bg-vscode-main">
            <!-- Dashboard -->
            <div id="dashboard-section" class="section-content active">
                <!-- Gráficos -->
                <div class="row mb-4">
                    <div class="col-md-6">
                        <div class="chart-container">
                            <h5 class="text-center">Status dos Projetos</h5>
                            <canvas id="projectsStatusChart" height="250"></canvas>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="chart-container">
                            <h5 class="text-center">Distribuição de Riscos</h5>
                            <canvas id="projectsRiskChart" height="250"></canvas>
                        </div>
                    </div>
                </div>

                <!-- Quadro Kanban -->
                <div class="kanban-container">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4><i class="bi bi-kanban"></i> Quadro Kanban (Tarefas Recentes)</h4>
                        <button type="button" class="btn btn-sm btn-outline-vscode-blue"
                                data-bs-toggle="modal" data-bs-target="#crudModal"
                                data-type="tarefa" data-action="criar">
                            <i class="bi bi-plus-lg"></i> Nova Tarefa
                        </button>
                    </div>

                    <div class="row">
                        <div class="col-md-3">
                            <div class="kanban-column" data-status="PENDENTE">
                                <h5 class="kanban-title text-center"><i class="bi bi-hourglass"></i> Pendente</h5>
                                <div id="kanban-pending" class="kanban-tasks-container"></div>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="kanban-column" data-status="EM_ANDAMENTO">
                                <h5 class="kanban-title text-center"><i class="bi bi-arrow-repeat"></i> Em Andamento</h5>
                                <div id="kanban-in-progress" class="kanban-tasks-container"></div>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="kanban-column" data-status="BLOQUEADA">
                                <h5 class="kanban-title text-center"><i class="bi bi-exclamation-octagon"></i> Bloqueada</h5>
                                <div id="kanban-blocked" class="kanban-tasks-container"></div>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="kanban-column" data-status="CONCLUIDA">
                                <h5 class="kanban-title text-center"><i class="bi bi-check-circle"></i> Concluída</h5>
                                <div id="kanban-completed" class="kanban-tasks-container"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Seção de Projetos -->
            <div id="projects-section" class="section-content">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom border-vscode-border">
                    <h1 class="h2"><i class="bi bi-kanban"></i> Projetos</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <button type="button" class="btn btn-sm btn-outline-vscode-blue"
                                data-bs-toggle="modal" data-bs-target="#crudModal"
                                data-type="projeto" data-action="criar">
                            <i class="bi bi-plus-lg"></i> Novo Projeto
                        </button>
                    </div>
                </div>

                <!-- Lista de Projetos -->
                <div class="row" id="projects-container">
                    <c:forEach items="${listaProjetos}" var="projeto">
                        <div class="col-md-4 mb-4">
                            <div class="card bg-vscode-card project-card" data-id="${projeto.id}">
                                <div class="card-header bg-vscode-card-header">
                                    <h5 class="card-title mb-0">${projeto.nome}</h5>
                                </div>
                                <div class="card-body">
                                    <p class="card-text text-vscode-text">${projeto.descricao}</p>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <span class="badge ${projeto.status == 'EM_ANDAMENTO' ? 'bg-vscode-blue' :
                                                      projeto.status == 'CONCLUIDO' ? 'bg-vscode-green' :
                                                      'bg-vscode-gray'}">
                                            ${projeto.status.replace('_', ' ')}
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- Seção de Pessoas -->
            <div id="people-section" class="section-content">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom border-vscode-border">
                    <h1 class="h2"><i class="bi bi-people-fill"></i> Pessoas</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <button type="button" class="btn btn-sm btn-outline-vscode-blue"
                                data-bs-toggle="modal" data-bs-target="#crudModal"
                                data-type="pessoa" data-action="criar">
                            <i class="bi bi-plus-lg"></i> Nova Pessoa
                        </button>
                    </div>
                </div>

                <!-- Lista de Pessoas -->
                <div class="table-responsive">
                    <table class="table table-dark table-hover">
                        <thead>
                            <tr>
                                <th>Nome</th>
                                <th>CPF</th>
                                <th>Funcionário</th>
                                <th>Gerente</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody id="people-table-body"></tbody>
                    </table>
                </div>
            </div>

            <!-- Seção de Tarefas -->
            <div id="tasks-section" class="section-content">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom border-vscode-border">
                    <h1 class="h2"><i class="bi bi-list-task"></i> Tarefas</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <button type="button" class="btn btn-sm btn-outline-vscode-blue"
                                data-bs-toggle="modal" data-bs-target="#crudModal"
                                data-type="tarefa" data-action="criar">
                            <i class="bi bi-plus-lg"></i> Nova Tarefa
                        </button>
                    </div>
                </div>

                <!-- Filtros -->
                <div class="row mb-3">
                    <div class="col-md-6">
                        <select id="filter-projeto" class="form-select bg-vscode-card text-light">
                            <option value="">Todos os projetos</option>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <select id="filter-status" class="form-select bg-vscode-card text-light">
                            <option value="">Todos os status</option>
                            <option value="PENDENTE">Pendente</option>
                            <option value="EM_ANDAMENTO">Em Andamento</option>
                            <option value="BLOQUEADA">Bloqueada</option>
                            <option value="CONCLUIDA">Concluída</option>
                        </select>
                    </div>
                </div>

                <!-- Lista de Tarefas -->
                <div class="table-responsive">
                    <table class="table table-dark table-hover">
                        <thead>
                            <tr>
                                <th>Título</th>
                                <th>Projeto</th>
                                <th>Responsável</th>
                                <th>Status</th>
                                <th>Prazo</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody id="tasks-table-body"></tbody>
                    </table>
                </div>
            </div>

            <!-- Seção de Membros -->
            <div id="members-section" class="section-content">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom border-vscode-border">
                    <h1 class="h2"><i class="bi bi-person-plus"></i> Membros</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <button type="button" class="btn btn-sm btn-outline-vscode-blue"
                                data-bs-toggle="modal" data-bs-target="#crudModal"
                                data-type="membro" data-action="criar">
                            <i class="bi bi-plus-lg"></i> Novo Membro
                        </button>
                    </div>
                </div>

                <!-- Lista de Membros -->
                <div class="table-responsive">
                    <table class="table table-dark table-hover">
                        <thead>
                            <tr>
                                <th>Projeto</th>
                                <th>Pessoa</th>
                                <th>Função</th>
                                <th>Data de Entrada</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody id="members-table-body"></tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>
</div>

<%@ include file="/WEB-INF/views/partials/modal.jsp" %>
<%@ include file="/WEB-INF/views/partials/footer.jsp" %>