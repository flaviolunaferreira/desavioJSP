/**
 * Classe base para construção de formulários
 */
class FormBuilder {
    constructor() {
        if (new.target === FormBuilder) {
            throw new Error("FormBuilder é uma classe abstrata e não pode ser instanciada diretamente.");
        }
    }

    /**
     * Método abstrato para obter a estrutura do formulário
     */
    static getForm(data, action, options = {}) {
        throw new Error("Método getForm deve ser implementado pelas classes filhas");
    }

    /**
     * Cria um campo de formulário baseado na configuração
     */
    static createFormField(fieldConfig, value = '') {
        const { type, id, label, required, options, ...attrs } = fieldConfig;

        let fieldHtml = '';
        const commonAttrs = `id="${id}" name="${id}" ${required ? 'required' : ''} 
                           class="form-control bg-vscode-card text-light" 
                           ${attrs.disabled ? 'disabled' : ''}`;

        switch(type) {
            case 'text':
            case 'number':
            case 'date':
            case 'datetime-local':
                fieldHtml = `<input type="${type}" ${commonAttrs} value="${value || ''}">`;
                break;

            case 'textarea':
                fieldHtml = `<textarea ${commonAttrs} rows="3">${value || ''}</textarea>`;
                break;

            case 'select':
                const optionsHtml = options.map(opt =>
                    `<option value="${opt.value}" ${value === opt.value ? 'selected' : ''}>
                        ${opt.label}
                    </option>`
                ).join('');
                fieldHtml = `<select ${commonAttrs}>${optionsHtml}</select>`;
                break;

            case 'checkbox':
                fieldHtml = `<input type="checkbox" id="${id}" name="${id}" 
                                  class="form-check-input" ${value ? 'checked' : ''}>`;
                break;

            default:
                throw new Error(`Tipo de campo não suportado: ${type}`);
        }

        return `
            <div class="mb-3">
                <label for="${id}" class="form-label">${label}${required ? ' *' : ''}</label>
                ${fieldHtml}
            </div>
        `;
    }

    /**
     * Cria o container do formulário com campos e botão de erro
     */
    static buildFormContainer(fieldsHtml, formId) {
        return `
            <form id="${formId}">
                ${fieldsHtml}
                <div class="alert alert-danger d-none" id="formError"></div>
            </form>
        `;
    }
}