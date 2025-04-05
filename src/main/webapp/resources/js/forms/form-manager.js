class FormManager {
    constructor() {
        this.formBuilders = {
            projeto: ProjetoForm,
            pessoa: PessoaForm,
            tarefa: TarefaForm,
            membro: MembroForm
        };
    }

    openForm(type, action = 'criar', id = null, options = {}) {
        const builder = this.formBuilders[type];
        if (!builder) {
            console.error(`Tipo de formulário não suportado: ${type}`);
            return;
        }

        const title = this.getFormTitle(type, action);
        $('#crudModalLabel').text(title);

        const formHtml = builder.getForm(null, action, options);
        $('#crudModalBody').html(formHtml);

        if (action === 'editar' && id) {
            this.loadFormData(type, id);
        } else {
            this.loadDynamicSelects(type, options);
        }

        $('#crudModal').modal('show');
    }

    // ... outros métodos permanecem iguais
}