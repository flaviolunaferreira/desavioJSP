class ProjetoForm extends FormBuilder {
    static statusOptions = [
        { value: 'EM_ANALISE', label: 'Em Análise' },
        { value: 'ANALISE_REALIZADA', label: 'Análise Realizada' },
        { value: 'ANALISE_APROVADA', label: 'Análise Aprovada' },
        { value: 'INICIADO', label: 'Iniciado' },
        { value: 'PLANEJADO', label: 'Planejado' },
        { value: 'EM_ANDAMENTO', label: 'Em Andamento' },
        { value: 'ENCERRADO', label: 'Encerrado' },
        { value: 'CANCELADO', label: 'Cancelado' }
    ];

    static riscoOptions = [
        { value: 'BAIXO', label: 'Baixo' },
        { value: 'MEDIO', label: 'Médio' },
        { value: 'ALTO', label: 'Alto' }
    ];

    static async getForm(data, action, options) {
        const fields = [
            `<input type="hidden" name="id" value="${data?.id || ''}">`,
            this.createField({
                type: 'text',
                name: 'nome',
                label: 'Nome',
                required: true,
                maxlength: 100
            }, data?.nome),
            this.createField({
                type: 'date',
                name: 'dataInicio',
                label: 'Data de Início',
                required: true
            }, data?.dataInicio?.split('T')[0]),
            this.createField({
                type: 'date',
                name: 'dataPrevisaoFim',
                label: 'Data Prevista de Término',
                required: true
            }, data?.dataPrevisaoFim?.split('T')[0]),
            this.createField({
                type: 'textarea',
                name: 'descricao',
                label: 'Descrição',
            }, data?.descricao || ''),
            this.createField({
                type: 'number',
                name: 'orcamento',
                label: 'Orçamento',
                step: "0.01",
                min: "0"
            }, data?.orcamento),
            await this.createGerenteField(data?.gerente?.id),
            this.createField({
                type: 'select',
                name: 'risco',
                label: 'Risco',
                options: this.riscoOptions
            }, data?.risco || 'BAIXO'),
            this.createField({
                type: 'select',
                name: 'status',
                label: 'Status',
                options: this.statusOptions
            }, data?.status || 'EM_ANALISE')
        ];

        return this.buildForm(fields, 'projetoForm');
    }

    static async createGerenteField(selectedValue) {
        try {
            const gerentes = await PessoaApi.listGerentes();
            const options = gerentes.map(g => ({
                value: g.id,
                label: g.nome
            }));

            return this.createField({
                type: 'select',
                name: 'gerente.id',
                label: 'Gerente',
                required: true,
                options: [
                    { value: '', label: 'Selecione um gerente' },
                    ...options
                ]
            }, selectedValue);
        } catch (error) {
            console.error('Erro ao carregar gerentes:', error);
            return this.createField({
                type: 'select',
                name: 'gerente.id',
                label: 'Gerente',
                required: true,
                options: [
                    { value: '', label: 'Erro ao carregar gerentes' }
                ]
            }, selectedValue);
        }
    }
}