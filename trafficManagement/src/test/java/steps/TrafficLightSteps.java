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
    import services.TrafficLightService;

    public class TrafficLightSteps {
        TrafficLightService trafficLightService = new TrafficLightService();

        public TrafficLightSteps() {
        }

        @Dado("que estou autenticado com um usuário {string}")
        public void queEstouAutenticadoComUmUsuário(String userRole) {
        }

        @E("possuo os seguintes dados do semáforo:")
        public void possuoOsSeguintesDadosDoSemáforo(List<Map<String, String>> rows) {
            Iterator var2 = rows.iterator();

            while(var2.hasNext()) {
                Map<String, String> columns = (Map)var2.next();
                this.trafficLightService.setFieldsDelivery((String)columns.get("campo"), (String)columns.get("valor"));
            }

        }

        @Quando("eu envio uma requisição POST para o endpoint {string}")
        public void euEnvioUmaRequisiçãoPOSTParaOEndpoint(String endPoint) {
            this.trafficLightService.createTrafficLight(endPoint);
        }

        @Então("o status code da resposta deve ser {int}")
        public void oStatusCodeDaRespostaDeveSer(int statusCode) {
            Assert.assertEquals((long)statusCode, (long)this.trafficLightService.response.statusCode());
        }

        @E("o corpo de resposta da API deve retornar a mensagem {string}")
        public void oCorpoDeRespostaDaAPIDeveRetornarAMensagem(String message) {
            if (this.trafficLightService.response != null && this.trafficLightService.response.getBody() != null) {
                String responseBody = this.trafficLightService.response.getBody().asString();
                if (!responseBody.isEmpty()) {
                    ErrorMessageCreateTrafficLightModel errorMessageModel = (ErrorMessageCreateTrafficLightModel)this.trafficLightService.gson.fromJson(responseBody, ErrorMessageCreateTrafficLightModel.class);
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
            Long trafficLightId = this.trafficLightService.retrieveTrafficLightId();
            if (trafficLightId != null) {
                this.trafficLightService.trafficLightId = trafficLightId;
            } else {
                Assert.fail("Falha ao recuperar o ID do semáforo criado.");
            }

        }

        @Quando("eu enviar a requisição com o trafficLightId para o endpoint {string} de deleção de entrega")
        public void euEnviarARequisiçãoComOTrafficLightIdParaOEndpointDeDeleçãoDeEntrega(String endPoint) {
            this.trafficLightService.deleteTrafficLight(this.trafficLightService.trafficLightId);
        }

        @Dado("que eu tenha os seguintes dados do semáforo:")
        public void queEuTenhaOsSeguintesDadosDoSemáforo(List<Map<String, String>> rows) {
            Iterator var2 = rows.iterator();

            while(var2.hasNext()) {
                Map<String, String> columns = (Map)var2.next();
                this.trafficLightService.setFieldsDelivery((String)columns.get("campo"), (String)columns.get("valor"));
            }

        }

        @Quando("eu enviar a requisição para o endpoint {string} de cadastro de semáforo")
        public void euEnviarARequisiçãoParaOEndpointDeCadastroDeSemáforo(String endPoint) {
        }

        @E("que o arquivo de contrato esperado é o {string}")
        public void queOArquivoDeContratoEsperadoÉO(String contract) throws IOException, JSONException {
            this.trafficLightService.setContract(contract);
        }

        @Então("a resposta da requisição deve estar em conformidade com o contrato selecionado")
        public void aRespostaDaRequisiçãoDeveEstarEmConformidadeComOContratoSelecionado() throws IOException, JSONException {
            Set<ValidationMessage> validateResponse = this.trafficLightService.validateResponseAgainstSchema();
            Assert.assertTrue("O contrato está inválido. Erros encontrados: " + String.valueOf(validateResponse), validateResponse.isEmpty());
        }

        @E("que o id do semáforo inexistente é {int}")
        public void queOIdDoSemáforoInexistenteÉ(int trafficLightId) {
        }

        @Quando("eu envio uma requisição PUT para o endpoint {string}")
        public void euEnvioUmaRequisiçãoPUTParaOEndpoint(String endPoint) {
            this.trafficLightService.activateTrafficLights(999);
        }

        @Quando("envio uma requisição PUT para {string}")
        public void envioUmaRequisiçãoPUTPara(String endPoint) {
            this.trafficLightService.activateTrafficLights(this.trafficLightService.trafficLightId);
        }

        @Então("o status da resposta deve ser {int}")
        public void oStatusDaRespostaDeveSer(int statusCode) {
            Assert.assertEquals((long)statusCode, (long)this.trafficLightService.response.statusCode());
        }

        @Quando("eu envio uma requisição DEL para o endpoint {string}")
        public void euEnvioUmaRequisiçãoDELParaOEndpoint(String endPoint) {
            this.trafficLightService.deleteTrafficLight(999);
        }

        @Quando("envio uma requisição GET para {string}")
        public void envioUmaRequisiçãoGETPara(String endpoint) {
            this.trafficLightService.getById(this.trafficLightService.trafficLightId);
        }

        @Quando("eu envio uma requisição GET para o endpoint {string}")
        public void euEnvioUmaRequisiçãoGETParaOEndpoint(String endPoint) {
            this.trafficLightService.getById(999);
        }

        @Então("a resposta da requisição deve estar em conformidade com o contrato de usuário selecionado")
        public void aRespostaDaRequisiçãoDeveEstarEmConformidadeComOContratoDeUsuárioSelecionado() throws JSONException, IOException {
            Set<ValidationMessage> validateResponse = this.trafficLightService.validateResponseAgainstSchema();
            Assert.assertTrue("O contrato está inválido. Erros encontrados: " + String.valueOf(validateResponse), validateResponse.isEmpty());
        }
    }
