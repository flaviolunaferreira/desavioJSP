class Ui {
    static showSuccess(message, duration = 3000) {
        const toast = $(`
            <div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11">
                <div class="toast show" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header bg-vscode-green text-white">
                        <strong class="me-auto">Sucesso</strong>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body bg-vscode-card">
                        ${message}
                    </div>
                </div>
            </div>
        `);

        $('body').append(toast);
        setTimeout(() => toast.remove(), duration);
    }

    static showError(message, duration = 5000) {
        const toast = $(`
            <div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11">
                <div class="toast show" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header bg-vscode-red text-white">
                        <strong class="me-auto">Erro</strong>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body bg-vscode-card">
                        ${message}
                    </div>
                </div>
            </div>
        `);

        $('body').append(toast);
        setTimeout(() => toast.remove(), duration);
    }

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
            }
        } else {
            loader.remove();
        }
    }

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

    static getRiskColorClass(risco) {
        switch(risco) {
            case 'ALTO': return 'bg-vscode-red';
            case 'MEDIO': return 'bg-vscode-yellow';
            case 'BAIXO': return 'bg-vscode-green';
            default: return 'bg-vscode-gray';
        }
    }

    static formatDate(dateString) {
        if (!dateString) return 'N/A';
        const date = new Date(dateString);
        return date.toLocaleDateString('pt-BR');
    }

    static formatDateTime(dateString) {
        if (!dateString) return 'N/A';
        const date = new Date(dateString);
        return date.toLocaleString('pt-BR');
    }

    static confirm(message, callback) {
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
        const bsModal = new bootstrap.Modal(modal[0]);
        bsModal.show();

        modal.find('#confirmButton').click(() => {
            callback();
            bsModal.hide();
        });

        modal.on('hidden.bs.modal', () => {
            modal.remove();
        });
    }
}