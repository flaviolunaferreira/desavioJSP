

class Ui {
    /**
     * Mostra um indicador de carregamento
     * @param {boolean} show - Se true, mostra o loading. Se false, esconde.
     */
    static showLoading(show = true) {
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

    /**
     * Mostra uma mensagem de sucesso
     * @param {string} message - Mensagem a ser exibida
     * @param {number} duration - Duração em milissegundos (padrão: 3000)
     */
    static showSuccess(message, duration = 3000) {
        this.showToast('Sucesso', message, 'bg-vscode-green', duration);
    }

    /**
     * Mostra uma mensagem de erro
     * @param {string} message - Mensagem a ser exibida
     * @param {number} duration - Duração em milissegundos (padrão: 5000)
     */
    static showError(message, duration = 5000) {
        this.showToast('Erro', message, 'bg-vscode-red', duration);
    }

    /**
     * Mostra um toast de notificação
     * @private
     */
    static showToast(title, message, type = 'info', duration = 3000) {
        const toastId = 'toast-' + Date.now();
        const icon = {
            success: 'bi-check-circle',
            error: 'bi-exclamation-triangle',
            warning: 'bi-exclamation-circle',
            info: 'bi-info-circle'
        }[type];

        const toast = $(`
        <div id="${toastId}" class="toast show" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header bg-${type} text-white">
                <i class="bi ${icon} me-2"></i>
                <strong class="me-auto">${title}</strong>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body bg-dark text-white">
                ${message}
            </div>
        </div>
    `);

        $('#toast-container').append(toast);

        setTimeout(() => {
            toast.fadeOut(() => toast.remove());
        }, duration);
    }

    static showApiError(error) {
        const message = error.responseJSON?.message ||
            error.responseText ||
            error.statusText ||
            'Erro desconhecido';
        this.showToast('Erro', message, 'error', 5000);
    }

    /**
     * Mostra um diálogo de confirmação
     * @param {string} message - Mensagem de confirmação
     * @param {function} callback - Função a ser executada quando confirmado
     */
    static confirm(message, callback) {
        // Remove modais de confirmação existentes
        $('#confirmModal').remove();

        const modal = $(`
            <div class="modal fade" id="confirmModal" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content bg-vscode-card">
                        <div class="modal-header border-vscode-border">
                            <h5 class="modal-title text-vscode-blue">Confirmação</h5>
                            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            ${message}
                        </div>
                        <div class="modal-footer border-vscode-border">
                            <button type="button" class="btn btn-vscode-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-vscode-blue" id="confirmButton">Confirmar</button>
                        </div>
                    </div>
                </div>
            </div>
        `);

        $('body').append(modal);

        const bsModal = new bootstrap.Modal(document.getElementById('confirmModal'));
        bsModal.show();

        $('#confirmButton').off('click').on('click', () => {
            callback();
            bsModal.hide();
        });

        modal.on('hidden.bs.modal', () => {
            modal.remove();
        });
    }

    /**
     * Obtém a classe CSS para um status
     * @param {string} status - Status do item
     * @return {string} Classe CSS correspondente
     */
    static getStatusColorClass(status) {
        switch(status) {
            case 'EM_ANDAMENTO': return 'bg-vscode-blue';
            case 'CONCLUIDA':
            case 'CONCLUIDO':
            case 'ENCERRADO': return 'bg-vscode-green';
            case 'BLOQUEADA':
            case 'CANCELADO': return 'bg-vscode-red';
            default: return 'bg-vscode-gray';
        }
    }

    /**
     * Obtém a classe CSS para um nível de risco
     * @param {string} risco - Nível de risco
     * @return {string} Classe CSS correspondente
     */
    static getRiskColorClass(risco) {
        switch(risco) {
            case 'ALTO': return 'bg-vscode-red';
            case 'MEDIO': return 'bg-vscode-yellow';
            case 'BAIXO': return 'bg-vscode-green';
            default: return 'bg-vscode-gray';
        }
    }

    static confirmDelete(message, callback) {
        $('#confirmModalBody').text(message);
        $('#confirmActionButton').removeClass('btn-vscode-blue').addClass('btn-vscode-red').text('Excluir');

        const modal = new bootstrap.Modal(document.getElementById('confirmModal'));
        modal.show();

        $('#confirmActionButton').off('click').on('click', () => {
            callback();
            modal.hide();
        });
    }
}