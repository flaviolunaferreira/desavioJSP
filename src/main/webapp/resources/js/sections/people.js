class PeopleSection {
    constructor() {
        this.currentPage = 1;
        this.itemsPerPage = 10;
    }

    init() {
        this.loadPeople();
        this.setupEventListeners();
    }

    async loadPeople() {
        try {
            const people = await ApiService.get('/api/pessoas');
            this.renderPeople(people);
        } catch (error) {
            console.error('Erro ao carregar pessoas:', error);
            Ui.showError('Erro ao carregar pessoas');
        }
    }

    renderPeople(people) {
        const tbody = $('#people-table-body');
        tbody.empty();

        if (people.length === 0) {
            tbody.append('<tr><td colspan="5" class="text-center">Nenhuma pessoa encontrada</td></tr>');
            return;
        }

        people.forEach(person => {
            tbody.append(`
                <tr>
                    <td>${person.nome}</td>
                    <td>${person.cpf || '-'}</td>
                    <td>${person.funcionario ?
                '<i class="bi bi-check-circle text-success"></i>' :
                '<i class="bi bi-x-circle text-danger"></i>'}</td>
                    <td>${person.gerente ?
                '<i class="bi bi-check-circle text-success"></i>' :
                '<i class="bi bi-x-circle text-danger"></i>'}</td>
                    <td>
                        <button class="btn btn-sm btn-outline-vscode-blue" 
                                data-bs-toggle="modal" data-bs-target="#crudModal" 
                                data-type="pessoa" data-action="editar" data-id="${person.id}">
                            <i class="bi bi-pencil"></i>
                        </button>
                    </td>
                </tr>
            `);
        });
    }

    setupEventListeners() {
        $(document).on('formSaved', (e, data) => {
            if (data.type === 'pessoa') {
                this.loadPeople();
            }
        });
    }
}