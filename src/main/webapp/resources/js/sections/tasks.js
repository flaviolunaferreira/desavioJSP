class TasksSection {
    constructor() {
        this.currentPage = 1;
        this.itemsPerPage = 10;
        this.filters = {
            status: null,
            projetoId: null
        };
    }

    init() {
        this.loadTasks().then(r => console.log("Tasks loaded", r));
        this.setupEventListeners();
        this.initFilters();
    }

    async loadTasks() {
        console.log('Abrindo tarefas com o filtro', this.filters);
        try {
            let url = '/api/tarefas';
            if (this.filters.status || this.filters.projetoId) {
                const params = new URLSearchParams();
                if (this.filters.status) params.append('status', this.filters.status);
                if (this.filters.projetoId) params.append('projetoId', this.filters.projetoId);
                url += `?${params.toString()}`;
            }

            const tasks = await ApiService.get(url);
            this.renderTasks(tasks);
            Ui.showSuccess('Tarefa atualizada com sucesso');
        } catch (error) {
            console.error('Erro ao carregar tarefas:', error);
            Ui.showError('Erro ao carregar tarefas');
        }
    }

    renderTasks(tasks) {
        const tbody = $('#tasks-table-body');
        tbody.empty();

        if (tasks.length === 0) {
            tbody.append('<tr><td colspan="6" class="text-center">Nenhuma tarefa encontrada</td></tr>');
            return;
        }

        tasks.forEach(task => {
            tbody.append(`
                <tr>
                    <td>${task.titulo}</td>
                    <td>${task.projeto?.nome || 'N/A'}</td>
                    <td>${task.responsavel?.nome || 'N/A'}</td>
                    <td><span class="badge ${Ui.getStatusColorClass(task.status)}">
                        ${task.status.replace('_', ' ')}</span></td>
                    <td>${task.dataLimite ?
                new Date(task.dataLimite).toLocaleString() : 'N/A'}</td>
                    <td>
                        <button class="btn btn-sm btn-outline-vscode-blue" 
                                data-bs-toggle="modal" data-bs-target="#crudModal" 
                                data-type="tarefa" data-action="editar" data-id="${task.id}">
                            <i class="bi bi-pencil"></i>
                        </button>
                        <button class="btn btn-sm btn-outline-vscode-red btn-delete ms-1" 
                            data-type="tarefa" data-id="${task.id}">
                            <i class="bi bi-trash"></i>
                        </button>
                    </td>
                </tr>
            `);
        });
    }

    initFilters() {
        // Inicializa selects de filtro
        ApiService.get('/api/projetos').then(projects => {
            const select = $('#filter-projeto');
            select.empty().append('<option value="">Todos os projetos</option>');

            projects.forEach(project => {
                select.append(`<option value="${project.id}">${project.nome}</option>`);
            });
        });

        $('#filter-status').empty()
            .append('<option value="">Todos os status</option>')
            .append('<option value="PENDENTE">Pendente</option>')
            .append('<option value="EM_ANDAMENTO">Em Andamento</option>')
            .append('<option value="BLOQUEADA">Bloqueada</option>')
            .append('<option value="CONCLUIDA">Concluída</option>');
    }

    setupEventListeners() {
        $(document).on('formSaved', (e, data) => {
            if (data.type === 'tarefa') {
                this.loadTasks();
            }
        });

        $('#filter-projeto, #filter-status').change(() => {
            this.filters.projetoId = $('#filter-projeto').val();
            this.filters.status = $('#filter-status').val();
            this.loadTasks();
        });
    }

}