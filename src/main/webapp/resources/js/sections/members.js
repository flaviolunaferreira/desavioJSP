class MembersSection {
    constructor() {
        this.currentPage = 1;
        this.itemsPerPage = 10;
    }

    init() {
        this.loadMembers();
        this.setupEventListeners();
    }

    async loadMembers() {
        try {
            const members = await ApiService.get('/api/membros');
            this.renderMembers(members);
        } catch (error) {
            console.error('Erro ao carregar membros:', error);
            Ui.showError('Erro ao carregar membros');
        }
    }

    renderMembers(members) {
        const tbody = $('#members-table-body');
        tbody.empty();

        if (members.length === 0) {
            tbody.append('<tr><td colspan="5" class="text-center">Nenhum membro encontrado</td></tr>');
            return;
        }

        members.forEach(member => {
            tbody.append(`
                <tr>
                    <td>${member.projeto.nome}</td>
                    <td>${member.pessoa.nome}</td>
                    <td>${member.funcao || '-'}</td>
                    <td>${new Date(member.dataEntrada).toLocaleDateString()}</td>
                    <td>
                        <button class="btn btn-sm btn-outline-vscode-blue" 
                                data-bs-toggle="modal" data-bs-target="#crudModal" 
                                data-type="membro" data-action="editar" 
                                data-id="${member.projeto.id}/${member.pessoa.id}">
                            <i class="bi bi-pencil"></i>
                        </button>
                    </td>
                </tr>
            `);
        });
    }

    setupEventListeners() {
        $(document).on('formSaved', (e, data) => {
            if (data.type === 'membro') {
                this.loadMembers();
            }
        });
    }
}