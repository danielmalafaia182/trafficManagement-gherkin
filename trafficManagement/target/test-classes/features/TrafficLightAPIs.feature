# language: pt
@regressivo
Funcionalidade: Cadastro de semáforo
  Para gerenciar semáforos no sistema
  Como usuário autenticado
  Quero que o registro de um novo semáforo seja salvo corretamente no sistema
  Cenário: Cadastro bem-sucedido de um novo semáforo
    Dado que estou autenticado com um usuário "Admin"
    E possuo os seguintes dados do semáforo:
      | campo      | valor |
      | latitude   | 1.0   |
      | longitude  | 2.0   |
    Quando eu envio uma requisição POST para o endpoint "/api/trafficLights"
    Então o status code da resposta deve ser 201

  Cenário: Cadastro mal-sucedido de um novo semáforo ao passar o campo latitude inválido
    Dado que estou autenticado com um usuário "Admin"
    E possuo os seguintes dados do semáforo:
      | campo      | valor |
      | latitude   | Abacaxi   |
      | longitude  | 2.0   |
    Quando eu envio uma requisição POST para o endpoint "/api/trafficLights"
    Então o status code da resposta deve ser 400
    E o corpo de resposta da API deve retornar a mensagem "Dados da request estão em formato inválido."

  Cenário: Ativação mal-sucedida de um semáforo ao passar um id inexistente
    Dado que estou autenticado com um usuário "Admin"
    E que o id do semáforo inexistente é 999
    Quando eu envio uma requisição PUT para o endpoint "/api/trafficLights/activateTrafficLights/:{trafficLightId}"
    Então o status code da resposta deve ser 404
    E o corpo de resposta da API deve retornar a mensagem "Traffic Light ID not found!"

  Cenário: Deleção mal-sucedida de um semáforo ao passar um id inexistente
    Dado que estou autenticado com um usuário "Admin"
    E que o id do semáforo inexistente é 999
    Quando eu envio uma requisição DEL para o endpoint "/api/trafficLights/:{trafficLightId}"
    Então o status code da resposta deve ser 500
    E o corpo de resposta da API deve retornar a mensagem "Internal Server Error"

  Cenario: Consulta mal sucessida de um semáforo ao passar um id inexistente
    Dado que estou autenticado com um usuário "Admin"
    E que o id do semáforo inexistente é 999
    Quando eu envio uma requisição GET para o endpoint "/api/trafficLights/:{trafficLightId}"
    Então o status code da resposta deve ser 404
    E o corpo de resposta da API deve retornar a mensagem "Traffic Light ID not found!"
