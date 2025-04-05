<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-3 col-lg-2 bg-vscode-sidebar p-0">
    <div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-3">
        <a href="${pageContext.request.contextPath}/" class="d-flex align-items-center pb-3 mb-md-0 me-md-auto text-decoration-none">
            <span class="fs-5 d-none d-sm-inline text-vscode-blue">
                <i class="bi bi-code-square"></i> Gestão de Projetos
            </span>
        </a>
        <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start w-100" id="projectMenu">
            <li class="nav-item w-100">
                <a href="${pageContext.request.contextPath}/" class="nav-link px-0 align-middle">
                    <i class="bi bi-kanban-fill"></i> <span class="ms-1 d-none d-sm-inline">Projetos</span>
                </a>
            </li>
            <li class="nav-item w-100">
                <a href="#" class="nav-link px-0 align-middle" data-bs-toggle="modal" data-bs-target="#crudModal"
                   data-type="tarefa" data-action="criar" data-projeto-id="${param.id}">
                    <i class="bi bi-plus-circle"></i> <span class="ms-1 d-none d-sm-inline">Nova Tarefa</span>
                </a>
            </li>
            <li class="nav-item w-100">
                <a href="#" class="nav-link px-0 align-middle" data-bs-toggle="modal" data-bs-target="#crudModal"
                   data-type="membro" data-action="criar" data-projeto-id="${param.id}">
                    <i class="bi bi-person-plus"></i> <span class="ms-1 d-none d-sm-inline">Adicionar Membro</span>
                </a>
            </li>
            <li class="nav-item w-100">
                <a href="#" class="nav-link px-0 align-middle" data-bs-toggle="modal" data-bs-target="#crudModal"
                   data-type="projeto" data-action="editar" data-id="${param.id}">
                    <i class="bi bi-pencil"></i> <span class="ms-1 d-none d-sm-inline">Editar Projeto</span>
                </a>
            </li>
        </ul>
    </div>
</div>