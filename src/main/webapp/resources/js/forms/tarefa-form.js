class TarefaForm extends FormBuilder {
    static getFieldsConfig(action, projetoId = null) {
        const baseFields = [];

        if (!projetoId) {
            baseFields.push({
                id: 'projetoId',
                type: 'select',
                label: 'Projeto',
                required: true,
                options: []
            });
        } else {
            baseFields.push(`<input type="hidden" id="projetoId" name="projetoId" value="${projetoId}">`);
        }

        baseFields.push(
            {
                id: 'titulo',
                type: 'text',
                label: 'Título',
                required: true
            },
            {
                id: 'descricao',
                type: 'textarea',
                label: 'Descrição'
            },
            {
                id: 'responsavelId',
                type: 'select',
                label: 'Responsável',
                options: []
            },
            {
                id: 'dataLimite',
                type: 'datetime-local',
                label: 'Data Limite'
            }
        );

        if (action === 'editar') {
            baseFields.push({
                id: 'status',
                type: 'select',
                label: 'Status',
                options: [
                    { value: 'PENDENTE', label: 'Pendente' },
                    { value: 'EM_ANDAMENTO', label: 'Em Andamento' },
                    { value: 'BLOQUEADA', label: 'Bloqueada' },
                    { value: 'CONCLUIDA', label: 'Concluída' },
                    { value: 'CANCELADA', label: 'Cancelada' }
                ]
            });
        }

        return baseFields;
    }

    static getForm(data, action, options = {}) {
        const fieldsConfig = this.getFieldsConfig(action, options.projetoId);
        let fieldsHtml = '<input type="hidden" id="id" name="id">';

        for (const field of fieldsConfig) {
            if (typeof field === 'string') {
                fieldsHtml += field;
                continue;
            }

            let value = data ? data[field.id] : '';

            // Tratamento especial para relacionamentos
            if (field.id === 'responsavelId' && data?.responsavel) {
                value = data.responsavel.id;
            }

            // Formatação de data para datetime-local
            if (field.id === 'dataLimite' && value) {
                const date = new Date(value);
                value = date.toISOString().slice(0, 16);
            }

            fieldsHtml += this.createFormField(field, value);
        }

        return this.buildFormContainer(fieldsHtml, 'tarefaForm');
    }
}