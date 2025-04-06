/**
 * Classe base para construção de formulários
 */
class FormBuilder {
    static getForm(data, action, options = {}) {
        throw new Error("Método getForm deve ser implementado pelas classes filhas");
    }

    static createField(fieldConfig, value = '') {
        const { type, name, label, required, options, placeholder, ...attrs } = fieldConfig;
        const fieldId = `${name}Field`;
        const isCheckbox = type === 'checkbox';
        const isSmall = attrs.small;

        let fieldHtml = '';
        const commonAttrs = `id="${fieldId}" name="${name}" ${required ? 'required' : ''} 
                       class="form-control ${isCheckbox ? 'form-check-input' : ''} ${isSmall ? 'small-field' : ''}"
                       ${placeholder ? `placeholder="${placeholder}"` : ''}`;

        switch(type) {
            case 'text':
            case 'number':
            case 'date':
            case 'datetime-local':
            case 'email':
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
                fieldHtml = `<input type="checkbox" ${commonAttrs} ${value ? 'checked' : ''}>`;
                break;

            default:
                throw new Error(`Tipo de campo não suportado: ${type}`);
        }

        // Estrutura diferente para checkboxes
        if (isCheckbox) {
            return `
                <div class="form-check mb-3">
                    ${fieldHtml}
                    <label class="form-check-label" for="${fieldId}">${label}</label>
                </div>
            `;
        }

        return `
            <div class="mb-3">
                <label for="${fieldId}" class="form-label">${label}${required ? ' *' : ''}</label>
                ${fieldHtml}
            </div>
        `;
    }

    static buildForm(fields, formId) {
        return `
        <form id="${formId}" class="needs-validation" novalidate>
            <div class="form-row">
                ${fields.join('')}
            </div>
            <div class="alert alert-danger d-none" id="formError"></div>
        </form>
    `;
    }
}