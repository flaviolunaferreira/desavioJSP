class MembroForm extends FormBuilder {
    static getFieldsConfig(projetoId = null) {
        const fields = [];

        if (!projetoId) {
            fields.push({
                id: 'membro-projeto',
                type: 'select',
                label: 'Projeto',
                required: true,
                options: []
            });
        } else {
            fields.push(`<input type="hidden" id="membroProjetoId" name="projeto" value="${projetoId}">`);
        }

        fields.push(
            {
                id: 'membro-pessoa',
                type: 'select',
                label: 'Pessoa',
                required: true,
                options: []
            },
            {
                id: 'funcao',
                type: 'text',
                label: 'Função'
            },
            {
                id: 'dataEntrada',
                type: 'date',
                label: 'Data de Entrada',
                required: true
            }
        );

        return fields;
    }

    static getForm(data, action, options = {}) {
        const fieldsConfig = this.getFieldsConfig(options.projetoId);
        let fieldsHtml = '';

        for (const field of fieldsConfig) {
            if (typeof field === 'string') {
                fieldsHtml += field;
                continue;
            }

            let value = data ? data[field.id.replace('membro-', '')] : '';

            // Tratamento especial para relacionamentos
            if (field.id === 'membro-pessoa' && data?.pessoa) {
                value = data.pessoa.id;
            }

            fieldsHtml += this.createFormField(field, value);
        }

        return this.buildFormContainer(fieldsHtml, 'membroForm');
    }
}