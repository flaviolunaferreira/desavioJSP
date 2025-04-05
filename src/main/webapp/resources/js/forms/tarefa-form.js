class TarefaForm extends FormBuilder {
    static statusOptions = [
        { value: 'PENDENTE', label: 'Pendente' },
        { value: 'EM_ANDAMENTO', label: 'Em Andamento' },
        { value: 'BLOQUEADA', label: 'Bloqueada' },
        { value: 'CONCLUIDA', label: 'Concluída' },
        { value: 'CANCELADA', label: 'Cancelada' }
    ];

    static async getForm(data, action, options) {
        const fields = [
            `<input type="hidden" name="id" value="${data?.id || ''}">`
        ];

        // Campo de projeto (se não estiver definido nas opções)
        if (!options.projetoId) {
            fields.push(await this.createProjetoField(data?.projeto?.id));
        } else {
            fields.push(`<input type="hidden" name="projetoId" value="${options.projetoId}">`);
        }

        fields.push(
            this.createField({
                type: 'text',
                name: 'titulo',
                label: 'Título',
                required: true
            }, data?.titulo),
            this.createField({
                type: 'textarea',
                name: 'descricao',
                label: 'Descrição'
            }, data?.descricao),
            await this.createResponsavelField(data?.responsavel?.id, options.projetoId),
            this.createField({
                type: 'datetime-local',
                name: 'dataLimite',
                label: 'Data Limite'
            }, data?.dataLimite ? new Date(data.dataLimite).toISOString().slice(0, 16) : '')
        );

        if (action === 'editar') {
            fields.push(
                this.createField({
                    type: 'select',
                    name: 'status',
                    label: 'Status',
                    options: this.statusOptions
                }, data?.status)
            );
        }

        return this.buildForm(fields, 'tarefaForm');
    }

    static async createProjetoField(selectedValue) {
        try {
            const projetos = await ProjetoApi.list();
            const options = projetos.map(p => ({
                value: p.id,
                label: p.nome
            }));

            return this.createField({
                type: 'select',
                name: 'projetoId',
                label: 'Projeto',
                required: true,
                options: [
                    { value: '', label: 'Selecione um projeto' },
                    ...options
                ]
            }, selectedValue);
        } catch (error) {
            console.error('Erro ao carregar projetos:', error);
            return this.createField({
                type: 'select',
                name: 'projetoId',
                label: 'Projeto',
                required: true,
                options: [
                    { value: '', label: 'Erro ao carregar projetos' }
                ]
            }, selectedValue);
        }
    }

    static async createResponsavelField(selectedValue, projetoId) {
        try {
            let pessoas = [];

            if (projetoId) {
                const membros = await MembroApi.listByProjeto(projetoId);
                pessoas = membros.map(m => m.pessoa);
            } else {
                pessoas = await PessoaApi.listFuncionarios();
            }

            const options = pessoas.map(p => ({
                value: p.id,
                label: p.nome
            }));

            return this.createField({
                type: 'select',
                name: 'responsavel.id',
                label: 'Responsável',
                options: [
                    { value: '', label: 'Selecione um responsável' },
                    ...options
                ]
            }, selectedValue);
        } catch (error) {
            console.error('Erro ao carregar responsáveis:', error);
            return this.createField({
                type: 'select',
                name: 'responsavel.id',
                label: 'Responsável',
                options: [
                    { value: '', label: 'Erro ao carregar responsáveis' }
                ]
            }, selectedValue);
        }
    }
}