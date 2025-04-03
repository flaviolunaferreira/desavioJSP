<jsp:useBean id="projeto" scope="request" type="com.portfolio.dto.projeto.ProjetoResponseDTO"/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciamento de Projetos</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Estilo personalizado com cores do VS Code -->
    <style>
        :root {
            --vs-code-bg: #1e1e1e;
            --vs-code-text: #d4d4d4;
            --vs-code-blue: #569cd6;
            --vs-code-green: #6a9955;
            --vs-code-purple: #c586c0;
            --vs-code-orange: #ce9178;
            --vs-code-yellow: #dcdcaa;
            --vs-code-red: #f44747;
            --vs-code-gray: #858585;
        }

        body {
            background-color: var(--vs-code-bg);
            color: var(--vs-code-text);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .navbar {
            background-color: #252526;
            border-bottom: 1px solid #3c3c3c;
        }

        .card {
            background-color: #252526;
            border: 1px solid #3c3c3c;
            border-radius: 5px;
        }

        .card-header {
            background-color: #2d2d2d;
            border-bottom: 1px solid #3c3c3c;
            color: var(--vs-code-blue);
        }

        .table {
            color: var(--vs-code-text);
            margin-bottom: 0;
        }

        .table th {
            background-color: #2d2d2d;
            border-bottom: 1px solid #3c3c3c;
            color: var(--vs-code-blue);
        }

        .table td {
            border-bottom: 1px solid #3c3c3c;
            vertical-align: middle;
        }

        .table-hover tbody tr:hover {
            background-color: #2a2d2e;
        }

        .btn-primary {
            background-color: var(--vs-code-blue);
            border-color: var(--vs-code-blue);
        }

        .btn-danger {
            background-color: var(--vs-code-red);
            border-color: var(--vs-code-red);
        }

        .btn-success {
            background-color: var(--vs-code-green);
            border-color: var(--vs-code-green);
        }

        .btn-warning {
            background-color: var(--vs-code-orange);
            border-color: var(--vs-code-orange);
            color: white;
        }

        .form-control, .form-select {
            background-color: #3c3c3c;
            border: 1px solid #3c3c3c;
            color: var(--vs-code-text);
        }

        .form-control:focus, .form-select:focus {
            background-color: #3c3c3c;
            color: var(--vs-code-text);
            border-color: var(--vs-code-blue);
            box-shadow: 0 0 0 0.25rem rgba(86, 156, 214, 0.25);
        }

        .badge {
            font-weight: normal;
            padding: 5px 8px;
        }

        .status-em-analise {
            background-color: #007bff;
        }

        .status-analise-realizada {
            background-color: #17a2b8;
        }

        .status-analise-aprovada {
            background-color: #6c757d;
        }

        .status-iniciado {
            background-color: #28a745;
        }

        .status-planejado {
            background-color: #ffc107;
            color: #212529;
        }

        .status-em-andamento {
            background-color: #fd7e14;
        }

        .status-encerrado {
            background-color: #6f42c1;
        }

        .status-cancelado {
            background-color: #dc3545;
        }

        .risco-baixo {
            background-color: var(--vs-code-green);
        }

        .risco-medio {
            background-color: var(--vs-code-yellow);
            color: #212529;
        }

        .risco-alto {
            background-color: var(--vs-code-red);
        }

        .modal-content {
            background-color: #252526;
            border: 1px solid #3c3c3c;
        }

        .modal-header {
            border-bottom: 1px solid #3c3c3c;
        }

        .modal-footer {
            border-top: 1px solid #3c3c3c;
        }

        .nav-tabs .nav-link {
            color: var(--vs-code-text);
            border: 1px solid transparent;
        }

        .nav-tabs .nav-link:hover {
            border-color: #3c3c3c;
        }

        .nav-tabs .nav-link.active {
            background-color: #2d2d2d;
            border-color: #3c3c3c;
            color: var(--vs-code-blue);
        }

        .tab-content {
            background-color: #2d2d2d;
            border: 1px solid #3c3c3c;
            border-top: none;
            padding: 15px;
        }
    </style>
</head>
<body>
<!-- Barra de navegação -->
<nav class="navbar navbar-expand-lg navbar-dark mb-4">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <i class="fas fa-project-diagram me-2"></i>Gerenciamento de Projetos
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="#"><i class="fas fa-home me-1"></i> Projetos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><i class="fas fa-users me-1"></i> Pessoas</a>
                </li>
            </ul>
            <form class="d-flex">
                <input class="form-control me-2" type="search" placeholder="Pesquisar...">
                <button class="btn btn-outline-light" type="submit"><i class="fas fa-search"></i></button>
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row mb-4">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0"><i class="fas fa-filter me-2"></i>Filtrar Projetos</h5>
                    <button class="btn btn-sm btn-primary" data-bs-toggle="collapse" data-bs-target="#filtroCollapse">
                        <i class="fas fa-chevron-down"></i>
                    </button>
                </div>
                <div class="collapse show" id="filtroCollapse">
                    <div class="card-body">
                        <form id="filtroForm">
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="mb-3">
                                        <label for="filtroNome" class="form-label">Nome</label>
                                        <input type="text" class="form-control" id="filtroNome" placeholder="Nome do projeto">
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <div class="mb-3">
                                        <label for="filtroStatus" class="form-label">Status</label>
                                        <select class="form-select" id="filtroStatus">
                                            <option value="">Todos</option>
                                            <option value="EM_ANALISE">Em análise</option>
                                            <option value="ANALISE_REALIZADA">Análise realizada</option>
                                            <option value="ANALISE_APROVADA">Análise aprovada</option>
                                            <option value="INICIADO">Iniciado</option>
                                            <option value="PLANEJADO">Planejado</option>
                                            <option value="EM_ANDAMENTO">Em andamento</option>
                                            <option value="ENCERRADO">Encerrado</option>
                                            <option value="CANCELADO">Cancelado</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <div class="mb-3">
                                        <label for="filtroDataInicio" class="form-label">Data Início</label>
                                        <input type="date" class="form-control" id="filtroDataInicio">
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <div class="mb-3">
                                        <label for="filtroDataFim" class="form-label">Data Fim</label>
                                        <input type="date" class="form-control" id="filtroDataFim">
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="mb-3">
                                        <label for="filtroGerente" class="form-label">Gerente</label>
                                        <select class="form-select" id="filtroGerente">
                                            <option value="">Todos</option>
                                            <!-- Opções serão preenchidas via AJAX -->
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 text-end">
                                    <button type="button" class="btn btn-secondary me-2" id="btnLimparFiltros">
                                        <i class="fas fa-broom me-1"></i>Limpar
                                    </button>
                                    <button type="button" class="btn btn-primary" id="btnAplicarFiltros">
                                        <i class="fas fa-filter me-1"></i>Aplicar Filtros
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0"><i class="fas fa-list me-2"></i>Lista de Projetos</h5>
                    <button class="btn btn-success btn-sm" id="btnNovoProjeto">
                        <i class="fas fa-plus me-1"></i>Novo Projeto
                    </button>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover" id="tabelaProjetos">
                            <thead>
                            <tr>
                                <th width="5%">ID</th>
                                <th width="20%">Nome</th>
                                <th width="10%">Status</th>
                                <th width="10%">Risco</th>
                                <th width="15%">Gerente</th>
                                <th width="10%">Início</th>
                                <th width="10%">Previsão</th>
                                <th width="10%">Término</th>
                                <th width="10%">Ações</th>
                            </tr>
                            </thead>
                            <tbody>
                            <!-- Dados serão preenchidos via AJAX -->
                            <tr>
                                <td colspan="9" class="text-center">Carregando projetos...</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <nav aria-label="Navegação de páginas" class="mt-3">
                        <ul class="pagination justify-content-center" id="paginacao">
                            <!-- Paginação será preenchida via AJAX -->
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal para adicionar/editar projeto -->
<div class="modal fade" id="projetoModal" tabindex="-1" aria-labelledby="projetoModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="projetoModalLabel">Novo Projeto</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="projetoForm">
                    <input type="hidden" id="projetoId">

                    <ul class="nav nav-tabs" id="projetoTabs" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link active" id="dados-tab" data-bs-toggle="tab" data-bs-target="#dados" type="button" role="tab">Dados Básicos</button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="membros-tab" data-bs-toggle="tab" data-bs-target="#membros" type="button" role="tab">Membros</button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" id="tarefas-tab" data-bs-toggle="tab" data-bs-target="#tarefas" type="button" role="tab">Tarefas</button>
                        </li>
                    </ul>

                    <div class="tab-content" id="projetoTabContent">
                        <!-- Aba Dados Básicos -->
                        <div class="tab-pane fade show active" id="dados" role="tabpanel">
                            <div class="row mt-3">
                                <div class="col-md-8">
                                    <div class="mb-3">
                                        <label for="nome" class="form-label">Nome do Projeto *</label>
                                        <input type="text" class="form-control" id="nome" required maxlength="100">
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="mb-3">
                                        <label for="status" class="form-label">Status *</label>
                                        <select class="form-select" id="status" required>
                                            <option value="">Selecione...</option>
                                            <option value="EM_ANALISE">Em análise</option>
                                            <option value="ANALISE_REALIZADA">Análise realizada</option>
                                            <option value="ANALISE_APROVADA">Análise aprovada</option>
                                            <option value="INICIADO">Iniciado</option>
                                            <option value="PLANEJADO">Planejado</option>
                                            <option value="EM_ANDAMENTO">Em andamento</option>
                                            <option value="ENCERRADO">Encerrado</option>
                                            <option value="CANCELADO">Cancelado</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4">
                                    <div class="mb-3">
                                        <label for="dataInicio" class="form-label">Data de Início *</label>
                                        <input type="date" class="form-control" id="dataInicio" required>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="mb-3">
                                        <label for="dataPrevisaoFim" class="form-label">Previsão de Término *</label>
                                        <input type="date" class="form-control" id="dataPrevisaoFim" required>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="mb-3">
                                        <label for="dataFim" class="form-label">Data Real de Término</label>
                                        <input type="date" class="form-control" id="dataFim">
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="gerente" class="form-label">Gerente Responsável *</label>
                                        <select class="form-select" id="gerente" required>
                                            <option value="">Selecione...</option>
                                            <!-- Opções serão preenchidas via AJAX -->
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="orcamento" class="form-label">Orçamento Total (R$)</label>
                                        <input type="number" step="0.01" min="0" class="form-control" id="orcamento">
                                    </div>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="descricao" class="form-label">Descrição</label>
                                <textarea class="form-control" id="descricao" rows="3" maxlength="500"></textarea>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label class="form-label">Risco</label>
                                        <div class="form-control bg-transparent border-0">
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="risco" id="riscoBaixo" value="BAIXO">
                                                <label class="form-check-label" for="riscoBaixo">Baixo</label>
                                            </div>
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="risco" id="riscoMedio" value="MEDIO">
                                                <label class="form-check-label" for="riscoMedio">Médio</label>
                                            </div>
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="risco" id="riscoAlto" value="ALTO">
                                                <label class="form-check-label" for="riscoAlto">Alto</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 text-end">
                                    <button type="button" class="btn btn-warning mt-3" id="btnCalcularRisco">
                                        <i class="fas fa-calculator me-1"></i>Calcular Risco
                                    </button>
                                </div>
                            </div>
                        </div>

                        <!-- Aba Membros -->
                        <div class="tab-pane fade" id="membros" role="tabpanel">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h6>Membros do Projeto</h6>
                                <button type="button" class="btn btn-sm btn-success" id="btnAdicionarMembro">
                                    <i class="fas fa-plus me-1"></i>Adicionar Membro
                                </button>
                            </div>

                            <div class="table-responsive">
                                <table class="table table-sm table-hover" id="tabelaMembros">
                                    <thead>
                                    <tr>
                                        <th>Nome</th>
                                        <th>Cargo</th>
                                        <th>Data Entrada</th>
                                        <th>Função</th>
                                        <th>Ações</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <!-- Dados serão preenchidos via AJAX quando o projeto for carregado -->
                                    <tr>
                                        <td colspan="5" class="text-center">Nenhum membro associado</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <!-- Aba Tarefas -->
                        <div class="tab-pane fade" id="tarefas" role="tabpanel">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h6>Tarefas do Projeto</h6>
                                <button type="button" class="btn btn-sm btn-success" id="btnAdicionarTarefa">
                                    <i class="fas fa-plus me-1"></i>Adicionar Tarefa
                                </button>
                            </div>

                            <div class="table-responsive">
                                <table class="table table-sm table-hover" id="tabelaTarefas">
                                    <thead>
                                    <tr>
                                        <th>Título</th>
                                        <th>Responsável</th>
                                        <th>Status</th>
                                        <th>Data Limite</th>
                                        <th>Ações</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <!-- Dados serão preenchidos via AJAX quando o projeto for carregado -->
                                    <tr>
                                        <td colspan="5" class="text-center">Nenhuma tarefa cadastrada</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-primary" id="btnSalvarProjeto">Salvar Projeto</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal para adicionar/editar membro -->
<div class="modal fade" id="membroModal" tabindex="-1" aria-labelledby="membroModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="membroModalLabel">Adicionar Membro ao Projeto</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="membroForm">
                    <input type="hidden" id="projetoIdMembro">
                    <input type="hidden" id="membroId">

                    <div class="mb-3">
                        <label for="pessoa" class="form-label">Pessoa *</label>
                        <select class="form-select" id="pessoa" required>
                            <option value="">Selecione...</option>
                            <!-- Opções serão preenchidas via AJAX -->
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="dataEntrada" class="form-label">Data de Entrada *</label>
                        <input type="date" class="form-control" id="dataEntrada" required>
                    </div>

                    <div class="mb-3">
                        <label for="funcao" class="form-label">Função</label>
                        <input type="text" class="form-control" id="funcao" maxlength="50">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-primary" id="btnSalvarMembro">Salvar Membro</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal para adicionar/editar tarefa -->
<div class="modal fade" id="tarefaModal" tabindex="-1" aria-labelledby="tarefaModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="tarefaModalLabel">Adicionar Tarefa ao Projeto</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="tarefaForm">
                    <input type="hidden" id="projetoIdTarefa">
                    <input type="hidden" id="tarefaId">

                    <div class="mb-3">
                        <label for="titulo" class="form-label">Título *</label>
                        <input type="text" class="form-control" id="titulo" required maxlength="100">
                    </div>

                    <div class="mb-3">
                        <label for="descricaoTarefa" class="form-label">Descrição</label>
                        <textarea class="form-control" id="descricaoTarefa" rows="3" maxlength="500"></textarea>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label for="responsavel" class="form-label">Responsável</label>
                                <select class="form-select" id="responsavel">
                                    <option value="">Selecione...</option>
                                    <!-- Opções serão preenchidas via AJAX -->
                                </select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label for="statusTarefa" class="form-label">Status *</label>
                                <select class="form-select" id="statusTarefa" required>
                                    <option value="">Selecione...</option>
                                    <option value="PENDENTE">Pendente</option>
                                    <option value="EM_ANDAMENTO">Em andamento</option>
                                    <option value="CONCLUIDA">Concluída</option>
                                    <option value="CANCELADA">Cancelada</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="dataLimite" class="form-label">Data Limite</label>
                        <input type="datetime-local" class="form-control" id="dataLimite">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-primary" id="btnSalvarTarefa">Salvar Tarefa</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal para confirmar exclusão -->
<div class="modal fade" id="confirmarExclusaoModal" tabindex="-1" aria-labelledby="confirmarExclusaoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmarExclusaoModalLabel">Confirmar Exclusão</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p id="mensagemExclusao">Tem certeza que deseja excluir este item?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-danger" id="btnConfirmarExclusao">Confirmar Exclusão</button>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Scripts personalizados -->
<script>
    $(document).ready(function() {
        // Variáveis globais
        let projetoAtual = null;
        let itemParaExcluir = null;
        let tipoItemParaExcluir = '';

        // Inicializar a página
        carregarGerentes();
        carregarProjetos();

        // Eventos dos botões
        $('#btnNovoProjeto').click(function() {
            abrirModalNovoProjeto();
        });

        $('#btnSalvarProjeto').click(salvarProjeto);
        $('#btnCalcularRisco').click(calcularRisco);
        $('#btnAdicionarMembro').click(abrirModalNovoMembro);
        $('#btnSalvarMembro').click(salvarMembro);
        $('#btnAdicionarTarefa').click(abrirModalNovaTarefa);
        $('#btnSalvarTarefa').click(salvarTarefa);
        $('#btnAplicarFiltros').click(aplicarFiltros);
        $('#btnLimparFiltros').click(limparFiltros);
        $('#btnConfirmarExclusao').click(confirmarExclusao);

        // Funções para carregar dados
        function carregarGerentes() {
            $.ajax({
                url: '/api/pessoas/gerentes',
                method: 'GET',
                success: function(data) {
                    let options = '<option value="">Todos</option>';
                    data.forEach(function(gerente) {
                        options += `<option value="${gerente.id}">${gerente.nome}</option>`;
                    });
                    $('#filtroGerente').html(options);
                    $('#gerente').html(options.replace('Todos', 'Selecione...'));
                }
            });
        }

        function carregarProjetos(filtros = {}) {
            $.ajax({
                url: '/api/projetos',
                method: 'GET',
                data: filtros,
                success: function(data) {
                    let rows = '';
                    if (data.length > 0) {
                        data.forEach(function(projeto) {
                            rows += `
                                    <tr>
                                        <td>${projeto.id}</td>
                                        <td>${projeto.nome}</td>
                                        <td><span class="badge status-${projeto.status.toLowerCase().replace('_', '-')}">${formatarStatus(projeto.status)}</span></td>
                                        <td><span class="badge risco-${projeto.risco ? projeto.risco.toLowerCase() : 'baixo'}">${projeto.risco ? projeto.risco.charAt(0) + projeto.risco.slice(1).toLowerCase() : 'Baixo'}</span></td>
                                        <td>${projeto.gerente ? projeto.gerente.nome : 'Não definido'}</td>
                                        <td>${formatarData(projeto.dataInicio)}</td>
                                        <td>${formatarData(projeto.dataPrevisaoFim)}</td>
                                        <td>${projeto.dataFim ? formatarData(projeto.dataFim) : '-'}</td>
                                        <td>
                                            <button class="btn btn-sm btn-primary me-1 btn-editar" data-id="${projeto.id}" title="Editar">
                                                <i class="fas fa-edit"></i>
                                            </button>
                                            <button class="btn btn-sm btn-danger btn-excluir" data-id="${projeto.id}" title="Excluir" ${projeto.status == 'INICIADO' || projeto.status == 'EM_ANDAMENTO' || projeto.status == 'ENCERRADO' ? 'disabled' : ''}>
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </td>
                                    </tr>
                                `;
                        });
                    } else {
                        rows = '<tr><td colspan="9" class="text-center">Nenhum projeto encontrado</td></tr>';
                    }
                    $('#tabelaProjetos tbody').html(rows);

                    // Adicionar eventos aos botões de edição e exclusão
                    $('.btn-editar').click(function() {
                        const projetoId = $(this).data('id');
                        carregarProjeto(projetoId);
                    });

                    $('.btn-excluir').click(function() {
                        const projetoId = $(this).data('id');
                        prepararExclusao(projetoId, 'projeto', `Deseja realmente excluir o projeto ${projetoId}?`);
                    });
                }
            });
        }

        function carregarProjeto(id) {
            $.ajax({
                url: `/api/projetos/${id}`,
                method: 'GET',
                success: function(data) {
                    projetoAtual = data;

                    // Preencher o modal com os dados do projeto
                    $('#projetoId').val(data.id);
                    $('#nome').val(data.nome);
                    $('#status').val(data.status);
                    $('#dataInicio').val(data.dataInicio);
                    $('#dataPrevisaoFim').val(data.dataPrevisaoFim);
                    $('#dataFim').val(data.dataFim || '');
                    $('#descricao').val(data.descricao || '');
                    $('#orcamento').val(data.orcamento || '');
                    $('#gerente').val(data.gerente ? data.gerente.id : '');

                    // Selecionar o risco
                    $('input[name="risco"]').prop('checked', false);
                    if (data.risco) {
                        $(`#risco${data.risco.charAt(0) + data.risco.slice(1).toLowerCase()}`).prop('checked', true);
                    }

                    // Atualizar título do modal
                    $('#projetoModalLabel').text(`Editar Projeto #${data.id}`);

                    // Carregar membros e tarefas
                    carregarMembrosDoProjeto(id);
                    carregarTarefasDoProjeto(id);

                    // Mostrar o modal
                    const projetoModal = new bootstrap.Modal(document.getElementById('projetoModal'));
                    projetoModal.show();
                }
            });
        }

        function carregarMembrosDoProjeto(projetoId) {
            $.ajax({
                url: `/api/membros/projeto/${projetoId}`,
                method: 'GET',
                success: function(data) {
                    let rows = '';
                    if (data.length > 0) {
                        data.forEach(function(membro) {
                            rows += `
                                    <tr>
                                        <td>${membro.pessoa.nome}</td>
                                        <td>${membro.pessoa.cargo}</td>
                                        <td>${formatarData(membro.dataEntrada)}</td>
                                        <td>${membro.funcao || '-'}</td>
                                        <td>
                                            <button class="btn btn-sm btn-primary me-1 btn-editar-membro"
                                                    data-projeto-id="${projetoId}"
                                                    data-pessoa-id="${membro.pessoa.id}"
                                                    title="Editar">
                                                <i class="fas fa-edit"></i>
                                            </button>
                                            <button class="btn btn-sm btn-danger btn-excluir-membro"
                                                    data-projeto-id="${projetoId}"
                                                    data-pessoa-id="${membro.pessoa.id}"
                                                    title="Excluir">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </td>
                                    </tr>
                                `;
                        });
                    } else {
                        rows = '<tr><td colspan="5" class="text-center">Nenhum membro associado</td></tr>';
                    }
                    $('#tabelaMembros tbody').html(rows);

                    // Adicionar eventos aos botões de edição e exclusão de membros
                    $('.btn-editar-membro').click(function() {
                        const projetoId = $(this).data('projeto-id');
                        const pessoaId = $(this).data('pessoa-id');
                        carregarMembro(projetoId, pessoaId);
                    });

                    $('.btn-excluir-membro').click(function() {
                        const projetoId = $(this).data('projeto-id');
                        const pessoaId = $(this).data('pessoa-id');
                        prepararExclusao({projetoId, pessoaId}, 'membro', `Deseja realmente remover este membro do projeto?`);
                    });
                }
            });
        }

        function carregarTarefasDoProjeto(projetoId) {
            $.ajax({
                url: '/api/tarefas',
                method: 'GET',
                data: { projetoId: projetoId },
                success: function(data) {
                    let rows = '';
                    if (data.length > 0) {
                        data.forEach(function(tarefa) {
                            rows += `
                                    <tr>
                                        <td>${tarefa.titulo}</td>
                                        <td>${tarefa.responsavel ? tarefa.responsavel.nome : 'Não definido'}</td>
                                        <td><span class="badge status-${tarefa.status.toLowerCase().replace('_', '-')}">${formatarStatus(tarefa.status)}</span></td>
                                        <td>${tarefa.dataLimite ? formatarDataHora(tarefa.dataLimite) : '-'}</td>
                                        <td>
                                            <button class="btn btn-sm btn-primary me-1 btn-editar-tarefa"
                                                    data-id="${tarefa.id}"
                                                    title="Editar">
                                                <i class="fas fa-edit"></i>
                                            </button>
                                            <button class="btn btn-sm btn-danger btn-excluir-tarefa"
                                                    data-id="${tarefa.id}"
                                                    title="Excluir" ${tarefa.status != 'PENDENTE' ? 'disabled' : ''}>
                                                <i class="fas fa-trash"></i>
                                            </button>
                                            <button class="btn btn-sm btn-warning btn-status-tarefa"
                                                    data-id="${tarefa.id}"
                                                    title="Alterar Status">
                                                <i class="fas fa-sync-alt"></i>
                                            </button>
                                        </td>
                                    </tr>
                                `;
                        });
                    } else {
                        rows = '<tr><td colspan="5" class="text-center">Nenhuma tarefa cadastrada</td></tr>';
                    }
                    $('#tabelaTarefas tbody').html(rows);

                    // Adicionar eventos aos botões de tarefas
                    $('.btn-editar-tarefa').click(function() {
                        const tarefaId = $(this).data('id');
                        carregarTarefa(tarefaId);
                    });

                    $('.btn-excluir-tarefa').click(function() {
                        const tarefaId = $(this).data('id');
                        prepararExclusao(tarefaId, 'tarefa', `Deseja realmente excluir esta tarefa?`);
                    });

                    $('.btn-status-tarefa').click(function() {
                        const tarefaId = $(this).data('id');
                        alterarStatusTarefa(tarefaId);
                    });
                }
            });
        }

        function carregarMembro(projetoId, pessoaId) {
            $.ajax({
                url: `/api/membros/${projetoId}/${pessoaId}`,
                method: 'GET',
                success: function(data) {
                    $('#projetoIdMembro').val(projetoId);
                    $('#membroId').val(pessoaId);
                    $('#pessoa').val(pessoaId);
                    $('#dataEntrada').val(data.dataEntrada);
                    $('#funcao').val(data.funcao || '');

                    // Atualizar título do modal
                    $('#membroModalLabel').text('Editar Membro do Projeto');

                    // Mostrar o modal
                    const membroModal = new bootstrap.Modal(document.getElementById('membroModal'));
                    membroModal.show();
                }
            });
        }

        function carregarTarefa(id) {
            $.ajax({
                url: `/api/tarefas/${id}`,
                method: 'GET',
                success: function(data) {
                    $('#projetoIdTarefa').val(data.projeto.id);
                    $('#tarefaId').val(data.id);
                    $('#titulo').val(data.titulo);
                    $('#descricaoTarefa').val(data.descricao || '');
                    $('#responsavel').val(data.responsavel ? data.responsavel.id : '');
                    $('#statusTarefa').val(data.status);
                    $('#dataLimite').val(data.dataLimite ? formatarDataHoraParaInput(data.dataLimite) : '');

                    // Carregar funcionários para o select de responsável
                    carregarFuncionarios();

                    // Atualizar título do modal
                    $('#tarefaModalLabel').text('Editar Tarefa');

                    // Mostrar o modal
                    const tarefaModal = new bootstrap.Modal(document.getElementById('tarefaModal'));
                    tarefaModal.show();
                }
            });
        }

        function carregarFuncionarios() {
            $.ajax({
                url: '/api/pessoas/funcionarios',
                method: 'GET',
                success: function(data) {
                    let options = '<option value="">Selecione...</option>';
                    data.forEach(function(funcionario) {
                        options += `<option value="${funcionario.id}">${funcionario.nome}</option>`;
                    });
                    $('#responsavel').html(options);
                }
            });
        }

        function carregarPessoas() {
            $.ajax({
                url: '/api/pessoas',
                method: 'GET',
                success: function(data) {
                    let options = '<option value="">Selecione...</option>';
                    data.forEach(function(pessoa) {
                        options += `<option value="${pessoa.id}">${pessoa.nome} (${pessoa.cargo})</option>`;
                    });
                    $('#pessoa').html(options);
                }
            });
        }

        // Funções para abrir modais
        function abrirModalNovoProjeto() {
            projetoAtual = null;

            // Limpar o formulário
            $('#projetoForm')[0].reset();
            $('#projetoId').val('');
            $('#tabelaMembros tbody').html('<tr><td colspan="5" class="text-center">Nenhum membro associado</td></tr>');
            $('#tabelaTarefas tbody').html('<tr><td colspan="5" class="text-center">Nenhuma tarefa cadastrada</td></tr>');

            // Atualizar título do modal
            $('#projetoModalLabel').text('Novo Projeto');

            // Mostrar o modal
            const projetoModal = new bootstrap.Modal(document.getElementById('projetoModal'));
            projetoModal.show();
        }

        function abrirModalNovoMembro() {
            if (!projetoAtual) return;

            // Limpar o formulário
            $('#membroForm')[0].reset();
            $('#projetoIdMembro').val(projetoAtual.id);
            $('#membroId').val('');

            // Carregar pessoas para o select
            carregarPessoas();

            // Atualizar título do modal
            $('#membroModalLabel').text('Adicionar Membro ao Projeto');

            // Mostrar o modal
            const membroModal = new bootstrap.Modal(document.getElementById('membroModal'));
            membroModal.show();
        }

        function abrirModalNovaTarefa() {
            if (!projetoAtual) return;

            // Limpar o formulário
            $('#tarefaForm')[0].reset();
            $('#projetoIdTarefa').val(projetoAtual.id);
            $('#tarefaId').val('');

            // Carregar funcionários para o select de responsável
            carregarFuncionarios();

            // Atualizar título do modal
            $('#tarefaModalLabel').text('Adicionar Tarefa ao Projeto');

            // Mostrar o modal
            const tarefaModal = new bootstrap.Modal(document.getElementById('tarefaModal'));
            tarefaModal.show();
        }

        // Funções para salvar dados
        function salvarProjeto() {
            const form = $('#projetoForm')[0];
            if (!form.checkValidity()) {
                form.reportValidity();
                return;
            }

            const projetoData = {
                nome: $('#nome').val(),
                dataInicio: $('#dataInicio').val(),
                dataPrevisaoFim: $('#dataPrevisaoFim').val(),
                dataFim: $('#dataFim').val() || null,
                descricao: $('#descricao').val() || null,
                status: $('#status').val(),
                orcamento: $('#orcamento').val() ? parseFloat($('#orcamento').val()) : null,
                risco: $('input[name="risco"]:checked').val() || null,
                idGerente: $('#gerente').val()
            };

            const metodo = projetoAtual ? 'PUT' : 'POST';
            const url = projetoAtual ? `/api/projetos/${projetoAtual.id}` : '/api/projetos';

            $.ajax({
                url: url,
                method: metodo,
                contentType: 'application/json',
                data: JSON.stringify(projetoData),
                success: function(data) {
                    alert('Projeto salvo com sucesso!');
                    carregarProjetos();
                    $('#projetoModal').modal('hide');
                },
                error: function(xhr) {
                    alert('Erro ao salvar projeto: ' + (xhr.responseJSON?.message || xhr.statusText));
                }
            });
        }

        function salvarMembro() {
            const form = $('#membroForm')[0];
            if (!form.checkValidity()) {
                form.reportValidity();
                return;
            }

            const membroData = {
                idProjeto: $('#projetoIdMembro').val(),
                idPessoa: $('#pessoa').val(),
                dataEntrada: $('#dataEntrada').val(),
                funcao: $('#funcao').val() || null
            };

            const url = '/api/membros';

            $.ajax({
                url: url,
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(membroData),
                success: function(data) {
                    alert('Membro associado com sucesso!');
                    carregarMembrosDoProjeto(membroData.idProjeto);
                    $('#membroModal').modal('hide');
                },
                error: function(xhr) {
                    alert('Erro ao associar membro: ' + (xhr.responseJSON?.message || xhr.statusText));
                }
            });
        }

        function salvarTarefa() {
            const form = $('#tarefaForm')[0];
            if (!form.checkValidity()) {
                form.reportValidity();
                return;
            }

            const tarefaData = {
                titulo: $('#titulo').val(),
                descricao: $('#descricaoTarefa').val() || null,
                idProjeto: $('#projetoIdTarefa').val(),
                idResponsavel: $('#responsavel').val() || null,
                status: $('#statusTarefa').val(),
                dataLimite: $('#dataLimite').val() || null
            };

            const metodo = $('#tarefaId').val() ? 'PUT' : 'POST';
            const url = $('#tarefaId').val() ? `/api/tarefas/${$('#tarefaId').val()}` : '/api/tarefas';

            $.ajax({
                url: url,
                method: metodo,
                contentType: 'application/json',
                data: JSON.stringify(tarefaData),
                success: function(data) {
                    alert('Tarefa salva com sucesso!');
                    carregarTarefasDoProjeto(tarefaData.idProjeto);
                    $('#tarefaModal').modal('hide');
                },
                error: function(xhr) {
                    alert('Erro ao salvar tarefa: ' + (xhr.responseJSON?.message || xhr.statusText));
                }
            });
        }

        // Funções para exclusão
        function prepararExclusao(id, tipo, mensagem) {
            itemParaExcluir = id;
            tipoItemParaExcluir = tipo;
            $('#mensagemExclusao').text(mensagem);

            const confirmarExclusaoModal = new bootstrap.Modal(document.getElementById('confirmarExclusaoModal'));
            confirmarExclusaoModal.show();
        }

        function confirmarExclusao() {
            if (!itemParaExcluir || !tipoItemParaExcluir) return;

            let url = '';
            let metodo = 'DELETE';

            switch (tipoItemParaExcluir) {
                case 'projeto':
                    url = `/api/projetos/${itemParaExcluir}`;
                    break;
                case 'tarefa':
                    url = `/api/tarefas/${itemParaExcluir}`;
                    break;
                case 'membro':
                    url = `/api/membros/${itemParaExcluir.projetoId}/${itemParaExcluir.pessoaId}`;
                    break;
                default:
                    return;
            }

            $.ajax({
                url: url,
                method: metodo,
                success: function() {
                    alert('Item excluído com sucesso!');

                    if (tipoItemParaExcluir === 'projeto') {
                        carregarProjetos();
                    } else if (projetoAtual) {
                        if (tipoItemParaExcluir === 'tarefa') {
                            carregarTarefasDoProjeto(projetoAtual.id);
                        } else if (tipoItemParaExcluir === 'membro') {
                            carregarMembrosDoProjeto(projetoAtual.id);
                        }
                    }

                    $('#confirmarExclusaoModal').modal('hide');
                },
                error: function(xhr) {
                    alert('Erro ao excluir item: ' + (xhr.responseJSON?.message || xhr.statusText));
                    $('#confirmarExclusaoModal').modal('hide');
                }
            });
        }

        // Funções auxiliares
        function aplicarFiltros() {
            const filtros = {
                nome: $('#filtroNome').val() || null,
                status: $('#filtroStatus').val() || null,
                dataInicio: $('#filtroDataInicio').val() || null,
                dataFim: $('#filtroDataFim').val() || null,
                idGerente: $('#filtroGerente').val() || null
            };

            carregarProjetos(filtros);
        }

        function limparFiltros() {
            $('#filtroForm')[0].reset();
            carregarProjetos();
        }

        function calcularRisco() {
            if (!projetoAtual) return;

            $.ajax({
                url: `/api/projetos/${projetoAtual.id}/risco`,
                method: 'GET',
                success: function(risco) {
                    alert(`Risco calculado: ${risco}`);
                    $('input[name="risco"]').prop('checked', false);
                    $(`#risco${risco.charAt(0) + risco.slice(1).toLowerCase()}`).prop('checked', true);
                },
                error: function(xhr) {
                    alert('Erro ao calcular risco: ' + (xhr.responseJSON?.message || xhr.statusText));
                }
            });
        }

        function alterarStatusTarefa(tarefaId) {
            const novoStatus = prompt('Informe o novo status da tarefa (PENDENTE, EM_ANDAMENTO, CONCLUIDA, CANCELADA):');
            if (!novoStatus) return;

            const comentario = prompt('Informe um comentário (opcional):');

            const dadosStatus = {
                novoStatus: novoStatus,
                comentario: comentario || null
            };

            $.ajax({
                url: `/api/tarefas/${tarefaId}/status`,
                method: 'PATCH',
                contentType: 'application/json',
                data: JSON.stringify(dadosStatus),
                success: function() {
                    alert('Status da tarefa atualizado com sucesso!');
                    if (projetoAtual) {
                        carregarTarefasDoProjeto(projetoAtual.id);
                    }
                },
                error: function(xhr) {
                    alert('Erro ao atualizar status: ' + (xhr.responseJSON?.message || xhr.statusText));
                }
            });
        }

        function formatarData(data) {
            if (!data) return '-';
            const date = new Date(data);
            return date.toLocaleDateString('pt-BR');
        }

        function formatarDataHora(dataHora) {
            if (!dataHora) return '-';
            const date = new Date(dataHora);
            return date.toLocaleString('pt-BR');
        }

        function formatarDataHoraParaInput(dataHora) {
            if (!dataHora) return '';
            const date = new Date(dataHora);
            return date.toISOString().slice(0, 16);
        }

        function formatarStatus(status) {
            const statusMap = {
                'EM_ANALISE': 'Em Análise',
                'ANALISE_REALIZADA': 'Análise Realizada',
                'ANALISE_APROVADA': 'Análise Aprovada',
                'INICIADO': 'Iniciado',
                'PLANEJADO': 'Planejado',
                'EM_ANDAMENTO': 'Em Andamento',
                'ENCERRADO': 'Encerrado',
                'CANCELADO': 'Cancelado',
                'PENDENTE': 'Pendente',
                'CONCLUIDA': 'Concluída',
                'CANCELADA': 'Cancelada'
            };
            return statusMap[status] || status;
        }
    });
</script>
</body>
</html>
