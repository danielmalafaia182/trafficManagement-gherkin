    //
    // Source code recreated from a .class file by IntelliJ IDEA
    // (powered by FernFlower decompiler)
    //

    package steps;

    import com.networknt.schema.ValidationMessage;
    import io.cucumber.java.pt.Dado;
    import io.cucumber.java.pt.E;
    import io.cucumber.java.pt.Então;
    import io.cucumber.java.pt.Quando;
    import java.io.IOException;
    import java.util.Iterator;
    import java.util.List;
    import java.util.Map;
    import java.util.Set;
    import model.ErrorMessageCreateTrafficLightModel;
    import org.json.JSONException;
    import org.junit.Assert;
    import services.CreateTrafficLightService;

    public class CreateTrafficLightSteps {
        CreateTrafficLightService createTrafficLightService = new CreateTrafficLightService();

        public CreateTrafficLightSteps() {
        }

        @Dado("que estou autenticado com um usuário {string}")
        public void queEstouAutenticadoComUmUsuário(String userRole) {
        }

        @E("possuo os seguintes dados do semáforo:")
        public void possuoOsSeguintesDadosDoSemáforo(List<Map<String, String>> rows) {
            Iterator var2 = rows.iterator();

            while(var2.hasNext()) {
                Map<String, String> columns = (Map)var2.next();
                this.createTrafficLightService.setFieldsDelivery((String)columns.get("campo"), (String)columns.get("valor"));
            }

        }

        @Quando("eu envio uma requisição POST para o endpoint {string}")
        public void euEnvioUmaRequisiçãoPOSTParaOEndpoint(String endPoint) {
            this.createTrafficLightService.createTrafficLight(endPoint);
        }

        @Então("o status code da resposta deve ser {int}")
        public void oStatusCodeDaRespostaDeveSer(int statusCode) {
            Assert.assertEquals((long)statusCode, (long)this.createTrafficLightService.response.statusCode());
        }

        @E("o corpo de resposta da API deve retornar a mensagem {string}")
        public void oCorpoDeRespostaDaAPIDeveRetornarAMensagem(String message) {
            if (this.createTrafficLightService.response != null && this.createTrafficLightService.response.getBody() != null) {
                String responseBody = this.createTrafficLightService.response.getBody().asString();
                if (!responseBody.isEmpty()) {
                    ErrorMessageCreateTrafficLightModel errorMessageModel = (ErrorMessageCreateTrafficLightModel)this.createTrafficLightService.gson.fromJson(responseBody, ErrorMessageCreateTrafficLightModel.class);
                    Assert.assertEquals(message, errorMessageModel.getError());
                } else {
                    Assert.fail("A resposta da API não contém um corpo JSON.");
                }
            } else {
                Assert.fail("A resposta da API é nula ou não contém um corpo.");
            }

        }

        @Dado("que eu recupere o trafficLightId da entrega criada no contexto")
        public void queEuRecupereOTrafficLightIdDaEntregaCriadaNoContexto() {
            Long trafficLightId = this.createTrafficLightService.retrieveTrafficLightId();
            if (trafficLightId != null) {
                this.createTrafficLightService.trafficLightId = trafficLightId;
            } else {
                Assert.fail("Falha ao recuperar o ID do semáforo criado.");
            }

        }

        @Quando("eu enviar a requisição com o trafficLightId para o endpoint {string} de deleção de entrega")
        public void euEnviarARequisiçãoComOTrafficLightIdParaOEndpointDeDeleçãoDeEntrega(String endPoint) {
            this.createTrafficLightService.deleteTrafficLight(this.createTrafficLightService.trafficLightId);
        }

        @Dado("que eu tenha os seguintes dados do semáforo:")
        public void queEuTenhaOsSeguintesDadosDoSemáforo(List<Map<String, String>> rows) {
            Iterator var2 = rows.iterator();

            while(var2.hasNext()) {
                Map<String, String> columns = (Map)var2.next();
                this.createTrafficLightService.setFieldsDelivery((String)columns.get("campo"), (String)columns.get("valor"));
            }

        }

        @Quando("eu enviar a requisição para o endpoint {string} de cadastro de semáforo")
        public void euEnviarARequisiçãoParaOEndpointDeCadastroDeSemáforo(String arg0) {
        }

        @E("que o arquivo de contrato esperado é o {string}")
        public void queOArquivoDeContratoEsperadoÉO(String contract) throws IOException, JSONException {
            this.createTrafficLightService.setContract(contract);
        }

        @Então("a resposta da requisição deve estar em conformidade com o contrato selecionado")
        public void aRespostaDaRequisiçãoDeveEstarEmConformidadeComOContratoSelecionado() throws IOException, JSONException {
            Set<ValidationMessage> validateResponse = this.createTrafficLightService.validateResponseAgainstSchema();
            Assert.assertTrue("O contrato está inválido. Erros encontrados: " + String.valueOf(validateResponse), validateResponse.isEmpty());
        }
    }
