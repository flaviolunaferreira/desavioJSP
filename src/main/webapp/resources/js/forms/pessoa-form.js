class PessoaForm extends FormBuilder {
    static async getForm(data, action, options) {
        const fields = [
            `<input type="hidden" name="id" value="${data?.id || ''}">`,
            this.createField({
                type: 'text',
                name: 'nome',
                label: 'Nome',
                required: true
            }, data?.nome),
            this.createField({
                type: 'date',
                name: 'dataNascimento',
                label: 'Data de Nascimento'
            }, data?.dataNascimento ? new Date(data.dataNascimento).toISOString().split('T')[0] : ''),
            this.createField({
                type: 'text',
                name: 'cpf',
                label: 'CPF',
                placeholder: '000.000.000-00'
            }, data?.cpf),
            this.createField({
                type: 'checkbox',
                name: 'funcionario',
                label: 'Funcionário'
            }, data?.funcionario),
            this.createField({
                type: 'checkbox',
                name: 'gerente',
                label: 'Gerente'
            }, data?.gerente)
        ];

        return this.buildForm(fields, 'pessoaForm');
    }
}