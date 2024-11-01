package steps;


import com.networknt.schema.ValidationMessage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import model.ErrorMessageCreateTrafficLightModel;
import org.json.JSONException;
import org.junit.Assert;
import services.UserService;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class UserSteps {
    UserService userService = new UserService();

    @E("possuo os seguintes dados do usuário:")
    public void possuoOsSeguintesDadosDoUsuário(List<Map<String, String>> rows) {
            Iterator var2 = rows.iterator();

            while(var2.hasNext()) {
                Map<String, String> columns = (Map)var2.next();
                this.userService.setFieldsDelivery((String)columns.get("campo"), (String)columns.get("valor"));
            }

        }

    @Então("o status code da resposta do endpoint de criação do usuário deve ser {int}")
    public void oStatusCodeDaRespostaDoEndpointDeCriaçãoDoUsuárioDeveSer(int statusCode) {
        Assert.assertEquals((long)statusCode, (long)this.userService.response.statusCode());
    }

    @Quando("eu envio uma requisição POST para o endpoint {string} de cadastro de usuário")
    public void euEnvioUmaRequisiçãoPOSTParaOEndpointDeCadastroDeUsuário(String endPoint) {
        this.userService.registerUser(endPoint);
    }

    @E("o corpo de resposta da API de cadastro do usuário deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDaAPIDeCadastroDoUsuárioDeveRetornarAMensagem(String message) {
        if (this.userService.response != null && this.userService.response.getBody() != null) {
            String responseBody = this.userService.response.getBody().asString();
            if (!responseBody.isEmpty()) {
                ErrorMessageCreateTrafficLightModel errorMessageModel = (ErrorMessageCreateTrafficLightModel)this.userService.gson.fromJson(responseBody, ErrorMessageCreateTrafficLightModel.class);
                Assert.assertEquals(message, errorMessageModel.getError());
            } else {
                Assert.fail("A resposta da API não contém um corpo JSON.");
            }
        } else {
            Assert.fail("A resposta da API é nula ou não contém um corpo.");
        }

    }

    @E("que o arquivo de contrato de cadastro de usuário esperado é o {string}")
    public void queOArquivoDeContratoDeCadastroDeUsuárioEsperadoÉO(String contract) throws JSONException, IOException {
        this.userService.setContract(contract);
    }

    @Então("a resposta da requisição de cadastro de usuário deve estar em conformidade com o contrato selecionado")
    public void aRespostaDaRequisiçãoDeCadastroDeUsuárioDeveEstarEmConformidadeComOContratoSelecionado() throws JSONException, IOException {
        Set<ValidationMessage> validateResponse = this.userService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. Erros encontrados: " + String.valueOf(validateResponse), validateResponse.isEmpty());
    }

    @E("que eu recupere o userId da entrega criada no contexto")
    public void queEuRecupereOUserIdDaEntregaCriadaNoContexto() {
        Long userId = this.userService.retrieveUserId();
        if (userId != null) {
            this.userService.userId = userId;
        } else {
            Assert.fail("Falha ao recuperar o ID do semáforo criado.");
        }
    }

    @Quando("eu enviar a requisição com o usuarioId para o endpoint {string} de deleção de usuário")
    public void euEnviarARequisiçãoComOUsuarioIdParaOEndpointDeDeleçãoDeUsuário(String endPoint) {
        this.userService.deleteUser(this.userService.userId);
    }

    @Então("o status code da resposta de deleção com sucesso de usuário deve ser {int}")
    public void oStatusCodeDaRespostaDeDeleçãoComSucessoDeUsuárioDeveSer(int statusCode) {
        Assert.assertEquals((long)statusCode, (long)this.userService.response.statusCode());
    }

    @Quando("envio uma requisição GET para o endpoint {string} de consulta de usuário")
    public void envioUmaRequisiçãoGETParaOEndpointDeConsultaDeUsuário(String endPoint) {
        this.userService.getById(this.userService.userId);

    }

    @E("que o arquivo de contrato de usuário esperado é o {string}")
    public void queOArquivoDeContratoDeUsuárioEsperadoÉO(String contract) throws JSONException, IOException {
        this.userService.setContract(contract);
    }

    @Então("o status code da resposta do endpoint de consulta do usuário deve ser {int}")
    public void oStatusCodeDaRespostaDoEndpointDeConsultaDoUsuárioDeveSer(int statusCode) {
        Assert.assertEquals((long)statusCode, (long)this.userService.response.statusCode());
    }

    @Então("a resposta da requisição de consulta de usuário deve estar em conformidade com o contrato selecionado")
    public void aRespostaDaRequisiçãoDeConsultaDeUsuárioDeveEstarEmConformidadeComOContratoSelecionado() throws JSONException, IOException {
        Set<ValidationMessage> validateResponse = this.userService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. Erros encontrados: " + String.valueOf(validateResponse), validateResponse.isEmpty());
    }

    @Dado("que o id do usuário inexistente é {int}")
    public void queOIdDoUsuárioInexistenteÉ(int userId) {
    }

    @Então("o status code da resposta de deleleção de usuário deve ser {int}")
    public void oStatusCodeDaRespostaDeDeleleçãoDeUsuárioDeveSer(int statusCode) {
        Assert.assertEquals((long)statusCode, (long)this.userService.response.statusCode());
    }

    @E("o corpo de resposta da API de deleção de usuário deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDaAPIDeDeleçãoDeUsuárioDeveRetornarAMensagem(String message) {
        if (this.userService.response != null && this.userService.response.getBody() != null) {
            String responseBody = this.userService.response.getBody().asString();
            if (!responseBody.isEmpty()) {
                ErrorMessageCreateTrafficLightModel errorMessageModel = (ErrorMessageCreateTrafficLightModel)this.userService.gson.fromJson(responseBody, ErrorMessageCreateTrafficLightModel.class);
                Assert.assertEquals(message, errorMessageModel.getError());
            } else {
                Assert.fail("A resposta da API não contém um corpo JSON.");
            }
        } else {
            Assert.fail("A resposta da API é nula ou não contém um corpo.");
        }
    }

    @Então("o status code da resposta da consulta de usuário deve ser {int}")
    public void oStatusCodeDaRespostaDaConsultaDeUsuárioDeveSer(int statusCode) {
        Assert.assertEquals((long)statusCode, (long)this.userService.response.statusCode());
    }

    @E("o corpo de resposta da API de consulta de usuário deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDaAPIDeConsultaDeUsuárioDeveRetornarAMensagem(String message) {
        if (this.userService.response != null && this.userService.response.getBody() != null) {
            String responseBody = this.userService.response.getBody().asString();
            if (!responseBody.isEmpty()) {
                ErrorMessageCreateTrafficLightModel errorMessageModel = (ErrorMessageCreateTrafficLightModel)this.userService.gson.fromJson(responseBody, ErrorMessageCreateTrafficLightModel.class);
                Assert.assertEquals(message, errorMessageModel.getError());
            } else {
                Assert.fail("A resposta da API não contém um corpo JSON.");
            }
        } else {
            Assert.fail("A resposta da API é nula ou não contém um corpo.");
        }
    }

    @Quando("envio uma requisição GET inválida para o endpoint {string} de consulta de usuário")
    public void envioUmaRequisiçãoGETInválidaParaOEndpointDeConsultaDeUsuário(String endpoint) {
        this.userService.getById(999);
    }

    @Quando("eu enviar a requisição inválida com o usuarioId para o endpoint {string} de deleção de usuário")
    public void euEnviarARequisiçãoInválidaComOUsuarioIdParaOEndpointDeDeleçãoDeUsuário(String endpoint) {
        this.userService.deleteUser(999);
    }
}
