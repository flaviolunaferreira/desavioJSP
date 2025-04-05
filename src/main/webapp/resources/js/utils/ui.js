

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
    static showToast(title, message, bgClass, duration) {
        // Remove toasts existentes
        $('.ui-toast').remove();

        const toastId = 'toast-' + Date.now();
        const toast = $(`
        <div class="ui-toast position-fixed bottom-0 end-0 p-3" style="z-index: 1100">
            <div id="${toastId}" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header ${bgClass} text-white">
                    <strong class="me-auto">${title}</strong>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body bg-light">
                    ${message}
                </div>
            </div>
        </div>
    `);

        $('body').append(toast);

        // Inicializa o toast manualmente
        const toastElement = document.getElementById(toastId);
        const bsToast = new bootstrap.Toast(toastElement, {
            autohide: true,
            delay: duration
        });

        bsToast.show();

        // Remove o elemento do DOM após esconder
        toastElement.addEventListener('hidden.bs.toast', () => {
            toast.remove();
        });

        // Configura o fechamento automático
        setTimeout(() => {
            toast.find('.toast').toast('hide');
            setTimeout(() => toast.remove(), 500);
        }, duration);
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
}