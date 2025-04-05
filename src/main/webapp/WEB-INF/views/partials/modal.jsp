<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!-- Modal CRUD -->
<div class="modal fade" id="crudModal" tabindex="-1" aria-labelledby="crudModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
    <div class="modal-content bg-vscode-card">
    <div class="modal-header border-vscode-border">
    <h5 class="modal-title text-vscode-blue" id="crudModalLabel"></h5>
<button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
</div>
<div class="modal-body" id="crudModalBody">
    <!-- O conteúdo será carregado dinamicamente via JavaScript -->
</div>
<div class="modal-footer border-vscode-border">
    <button type="button" class="btn btn-vscode-secondary" data-bs-dismiss="modal">Fechar</button>
    <button type="button" class="btn btn-vscode-blue" id="saveButton">Salvar</button>
</div>
</div>
</div>
</div>

<!-- Modal de Confirmação -->
<div class="modal fade" id="confirmModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content bg-vscode-card">
            <div class="modal-header border-vscode-border">
                <h5 class="modal-title text-vscode-blue">Confirmação</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="confirmModalBody">
                <!-- Mensagem será injetada via JavaScript -->
            </div>
            <div class="modal-footer border-vscode-border">
                <button type="button" class="btn btn-vscode-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-vscode-blue" id="confirmActionButton">Confirmar</button>
            </div>
        </div>
    </div>
</div>

<!-- Loading Overlay -->
<div id="loading-overlay" class="position-fixed top-0 start-0 w-100 h-100 d-flex justify-content-center align-items-center d-none" style="background-color: rgba(0,0,0,0.5); z-index: 9999;">
    <div class="spinner-border text-vscode-blue" role="status">
        <span class="visually-hidden">Carregando...</span>
    </div>
</div>