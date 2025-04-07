class ProjectsSection {
    constructor() {
        this.currentPage = 1;
        this.itemsPerPage = 6;
    }

    init() {
        this.loadProjects();
        this.setupEventListeners();
    }

    async loadProjects() {
        try {
            const projects = await ApiService.get('/api/projetos');
            this.renderProjects(projects);
        } catch (error) {
            console.error('Erro ao carregar projetos:', error);
            Ui.showError('Erro ao carregar projetos');
        }
    }

    renderProjects(projects) {
        const container = $('#projects-container');
        container.empty();

        if (projects.length === 0) {
            container.append('<p class="text-center">Nenhum projeto encontrado</p>');
            return;
        }

        projects.forEach(project => {
            container.append(`
                <div class="col-md-4 mb-4">
                    <div class="card bg-vscode-card project-card" data-id="${project.id}">
                        <div class="card-header bg-vscode-card-header">
                            <h5 class="card-title mb-0">${project.nome}</h5>
                        </div>
                        <div class="card-body">
                            <p class="card-text text-vscode-text">${project.descricao || 'Sem descrição'}</p>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="badge ${Ui.getStatusColorClass(project.status)}">
                                    ${project.status.replace('_', ' ')}
                                </span>
                                <div>
                                    <a href="/projetos/${project.id}" 
                                       class="btn btn-sm btn-outline-vscode-blue">
                                        <i class="bi bi-eye"></i>
                                    </a>
                                    <button class="btn btn-sm btn-outline-vscode-blue btn-edit ms-1"
                                            data-type="projeto" data-action="editar" data-id="${project.id}">
                                        <i class="bi bi-pencil"></i>
                                    </button>
                                    <button class="btn btn-sm btn-outline-vscode-red btn-delete ms-1"
                                            data-type="projeto" data-id="${project.id}">
                                        <i class="bi bi-trash"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            `);
        });
    }

    setupEventListeners() {
        $(document).on('formSaved', (e, data) => {
            if (data.type === 'projeto') {
                this.loadProjects();
            }
        });

        // Listener para clique no card (visualização)
        $(document).on('click', '.project-card', (e) => {
            // Evita redirecionar se clicou em um botão dentro do card
            if ($(e.target).closest('button, a').length === 0) {
                const projectId = $(e.currentTarget).closest('.project-card').data('id');
                if (projectId) {
                    window.location.href = `/projetos/${projectId}`;
                }
            }
        });

        // Listener para botão de exclusão
        $(document).on('click', '.btn-delete', async (e) => {
            e.stopPropagation();
            const button = $(e.currentTarget);
            const projectId = button.data('id');

            try {

                Ui.showLoading(true);

                // Chama a API para excluir
                await ApiService.delete(`/api/projetos/${projectId}`);

                Ui.showSuccess('Projeto excluído com sucesso!');
                await this.loadProjects(); // Recarrega a lista

            } catch (error) {
                console.error('Erro ao excluir projeto:', error);

                // Mostra mensagem de erro específica da API ou genérica
                const errorMsg = error.responseJSON?.message ||
                    error.message ||
                    'Erro ao excluir projeto';
                Ui.showError(errorMsg);
            } finally {
                Ui.showLoading(false);
            }
        });
    }
}