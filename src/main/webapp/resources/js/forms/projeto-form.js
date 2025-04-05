class ProjetoForm extends FormBuilder {
    static getFieldsConfig(action) {
        const baseFields = [
            {
                id: 'nome',
                type: 'text',
                label: 'Nome',
                required: true
            },
            {
                id: 'descricao',
                type: 'textarea',
                label: 'Descrição'
            },
            {
                id: 'dataInicio',
                type: 'date',
                label: 'Data de Início',
                required: true
            },
            {
                id: 'dataPrevisaoFim',
                type: 'date',
                label: 'Data Prevista de Término',
                required: true
            },
            {
                id: 'orcamento',
                type: 'number',
                label: 'Orçamento',
                step: '0.01'
            },
            {
                id: 'gerente',
                type: 'select',
                label: 'Gerente',
                required: true,
                options: []
            }
        ];

        if (action === 'editar') {
            baseFields.push(
                {
                    id: 'status',
                    type: 'select',
                    label: 'Status',
                    required: true,
                    options: [
                        { value: 'EM_ANALISE', label: 'Em Análise' },
                        { value: 'ANALISE_REALIZADA', label: 'Análise Realizada' },
                        { value: 'ANALISE_APROVADA', label: 'Análise Aprovada' },
                        { value: 'INICIADO', label: 'Iniciado' },
                        { value: 'PLANEJADO', label: 'Planejado' },
                        { value: 'EM_ANDAMENTO', label: 'Em Andamento' },
                        { value: 'ENCERRADO', label: 'Encerrado' },
                        { value: 'CANCELADO', label: 'Cancelado' }
                    ]
                },
                {
                    id: 'risco',
                    type: 'select',
                    label: 'Risco',
                    options: [
                        { value: 'BAIXO', label: 'Baixo' },
                        { value: 'MEDIO', label: 'Médio' },
                        { value: 'ALTO', label: 'Alto' }
                    ]
                }
            );
        }

        return baseFields;
    }

    static async getForm(data, action, options = {}) {
        const fieldsConfig = this.getFieldsConfig(action);
        let fieldsHtml = '<input type="hidden" id="id" name="id">';

        // Preenche os campos com os dados existentes (se for edição)
        for (const field of fieldsConfig) {
            let value = data ? data[field.id] : '';

            // Tratamento especial para relacionamentos
            if (field.id === 'gerente' && data?.gerente) {
                value = data.gerente.id;
            }

            fieldsHtml += this.createFormField(field, value);
        }

        return this.buildFormContainer(fieldsHtml, 'projetoForm');
    }
}