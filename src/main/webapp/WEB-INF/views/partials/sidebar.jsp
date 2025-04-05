<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-3 col-lg-2 bg-vscode-sidebar p-0">
    <div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-3">
        <a href="${pageContext.request.contextPath}/" class="d-flex align-items-center pb-3 mb-md-0 me-md-auto text-decoration-none">
            <span class="fs-5 d-none d-sm-inline text-vscode-blue">
                <i class="bi bi-code-square"></i> Gestão de Projetos
            </span>
        </a>
        <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start w-100" id="menu">
            <li class="nav-item w-100">
                <a href="#" class="nav-link px-0 align-middle section-link active" data-section="dashboard">
                    <i class="bi bi-speedometer2"></i> <span class="ms-1 d-none d-sm-inline">Dashboard</span>
                </a>
            </li>
            <li class="nav-item w-100">
                <a href="#" class="nav-link px-0 align-middle section-link" data-section="projects">
                    <i class="bi bi-kanban-fill"></i> <span class="ms-1 d-none d-sm-inline">Projetos</span>
                </a>
            </li>
            <li class="nav-item w-100">
                <a href="#" class="nav-link px-0 align-middle section-link" data-section="people">
                    <i class="bi bi-people-fill"></i> <span class="ms-1 d-none d-sm-inline">Pessoas</span>
                </a>
            </li>
            <li class="nav-item w-100">
                <a href="#" class="nav-link px-0 align-middle section-link" data-section="tasks">
                    <i class="bi bi-list-task"></i> <span class="ms-1 d-none d-sm-inline">Tarefas</span>
                </a>
            </li>
            <li class="nav-item w-100">
                <a href="#" class="nav-link px-0 align-middle section-link" data-section="members">
                    <i class="bi bi-person-plus"></i> <span class="ms-1 d-none d-sm-inline">Membros</span>
                </a>
            </li>
        </ul>
    </div>
</div>