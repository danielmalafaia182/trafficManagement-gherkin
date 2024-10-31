# language: pt

Funcionalidade: Gerenciar semáforo
  Como usuário da API
  Quero conseguir realizar operações de criação, deleção e atualização de semáforo
  Para que o registro e estado dos semáforos sejam controlados corretamente no sistema

  Contexto: Cadastro bem-sucedido de um novo semáforo
    Dado possuo os seguintes dados do semáforo:
      | campo      | valor |
      | latitude   | 1.0   |
      | longitude  | 2.0   |
    Quando eu envio uma requisição POST para o endpoint "/api/trafficLights"
    Então o status code da resposta deve ser 201
    E que eu recupere o trafficLightId da entrega criada no contexto

  Cenário: Deve ser possível deletar um semáforo
    Quando eu enviar a requisição com o trafficLightId para o endpoint "/api/trafficLights/:{trafficLightId}" de deleção de entrega
    Então o status code da resposta deve ser 204

  Cenário: Ativação bem sucedida de um semáforo
    Quando envio uma requisição PUT para "/api/trafficLights/activateTrafficLights/:{trafficLightId}"
    Então o status da resposta deve ser 200

  Cenário: Consulta bem sucedida de um semáforo pelo id
    Quando envio uma requisição GET para "/api/trafficLights/:{trafficLightId}"
    E que o arquivo de contrato esperado é o "Consulta bem sucedida de um semáforo pelo id"
    Então o status da resposta deve ser 200
    Então a resposta da requisição deve estar em conformidade com o contrato selecionado