class App {
    constructor() {
        this.sections = {
            dashboard: new DashboardSection(),
            projects: new ProjectsSection(),
            people: new PeopleSection(),
            tasks: new TasksSection(),
            members: new MembersSection()
        };

        this.formManager = new FormManager();
    }

    init() {
        this.initNavigation();
        this.initEventListeners();
        this.showSection('dashboard');
    }



    initNavigation() {
        $(document).on('click', '.section-link', (e) => {
            e.preventDefault();
            const section = $(e.currentTarget).data('section');
            this.showSection(section);
        });
    }

    initEventListeners() {
        // Listeners para eventos globais
        $(document).on('formSaved', (e, data) => {
            // Atualiza a seção relevante quando um formulário é salvo
            if (this.sections[data.type + 's']) {
                this.sections[data.type + 's'].init();
            }

            // Atualiza dashboard se for projeto ou tarefa
            if (data.type === 'projeto' || data.type === 'tarefa') {
                this.sections.dashboard.init();
            }
        });
    }

    showSection(sectionId) {
        $('.section-content').addClass('d-none');
        $(`#${sectionId}-section`).removeClass('d-none');

        $('.nav-link').removeClass('active');
        $(`.section-link[data-section="${sectionId}"]`).addClass('active');

        if (this.sections[sectionId]) {
            this.sections[sectionId].init();
        }
    }
}

$(document).ready(() => {
    window.app = new App();
    window.app.init();
});