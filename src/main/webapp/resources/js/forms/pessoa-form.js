class PessoaForm extends FormBuilder {
    static getFieldsConfig() {
        return [
            {
                id: 'nome',
                type: 'text',
                label: 'Nome',
                required: true
            },
            {
                id: 'dataNascimento',
                type: 'date',
                label: 'Data de Nascimento'
            },
            {
                id: 'cpf',
                type: 'text',
                label: 'CPF'
            },
            {
                id: 'funcionario',
                type: 'checkbox',
                label: 'Funcionário'
            },
            {
                id: 'gerente',
                type: 'checkbox',
                label: 'Gerente'
            }
        ];
    }

    static getForm(data, action) {
        const fieldsConfig = this.getFieldsConfig();
        let fieldsHtml = '<input type="hidden" id="id" name="id">';

        for (const field of fieldsConfig) {
            fieldsHtml += this.createFormField(field, data ? data[field.id] : '');
        }

        return this.buildFormContainer(fieldsHtml, 'pessoaForm');
    }
}