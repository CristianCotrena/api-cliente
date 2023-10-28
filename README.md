<h1 align="center">FormaNT - API Cliente</h1>

FormaNT - API Cliente é uma aplicação web baseada em Java que permite a um utilizador registar alunos de uma academia. A API é um microserviço.

<hr/>

# Aplicação

- Sistema de Edição/Atualização: pode-se adicionar, editar e remover alunos, funcionários e quaisquer pessoas físicas que não sejam fornecedores.

<hr/>

# CRUD:

- Método POST criado, recebendo dados através de jason e gerando uma id automaticamente, e preenchendo o status automaticamente com 1, podendo ser preenchido com 0
  - FORMA DE INSERÇÃO DOS DADOS: na criação de um cliente, o id é gerado automaticamente, e os dados são informado por um json
      {
        "nome": "Testando Inserção de Cliente", -> informado entre aspas, é uma string, pelo menos 6 caracteres divididos em duas palavras
        "dataNascimento": "1958-02-10", -> informado entre aspas, é uma string, formato ISO 8601 (yyyy-MM-dd)
        "email": "testando@email.com", -> informado entre aspas, é uma string
        "cpf": "12345678919", -> informado entre aspas, é uma string, todos os caracteres são numéricos e deve conter 11 obrigatoriamente
        "senhaCatraca": "7576" -> informado entre aspas, é uma string, todos os caracteres são numéricos e deve conter 4 obrigatoriamente
      }

- Método PUT criado, recebendo dados através de jason, informando uma id já existente, permitindo a alteração apenas do e-mail e da senha da catraca
  - FORMA DE INSERÇÃO DOS DADOS: a id é informada no endpoint, e os dados a serem alterados são recebido por um json
    {
      "email": "masouza@email.com", -> informado entre aspas, é uma string
      "senhaCatraca": "9999" -> informado entre aspas, é uma string, todos os caracteres são numéricos e deve conter 4 obrigatoriamente
    }

- Método GET criado para o serviço de listagem de clientes, funciona através do recebimento de parametros por query para consulta em banco de dados:
  - os parâmetros são data inicial, data final e pagina
  - as datas devem ser informadas de forma conjunta  no formato ISO 8601 (yyyy-MM-dd)
  - a data inicial deve ser inferior a data final
  - a paginação deve ser sempre informada, mesmo que seja 0, assim listará todos os registros
  - o json de retorno mostra o total de páginas e a página atual
  - devido a forma de se lidar com os dados em listas e paginações no Java, a página atual no retorno sempre será acrescida de 1 (exemplo: pagina informada = 2, pagina atual no retorno = 3)

- Método GET criado para o serviço de busca unitária de um cliente, funciona através do recebimento de parametros por query para consulta em banco de dados:
  - os parâmetros são id, email e cpf
  - podem ser recebidos separadamente ou todos em conjunto
  - deve ser informado ao menos um obrigatoriamente
  - o id deve ser válido conforme a estrutura do UUID, e deve existir no banco de dados
  - o email deve ser informado nesse formato: "testando@email.com"
  - o cpf deve ser informado nesse formato: "12345678919" -> todos os caracteres são numéricos e deve conter 11 obrigatoriamente

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