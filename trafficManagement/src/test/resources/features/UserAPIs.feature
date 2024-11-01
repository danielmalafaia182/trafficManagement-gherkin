# language: pt

Funcionalidade: Cadastro de um usuário
  Para conseguir se autenticar no sistema
  Quero que o registro de um novo usuário seja salvo corretamente no sistema
  Cenario: Cadastro bem-sucedido de um novo usuário
    E possuo os seguintes dados do usuário:
      | campo      | valor |
      | email   | blabla@gmail.com   |
      | password  | 123456   |
    Quando eu envio uma requisição POST para o endpoint "/auth/register" de cadastro de usuário
    Então o status code da resposta do endpoint de criação do usuário deve ser 201
    E que eu recupere o userId da entrega criada no contexto

  Cenário: Consulta bem sucedida de um usuário pelo id
    Quando envio uma requisição GET para o endpoint "/api/users/:{id}" de consulta de usuário
    E que o arquivo de contrato de usuário esperado é o "Consulta bem sucedida de um usuário pelo id"
    Então o status code da resposta do endpoint de consulta do usuário deve ser 200
    Então a resposta da requisição de consulta de usuário deve estar em conformidade com o contrato selecionado

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

  Cenário: Deve ser possível deletar um usuário
    Quando eu enviar a requisição com o usuarioId para o endpoint "/api/users/:{id}" de deleção de usuário
    Então o status code da resposta de deleção com sucesso de usuário deve ser 204