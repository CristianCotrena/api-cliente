<h1 align="center">FormaNT - API Cliente</h1>

FormaNT - API Cliente é uma aplicação web baseada em Java que permite a um utilizador registar alunos de uma academia. A API é um microserviço.

<hr/>

# Aplicação

- Sistema de Edição/Atualização: pode-se adicionar, editar e remover alunos, funcionários e quaisquer pessoas físicas que não sejam fornecedores.

<hr/>

# CRUD:

- Método POST
- Método PUT
- Método GET - listar clientes
- Método GET - buscar um cliente

<hr/>

# SWAGGER:
A url para acessar a documentação da API é a seguinte: http://localhost:8080/swagger-ui/index.html

<hr/>

# Tecnologia

FormaNT - API Cliente é construido usando as seguintes tecnologias:

BACKEND:
- Java: versão 17;
- PostgreSQL: ;
- Spring Boot: versão 3.1.3;

<hr/>

# Como executar o projeto

## Passo 1: Baixar e extrair o código

Primeiramente, baixe o código do website e extraia o arquivo ZIP para uma pasta no seu sistema local.

## Passo 2: Faça as configurações necessárias

### 🐘 Configurar o banco de dados
Esta aplicação usa o PostgreSQL como DB rodando em um sistema na núvem. Você precisará instalar o sistema e criar um banco de dados para a aplicação.

🚨 NOTE: O banco de dados vem configurado no projeto para rodar na porta 7502. Se você estiver usando outra porta, altere o arquivo "application.properties" para a porta que você está usando.

- PORTA: 7502
- USUARIO: postgres
- SENHA: rJej9B4DEEdZHmHI6qOM

## Passo 3: Execute seu projeto

Abra seu editor de código (Como o IntelliJ), navegue até o diretório do projeto e execute a aplicação.

<hr/>

# 🚨 Avisos Importantes

- 🚨 O Tomcat está configurado para rodar na porta 8080, então, além do PostgreSQL na porta 7502, certifique-se de que não tenha nenhum outro aplicativo rodando nesta porta (8080). Caso haja, faça as alterações necessárias (application.properties).
- 🚨 O projeto já vem com algumas dependências previamente instaladas. Caso seja necessário realizar alterações, lembre-se que o mesmo foi desenvolvido seguindo as configurações acima.