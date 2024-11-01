# language: pt
@regressivo
Funcionalidade: Cadastro de sensor de tráfego
  Para gerenciar os sensores no sistema
  Como usuário autenticado
  Quero que o registro de um novo sensor seja salvo corretamente no sistema

  Cenário: Cadastro bem-sucedido de um novo sensor de tráfego
    Dado que possuo os seguintes dados do sensor:
      | campo      | valor |
      | latitude   | 1.0   |
      | longitude  | 2.0   |
    Quando eu envio uma requisição POST para o endpoint "/api/trafficSensors" de criação de sensores
    Então o status code da resposta de criação do sensor deve ser 201

  Cenário: Deleção mal-sucedida de um sensor ao passar um id inexistente
    Dado  que o id do sensor inexistente é 999
    Quando eu envio uma requisição DEL para o endpoint "/api/trafficSensors/:{trafficSensorsId}" de deleção de sensores
    Então o status code da resposta de deleção de sensor deve ser 500
    E o corpo de resposta da API de deleção de sensor deve retornar a mensagem "Internal Server Error"

  Cenario: Consulta mal sucessida de um semáforo ao passar um id inexistente
    Dado que estou autenticado com um usuário "Admin"
    E que o id do semáforo inexistente é 999
    Quando eu envio uma requisição GET para o endpoint "/api/trafficLights/:{trafficLightId}"
    Então o status code da resposta deve ser 404
    E o corpo de resposta da API deve retornar a mensagem "Traffic Light ID not found!"

  Cenário: Cadastro mal-sucedido de um novo sensor ao passar o campo latitude inválido
    Dado que possuo os seguintes dados do sensor:
      | campo      | valor |
      | latitude   | Abacaxi   |
      | longitude  | 2.0   |
    Quando eu envio uma requisição POST para o endpoint "/api/trafficSensors" de criação de sensores
    Então o status code da resposta de criação do sensor deve ser 400
    E o corpo de resposta da API de criação de sensor deve retornar a mensagem "Dados da request estão em formato inválido."