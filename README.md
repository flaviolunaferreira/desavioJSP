Sistema de Gestão de Projetos
📋 Visão Geral
Sistema completo para gerenciamento de projetos, equipes e tarefas com:

Backend Java/Spring Boot

Frontend JSP/Bootstrap

API REST documentada com Swagger

Quadro Kanban interativo

Dashboard com métricas

🚀 Tecnologias
Backend: Java 17, Spring Boot 3, JPA/Hibernate

Frontend: JSP, Bootstrap 5, jQuery, Chart.js

Banco de Dados: Compatível com H2, MySQL, PostgreSQL

Ferramentas: Maven, Swagger (OpenAPI 3)

🔧 Configuração
Pré-requisitos
JDK 17+

Maven 3.8+

Banco de dados configurado (application.properties)

Instalação
bash
Copy
git clone [[repositório](https://github.com/flaviolunaferreira/desavioJSP.git)]
cd projeto
mvn clean install
Execução
bash
Copy
mvn spring-boot:run
Acesse: http://localhost:8080

📚 Documentação da API (Swagger)
A API está documentada com Swagger (OpenAPI 3.0). Para acessar:

Execute a aplicação

Acesse: http://localhost:8080/swagger-ui.html

Endpoints documentados:

/api/projetos - Gestão de projetos

/api/pessoas - Gestão de membros

/api/tarefas - Gestão de tarefas

/api/membros - Associações entre pessoas e projetos

🛠️ Funcionalidades
Projetos
✅ CRUD completo

✅ Controle de status (7 estágios)

✅ Cálculo automático de risco

✅ Dashboard com gráficos

Pessoas
✅ Cadastro com validação de CPF

✅ Distinção entre gerentes/funcionários

✅ Restrições de exclusão

Tarefas
✅ Quadro Kanban com drag-and-drop

✅ Filtros avançados

✅ Progresso automático

📊 Estrutura do Código
Copy
src/
├── main/
│   ├── java/
│   │   └── com/portfolio/
│   │       ├── config/       # Configurações
│   │       ├── controller/   # Controladores REST/MVC
│   │       ├── dto/          # Objetos de transferência
│   │       ├── model/        # Entidades JPA
│   │       └── service/      # Lógica de negócio
│   └── resources/
│       └── application.properties # Config DB
├── webapp/
│   ├── resources/           # JS/CSS
│   └── WEB-INF/views/       # Páginas JSP
🔄 Rotas Principais
Rota	Descrição
/	Dashboard principal
/projetos	Listagem de projetos
/projetos/{id}	Detalhes do projeto
/tarefas	Listagem de tarefas
/pessoas	Gestão de membros
💡 Melhorias Futuras
Segurança

Implementar autenticação com JWT

Controle de acesso por perfis

Performance

Adicionar cache de consultas

Implementar paginação

Funcionalidades

Upload de anexos

Notificações em tempo real

Documentação

Adicionar exemplos de requests no Swagger

Documentar códigos de erro

🤝 Contribuição
Contribuições são bem-vindas! Siga os passos:

Fork do projeto

Crie sua branch (git checkout -b feature/foo)

Commit suas mudanças (git commit -am 'Add foo')

Push para a branch (git push origin feature/foo)

Crie um novo Pull Request

📄 Licença
MIT License - veja LICENSE.md para detalhes
