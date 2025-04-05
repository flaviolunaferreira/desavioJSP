/**
 * Gerenciador de formulários com modais estáticos
 */
class FormManager {
    constructor() {
        this.formTitles = {
            projeto: 'Projeto',
            pessoa: 'Pessoa',
            tarefa: 'Tarefa',
            membro: 'Membro'
        };
        this.initModalEvents();
    }

    initModalEvents() {
        // Evento para abrir o modal com os dados corretos
        $(document).on('click', '[data-bs-toggle="modal"][data-bs-target="#crudModal"]', (e) => {
            const button = $(e.currentTarget);
            const type = button.data('type');
            const action = button.data('action');
            const id = button.data('id');
            const options = {
                projetoId: button.data('projeto-id') || null
            };

            this.openForm(type, action, id, options);
        });

        // Evento para salvar o formulário
        $('#saveButton').click(() => this.saveForm());
    }

    async openForm(type, action = 'criar', id = null, options = {}) {
        try {
            // Define o título do modal
            $('#crudModalLabel').text(`${action === 'criar' ? 'Novo' : 'Editar'} ${this.formTitles[type] || type}`);

            // Obtém o HTML do formulário
            const formHtml = await this.getFormHtml(type, action, id, options);
            $('#crudModalBody').html(formHtml);

            // Carrega dados se for edição
            if (action === 'editar' && id) {
                await this.loadFormData(type, id);
            }

            // Mostra o modal
            $('#crudModal').modal('show');
        } catch (error) {
            console.error('Erro ao abrir formulário:', error);
            this.showError('Erro ao carregar formulário');
        }
    }

    async getFormHtml(type, action, id, options) {
        const builders = {
            projeto: ProjetoForm,
            pessoa: PessoaForm,
            tarefa: TarefaForm,
            membro: MembroForm
        };

        const builder = builders[type];
        if (!builder) {
            throw new Error(`Tipo de formulário não suportado: ${type}`);
        }

        // Obtém os dados se for edição
        let data = null;
        if (id) {
            data = await this.fetchData(type, id);
        }

        return builder.getForm(data, action, options);
    }

    async loadFormData(type, id) {
        try {
            const data = await this.fetchData(type, id);
            this.populateForm(type, data);
        } catch (error) {
            console.error('Erro ao carregar dados:', error);
            this.showError('Erro ao carregar dados do formulário');
        }
    }

    async fetchData(type, id) {
        let url = `/api/${type}s/${id}`;

        if (type === 'membro' && id.includes('/')) {
            const [projetoId, pessoaId] = id.split('/');
            url = `/api/membros/projeto/${projetoId}/pessoa/${pessoaId}`;
        }

        return await ApiService.get(url);
    }

    populateForm(type, data) {
        const form = $(`#${type}Form`);
        if (!form.length) return;

        // Preenche os campos do formulário com os dados
        form.find('input, select, textarea').each(function() {
            const field = $(this);
            const fieldName = field.attr('name');

            if (!fieldName || !data) return;

            // Trata campos aninhados (como gerente.id)
            const value = fieldName.includes('.') ?
                this.getNestedValue(data, fieldName) :
                data[fieldName];

            if (field.attr('type') === 'checkbox') {
                field.prop('checked', Boolean(value));
            } else if (field.is('select')) {
                field.val(value).trigger('change');
            } else {
                field.val(value || '');
            }
        });
    }

    getNestedValue(obj, path) {
        return path.split('.').reduce((o, p) => o ? o[p] : null, obj);
    }

    async saveForm() {
        const form = $('#crudModalBody').find('form');
        const formId = form.attr('id');
        const type = formId.replace('Form', '');

        if (!formId || !type) {
            this.showError('Não foi possível identificar o tipo de formulário');
            return;
        }

        try {
            this.showLoading(true);

            const formData = this.getFormData(form);
            const response = await this.submitForm(type, formData);

            $('#crudModal').modal('hide');
            this.showSuccess(`${this.formTitles[type]} salvo com sucesso!`);

            // Dispara evento para atualizar as listagens
            $(document).trigger('formSaved', { type, data: response });
        } catch (error) {
            console.error('Erro ao salvar formulário:', error);
            this.showError(error.message || 'Erro ao salvar dados');
        } finally {
            this.showLoading(false);
        }
    }

    getFormData(form) {
        const data = {};

        form.find('input, select, textarea').each(function() {
            const field = $(this);
            const fieldName = field.attr('name');

            if (!fieldName) return;

            if (field.attr('type') === 'checkbox') {
                data[fieldName] = field.is(':checked');
            } else {
                data[fieldName] = field.val();
            }
        });

        return data;
    }

    async submitForm(type, data) {
        const endpoints = {
            projeto: data.id ? `/api/projetos/${data.id}` : '/api/projetos',
            pessoa: data.id ? `/api/pessoas/${data.id}` : '/api/pessoas',
            tarefa: data.id ? `/api/tarefas/${data.id}` : '/api/tarefas',
            membro: data.id ? `/api/membros/${data.id}` : '/api/membros'
        };

        const url = endpoints[type];
        if (!url) {
            throw new Error(`Endpoint não definido para o tipo: ${type}`);
        }

        return data.id ?
            await ApiService.put(url, data) :
            await ApiService.post(url, data);
    }

    /* Métodos auxiliares de UI */
    showLoading(show = true) {
        const loader = $('#loading-overlay');
        if (show) {
            if (loader.length === 0) {
                $('body').append(`
                    <div id="loading-overlay" class="position-fixed top-0 start-0 w-100 h-100 d-flex justify-content-center align-items-center" style="background-color: rgba(0,0,0,0.5); z-index: 9999;">
                        <div class="spinner-border text-vscode-blue" role="status">
                            <span class="visually-hidden">Carregando...</span>
                        </div>
                    </div>
                `);
            } else {
                loader.removeClass('d-none');
            }
        } else {
            loader.addClass('d-none');
        }
    }

    showSuccess(message, duration = 3000) {
        this.showToast('Sucesso', message, 'bg-vscode-green', duration);
    }

    showError(message, duration = 5000) {
        this.showToast('Erro', message, 'bg-vscode-red', duration);
    }

    showToast(title, message, bgClass, duration) {
        // Remove toasts existentes
        $('.ui-toast').remove();

        const toast = $(`
            <div class="ui-toast position-fixed bottom-0 end-0 p-3" style="z-index: 1100">
                <div class="toast show" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header ${bgClass} text-white">
                        <strong class="me-auto">${title}</strong>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body bg-vscode-card">
                        ${message}
                    </div>
                </div>
            </div>
        `);

        $('body').append(toast);

        // Configura o fechamento automático
        setTimeout(() => {
            toast.find('.toast').toast('hide');
            setTimeout(() => toast.remove(), 500);
        }, duration);
    }
}