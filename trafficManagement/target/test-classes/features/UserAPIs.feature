# language: pt

Funcionalidade: Cadastro de um usuário
  Para conseguir se autenticar no sistema
  Quero que o registro de um novo usuário seja salvo corretamente no sistema
  Contexto: Cadastro bem-sucedido de um novo usuário
    E possuo os seguintes dados do usuário:
      | campo      | valor |
      | email   | dmalai.b9@gmail.com   |
      | password  | 123456   |
    Quando eu envio uma requisição POST para o endpoint "/auth/register" de cadastro de usuário
    Então o status code da resposta do endpoint de criação do usuário deve ser 201
    E que eu recupere o userId da entrega criada no contexto

  Cenario: Validar contrato do cadastro bem-sucedido de usuário
    E possuo os seguintes dados do usuário:
      | campo      | valor |
      | email   | rm98123@fiap.com.br   |
      | password  | 123456   |
    Quando eu envio uma requisição POST para o endpoint "/auth/register" de cadastro de usuário
    Então  o status code da resposta do endpoint de criação do usuário deve ser 201
    E que o arquivo de contrato de cadastro de usuário esperado é o "Cadastro bem-sucedido de um novo usuário"
    Então a resposta da requisição de cadastro de usuário deve estar em conformidade com o contrato selecionado

  Cenário: Consulta bem sucedida de um usuário pelo id
    Quando envio uma requisição GET para o endpoint "/api/users/:{id}" de consulta de usuário
    E que o arquivo de contrato de usuário esperado é o "Consulta bem sucedida de um usuário pelo id"
    Então o status code da resposta do endpoint de consulta do usuário deve ser 200
    Então a resposta da requisição de consulta de usuário deve estar em conformidade com o contrato selecionado

  Cenário: Deleção mal-sucedida de um usuário ao passar um id inexistente
    Dado que o id do usuário inexistente é 9999
    Quando eu enviar a requisição inválida com o usuarioId para o endpoint "/api/users/:{id}" de deleção de usuário
    Então o status code da resposta de deleleção de usuário deve ser 500
    E o corpo de resposta da API de deleção de usuário deve retornar a mensagem "Internal Server Error"

  Cenário: Deve ser possível deletar um usuário
    Quando eu enviar a requisição com o usuarioId para o endpoint "/api/users/:{id}" de deleção de usuário
    Então o status code da resposta de deleção com sucesso de usuário deve ser 204

  Cenario: Consulta mal sucessida de um usuário ao passar um id inexistente
    Dado que o id do usuário inexistente é 999
    Quando envio uma requisição GET inválida para o endpoint "/api/users/:{id}" de consulta de usuário
    Então o status code da resposta da consulta de usuário deve ser 404
    E o corpo de resposta da API de consulta de usuário deve retornar a mensagem "User ID not found!"

  Cenário: Cadastro mal-sucedido de um novo usuário
    E possuo os seguintes dados do usuário:
      | campo      | valor |
      | email   | dmalafaia.iff9@gmail.com   |
      | password  | 123456   |
    Quando eu envio uma requisição POST para o endpoint "/auth/register" de cadastro de usuário
    Então o status code da resposta do endpoint de criação do usuário deve ser 409
    E o corpo de resposta da API de cadastro do usuário deve retornar a mensagem "Email already in use!"