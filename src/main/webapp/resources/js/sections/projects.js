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
                                <a href="projeto-detalhes.jsp?id=${project.id}" 
                                   class="btn btn-sm btn-outline-vscode-blue">
                                    <i class="bi bi-arrow-right"></i> Detalhes
                                </a>
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

        $(document).on('click', '.project-card', (e) => {
            const projectId = $(e.currentTarget).closest('.project-card').data('id');
            if (projectId) {
                window.location.href = `projeto-detalhes.jsp?id=${projectId}`;
            }
        });
    }
}