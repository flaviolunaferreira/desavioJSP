class App {
    constructor() {
        this.formManager = new FormManager();
        this.sections = {
            dashboard: new DashboardSection(),
            projects: new ProjectsSection(),
            people: new PeopleSection(),
            tasks: new TasksSection(),
            members: new MembersSection()
        };
    }

    init() {
        this.initNavigation();
        this.initEventListeners();
        this.showSection('dashboard');

        // Inicializa os tooltips
        $('[data-bs-toggle="tooltip"]').tooltip();
    }

    initNavigation() {
        $(document).on('click', '.section-link', (e) => {
            e.preventDefault();
            const section = $(e.currentTarget).data('section');
            this.showSection(section);
        });
    }

    initEventListeners() {
        $(document).on('formSaved', (e, data) => {
            if (this.sections[data.type + 's']) {
                this.sections[data.type + 's'].init();
            }
            if (data.type === 'projeto' || data.type === 'tarefa') {
                this.sections.dashboard.init();
            }
        });
        $(document).on('click', '.btn-delete', (e) => {
            e.preventDefault();
            const button = $(e.currentTarget);
            const type = button.data('type');
            const id = button.data('id');

            this.confirmDelete(type, id);
        });
    }

    showSection(sectionId) {
        // Esconde todas as seções
        $('.section-content').removeClass('active').hide();

        // Mostra apenas a seção selecionada
        $(`#${sectionId}-section`).addClass('active').show();

        // Atualiza a navegação
        $('.nav-link').removeClass('active');
        $(`.section-link[data-section="${sectionId}"]`).addClass('active');

        // Inicializa a seção
        if (this.sections[sectionId]) {
            this.sections[sectionId].init();
        }
    }

    confirmDelete(type, id) {
        const typeNames = {
            'projeto': 'projeto',
            'pessoa': 'pessoa',
            'tarefa': 'tarefa',
            'membro': 'membro'
        };

        Ui.confirmDelete(`Tem certeza que deseja excluir este ${typeNames[type]}? Esta ação não pode ser desfeita.`, () => {
            this.performDelete(type, id);
        });
    }

    async performDelete(type, id) {
        try {
            Ui.showLoading(true);

            let url;
            if (type === 'membro') {
                const [projetoId, pessoaId] = id.split('/');
                url = `/api/membros/${projetoId}/${pessoaId}`;
            } else {
                url = `/api/${type}s/${id}`;
            }

            await ApiService.delete(url);
            Ui.showSuccess(`${typeNames[type]} excluído com sucesso!`);

            // Recarregar a seção apropriada
            if (this.sections[`${type}s`]) {
                this.sections[`${type}s`].init();
            }

            // Se for projeto ou tarefa, atualizar dashboard também
            if (type === 'projeto' || type === 'tarefa') {
                this.sections.dashboard.init();
            }

        } catch (error) {
            console.error(`Erro ao excluir ${type}:`, error);
            Ui.showError(`Erro ao excluir ${typeNames[type]}: ${error.message}`);
        } finally {
            Ui.showLoading(false);
        }
    }
}

$(document).ready(() => {
    window.app = new App();
    window.app.init();
});