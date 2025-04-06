class TarefaForm extends FormBuilder {
    static statusOptions = [
        { value: 'PENDENTE', label: 'Pendente' },
        { value: 'EM_ANDAMENTO', label: 'Em Andamento' },
        { value: 'BLOQUEADA', label: 'Bloqueada' },
        { value: 'CONCLUIDA', label: 'Concluída' },
        { value: 'CANCELADA', label: 'Cancelada' }
    ];

    static async getForm(data, action, options) {
        // Garanta que data não é undefined
        data = data || {};

        const fields = [
            `<input type="hidden" name="id" value="${data.id || ''}">`
        ];

        // Projeto ID - obrigatório
        if (!options?.projetoId) {
            fields.push(await this.createProjetoField(data.projeto?.id));
        } else {
            fields.push(`<input type="hidden" name="projetoId" value="${options.projetoId}">`);
            data.projeto = data.projeto || { id: options.projetoId };
        }

        // Status - obrigatório com valor padrão
        const statusValue = data.status || 'PENDENTE';

        fields.push(
            this.createField({
                type: 'text',
                name: 'titulo',
                label: 'Título',
                required: true,
                maxlength: 100
            }, data.titulo || ''),

            this.createField({
                type: 'textarea',
                name: 'descricao',
                label: 'Descrição',
                maxlength: 500
            }, data.descricao || ''),

            await this.createResponsavelField(data.responsavel?.id, options?.projetoId),

            this.createField({
                type: 'datetime-local',
                name: 'dataLimite',
                label: 'Data Limite'
            }, data.dataLimite ? new Date(data.dataLimite).toISOString().slice(0, 16) : ''),

            // Campo status sempre obrigatório
            this.createField({
                type: 'select',
                name: 'status',
                label: 'Status',
                required: true,
                options: this.statusOptions
            }, statusValue)
        );

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
                    { value: '', label: 'Selecione um projeto', disabled: true },
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
                    { value: '', label: 'Erro ao carregar projetos', disabled: true }
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
                required: false, // Alterado para não obrigatório conforme DTO
                options: [
                    { value: '', label: 'Selecione um responsável', disabled: true },
                    ...options
                ]
            }, selectedValue);
        } catch (error) {
            console.error('Erro ao carregar responsáveis:', error);
            return this.createField({
                type: 'select',
                name: 'responsavel.id',
                label: 'Responsável',
                required: false,
                options: [
                    { value: '', label: 'Erro ao carregar responsáveis', disabled: true }
                ]
            }, selectedValue);
        }
    }
}