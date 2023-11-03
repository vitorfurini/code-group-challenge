<h1>Sistema de gerenciamento de projetos e pessoas</h1>

Este projeto foi desenvolvido a fins de desafio técnico para a Code Group;
O projeto consiste em ser uma API backend com front-end para gerenciar projetos e seus membros.

O projeto foi desenvolvido utilizando as seguintes tecnologias:
Java 17 <br>
Spring boot<br>
PostGreeSQL<br>
Swagger Open <br>
Lombok<br>
API REST<br>
Junit5<br>

<h2>Endpoints</h2>

GET /api/project: Retorna todos os projetos que estão cadastrados na base de dados.<br>
POST /api/project: Cria um novo projeto e salva na base de dados<br>
GET /api/project/{id}: Faz a busca na base por um projeto usando o ID dele.<br>
PUT /projetos/{id}: Atualiza um projeto cadastrado.<br>
PUT updateStatus/{id}: Atualiza o status de um projeto.<br>
DELETE /projetos/{id}: Exclui um projeto.<br>
GET /projetos/{id}/risco: Obtém o risco associado a um projeto.<br>
PATCH /associateEmployee: Adiciona um membro específico usando o seu ID a um projeto.<br>

POST /membros: Cria um novo membro.

<h2>Configuração e Execução</h2>

<h3>Pré-requisitos</h3>

Java 17
Maven
PostgreSQL

<h2>Para rodar a aplicação</h2>

Clone o Repositório
git clone https://github.com/vitorfurini/code-group-challenge.git

configure a conexão do banco de dados em src/main/resources/application.properties:

Rode o comando

./mvnw spring-boot:run<br>

<h2>Para abrir as páginas HTML</h2>
Vá no diretório do projeto e em templates abra as páginas HTML para testar, tudo irá funcionar se o projeto estiver rodando.


<h2>Documentação da API</h2><br>
http://localhost:8080/swagger-ui.html#/
