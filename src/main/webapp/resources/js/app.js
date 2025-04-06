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
}

$(document).ready(() => {
    window.app = new App();
    window.app.init();
});