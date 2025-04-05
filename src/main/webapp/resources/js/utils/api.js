class ApiService {
    static async get(url) {
        try {
            const response = await $.ajax({
                url: url,
                method: 'GET',
                contentType: 'application/json'
            });
            return response;
        } catch (error) {
            console.error(`GET request failed to ${url}:`, error);
            throw this.handleError(error);
        }
    }

    static async post(url, data) {
        try {
            const response = await $.ajax({
                url: url,
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data)
            });
            return response;
        } catch (error) {
            console.error(`POST request failed to ${url}:`, error);
            throw this.handleError(error);
        }
    }

    static async put(url, data) {
        try {
            const response = await $.ajax({
                url: url,
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(data)
            });
            return response;
        } catch (error) {
            console.error(`PUT request failed to ${url}:`, error);
            throw this.handleError(error);
        }
    }

    static async patch(url, data) {
        try {
            const response = await $.ajax({
                url: url,
                method: 'PATCH',
                contentType: 'application/json',
                data: JSON.stringify(data)
            });
            return response;
        } catch (error) {
            console.error(`PATCH request failed to ${url}:`, error);
            throw this.handleError(error);
        }
    }

    static async delete(url) {
        try {
            const response = await $.ajax({
                url: url,
                method: 'DELETE',
                contentType: 'application/json'
            });
            return response;
        } catch (error) {
            console.error(`DELETE request failed to ${url}:`, error);
            throw this.handleError(error);
        }
    }

    static handleError(error) {
        if (error.responseJSON) {
            return new Error(error.responseJSON.message || 'Erro desconhecido na API');
        }
        return new Error(error.statusText || 'Falha na conexão com o servidor');
    }
}

// API específica para Projetos
class ProjetoApi {
    static list() {
        return ApiService.get('/api/projetos');
    }

    static get(id) {
        return ApiService.get(`/api/projetos/${id}`);
    }

    static create(data) {
        return ApiService.post('/api/projetos', data);
    }

    static update(id, data) {
        return ApiService.put(`/api/projetos/${id}`, data);
    }

    static updateStatus(id, status) {
        return ApiService.patch(`/api/projetos/${id}/status`, { status });
    }

    static delete(id) {
        return ApiService.delete(`/api/projetos/${id}`);
    }

    static countByStatus() {
        return ApiService.get('/api/projetos/status-count');
    }

    static countByRisk() {
        return ApiService.get('/api/projetos/risk-count');
    }
}

// API específica para Pessoas
class PessoaApi {
    static list() {
        return ApiService.get('/api/pessoas');
    }

    static get(id) {
        return ApiService.get(`/api/pessoas/${id}`);
    }

    static create(data) {
        return ApiService.post('/api/pessoas', data);
    }

    static update(id, data) {
        return ApiService.put(`/api/pessoas/${id}`, data);
    }

    static delete(id) {
        return ApiService.delete(`/api/pessoas/${id}`);
    }

    static listFuncionarios() {
        return ApiService.get('/api/pessoas/funcionarios');
    }

    static listGerentes() {
        return ApiService.get('/api/pessoas/gerentes');
    }
}

// API específica para Tarefas
class TarefaApi {
    static list() {
        return ApiService.get('/api/tarefas');
    }

    static get(id) {
        return ApiService.get(`/api/tarefas/${id}`);
    }

    static create(data) {
        return ApiService.post('/api/tarefas', data);
    }

    static update(id, data) {
        return ApiService.put(`/api/tarefas/${id}`, data);
    }

    static updateStatus(id, status) {
        return ApiService.patch(`/api/tarefas/${id}/status`, { status });
    }

    static delete(id) {
        return ApiService.delete(`/api/tarefas/${id}`);
    }

    static listRecentes() {
        return ApiService.get('/api/tarefas/recentes');
    }

    static listByProjeto(projetoId) {
        return ApiService.get(`/api/tarefas?projetoId=${projetoId}`);
    }
}

// API específica para Membros
class MembroApi {
    static list() {
        return ApiService.get('/api/membros');
    }

    static get(projetoId, pessoaId) {
        return ApiService.get(`/api/membros/projeto/${projetoId}/pessoa/${pessoaId}`);
    }

    static create(data) {
        return ApiService.post('/api/membros', data);
    }

    static update(projetoId, pessoaId, data) {
        return ApiService.put(`/api/membros/projeto/${projetoId}/pessoa/${pessoaId}`, data);
    }

    static delete(projetoId, pessoaId) {
        return ApiService.delete(`/api/membros/projeto/${projetoId}/pessoa/${pessoaId}`);
    }

    static listByProjeto(projetoId) {
        return ApiService.get(`/api/membros/projeto/${projetoId}`);
    }
}