# restapi-ejb

## Sobre

API REST desenvolvida com JAX-RS em ecosistema Jakarta EE utilizando EJB e Wildfly como servidor.

## Motivação

Foi decidido implementar a API REST em um ecosistema Jakarta EE pois JAX-RS é uma de suas implementações e além disso, utilizando esse ecostistema, é possível utilizar IoC Containers com o EJB e Wildfly, o que ajuda, principalmente, nas trasações que são realizadas no banco de dados.

## Configurações 

### Pre-requisitos:

1. Java 11+
2. Maven
3. PostgresSql
4. Servidor Wildfly (de preferência a ultima versão)

### Configurações:

#### Banco de dados

1. Instale o postgresql 9 +
2. Configure usuário e senha (pode ser usado o padrão do postgres)
3. Acesse psql através do terminal
4. Crie o banco de dados reliazando o comando <code>CREATE DATABASE restapi_db;</code>

#### Servidor de Aplicação Wildfly

1. Inicie o servidor de aplicação acessando a pasta bin do Wildfly e executando o seguinte comando.

Linux: ./standalone.sh &

Windows: standalone.bat

2. Acesse localhost:8080

3. Acesse o painel administrativo clicando em Administrator Console

4. Insira usuário e senha. (Caso não possua siga esse tutorial: https://jboss-books.gitbooks.io/wildfly/content/instalacao/criando_usuario_de_gerenciamento.html)

5. Clique em "Deployment" e adicione o drive de conexão do postgresl em "Upload Deployment". 

6. Na raiz deste projeto há o drive para a conexão com o banco de dados postgresql (postgresql-42.2.23.jar). 

7. Após isso, vá em Configuration->Subsystems->Datasource && Drivers->Datasources e crie um datasource seguindo o passo a passo do Wildfly.

8. É MUITO IMPORTANTE que o datasouce possua essas configurações

Name: RestApiDS

JNDI Name: java:/RestApiDS

9. Faça o build do projeto e depois realize o deploy dele no Wildfly

## Instruções de uso

A API foi testada utilizando o POSTMAN, mas você pode utilizar a ferramenta de sua preferência.

A API REST possui apenas um endpoint com os quatro métodos HTTP (<code>GET</code>,<code>POST</code>,<code>PUT</code>,<code>DELETE</code>):

ENDPOINT: 
<code>http://localhost:8080/restapi-ejb-0.0.1-SNAPSHOT/clientes</code>

### GET

É possível realizar a requisição get utilizando um objeto JSON com um dos seguintes paramentros.

```json
{
    "nome": "jośe",
    "cpf": "111111111",
    "endereco": {
        "endereco": "RUA 1 CASA 25",
        "cidade": "Brasília",
        "estado": "DF"
    } 
}
```

### POST

Para inserir na base o objeto deve ter o seguinte formato:

```json
{
    "id": "2933417",
    "nome": "josé",
    "cpf": "111112222",
    "endereco": {
        "cidade": "Brasília",
        "endereco": "Asa norte",
        "estado": "DF",
        "idEndereco": 11,
        "numero": 0
    },
    
}
```


### PUT

Para atualizar o objeto obrigatoriemente precisa possuir o idCliente:

```json
{
    "idCliente": 7,
    "id": "2933417",
    "nome": "josé",
    "cpf": "111112222",
    "endereco": {
        "cidade": "Brasília",
        "endereco": "Asa norte",
        "estado": "DF",
        "idEndereco": 11,
        "numero": 0
    },
}
```
## DELETE

Para remover basta informar o idCliente para o seguinte endpoint

<code>http://localhost:8080/restapi-ejb-0.0.1-SNAPSHOT/clientes/{idCliente}</code>







