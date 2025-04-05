class MembroForm extends FormBuilder {
    static async getForm(data, action, options) {
        const fields = [];

        // Campo de projeto (se não estiver definido nas opções)
        if (!options.projetoId) {
            fields.push(await this.createProjetoField(data?.projeto?.id));
        } else {
            fields.push(`<input type="hidden" name="projeto" value="${options.projetoId}">`);
        }

        fields.push(
            await this.createPessoaField(data?.pessoa?.id, options.projetoId),
            this.createField({
                type: 'text',
                name: 'funcao',
                label: 'Função'
            }, data?.funcao),
            this.createField({
                type: 'date',
                name: 'dataEntrada',
                label: 'Data de Entrada',
                required: true
            }, data?.dataEntrada ? new Date(data.dataEntrada).toISOString().split('T')[0] : '')
        );

        return this.buildForm(fields, 'membroForm');
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
                name: 'projeto',
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
                name: 'projeto',
                label: 'Projeto',
                required: true,
                options: [
                    { value: '', label: 'Erro ao carregar projetos' }
                ]
            }, selectedValue);
        }
    }

    static async createPessoaField(selectedValue, projetoId) {
        try {
            let pessoas = await PessoaApi.list();

            // Se tiver projetoId, remove pessoas que já são membros
            if (projetoId) {
                const membros = await MembroApi.listByProjeto(projetoId);
                const membrosIds = membros.map(m => m.pessoa.id);
                pessoas = pessoas.filter(p => !membrosIds.includes(p.id));
            }

            const options = pessoas.map(p => ({
                value: p.id,
                label: p.nome
            }));

            return this.createField({
                type: 'select',
                name: 'pessoa',
                label: 'Pessoa',
                required: true,
                options: [
                    { value: '', label: 'Selecione uma pessoa' },
                    ...options
                ]
            }, selectedValue);
        } catch (error) {
            console.error('Erro ao carregar pessoas:', error);
            return this.createField({
                type: 'select',
                name: 'pessoa',
                label: 'Pessoa',
                required: true,
                options: [
                    { value: '', label: 'Erro ao carregar pessoas' }
                ]
            }, selectedValue);
        }
    }
}