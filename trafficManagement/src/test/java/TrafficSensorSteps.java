import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import model.ErrorMessageCreateTrafficLightModel;
import org.junit.Assert;
import services.TrafficSensorService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TrafficSensorSteps {
    TrafficSensorService trafficSensorService = new TrafficSensorService();

    @Dado("que possuo os seguintes dados do sensor:")
    public void quePossuoOsSeguintesDadosDoSensor(List<Map<String, String>> rows) {
        Iterator var2 = rows.iterator();

        while(var2.hasNext()) {
            Map<String, String> columns = (Map)var2.next();
            this.trafficSensorService.setFieldsDelivery((String)columns.get("campo"), (String)columns.get("valor"));
        }
    }

    @Quando("eu envio uma requisição POST para o endpoint {string} de criação de sensores")
    public void euEnvioUmaRequisiçãoPOSTParaOEndpointDeCriaçãoDeSensores(String endPoint) {
        this.trafficSensorService.createTrafficSensor(endPoint);
    }

    @Então("o status code da resposta de criação do sensor deve ser {int}")
    public void oStatusCodeDaRespostaDeCriaçãoDoSensorDeveSer(int statusCode) {
        Assert.assertEquals((long)statusCode, (long)this.trafficSensorService.response.statusCode());
    }

    @E("o corpo de resposta da API de criação de sensor deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDaAPIDeCriaçãoDeSensorDeveRetornarAMensagem(String message) {
        if (this.trafficSensorService.response != null && this.trafficSensorService.response.getBody() != null) {
            String responseBody = this.trafficSensorService.response.getBody().asString();
            if (!responseBody.isEmpty()) {
                ErrorMessageCreateTrafficLightModel errorMessageModel = (ErrorMessageCreateTrafficLightModel)this.trafficSensorService.gson.fromJson(responseBody, ErrorMessageCreateTrafficLightModel.class);
                Assert.assertEquals(message, errorMessageModel.getError());
            } else {
                Assert.fail("A resposta da API não contém um corpo JSON.");
            }
        } else {
            Assert.fail("A resposta da API é nula ou não contém um corpo.");
        }
    }

    @Dado("que o id do sensor inexistente é {int}")
    public void queOIdDoSensorInexistenteÉ(int trafficSensorId) {
    }

    @Quando("eu envio uma requisição DEL para o endpoint {string} de deleção de sensores")
    public void euEnvioUmaRequisiçãoDELParaOEndpointDeDeleçãoDeSensores(String endPoint) {
        this.trafficSensorService.deleteTrafficSensor(this.trafficSensorService.trafficSensorId);
    }

    @Então("o status code da resposta de deleção de sensor deve ser {int}")
    public void oStatusCodeDaRespostaDeDeleçãoDeSensorDeveSer(int statusCode) {
        Assert.assertEquals((long)statusCode, (long)this.trafficSensorService.response.statusCode());
    }

    @E("o corpo de resposta da API de deleção de sensor deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDaAPIDeDeleçãoDeSensorDeveRetornarAMensagem(String message) {
        if (this.trafficSensorService.response != null && this.trafficSensorService.response.getBody() != null) {
            String responseBody = this.trafficSensorService.response.getBody().asString();
            if (!responseBody.isEmpty()) {
                ErrorMessageCreateTrafficLightModel errorMessageModel = (ErrorMessageCreateTrafficLightModel)this.trafficSensorService.gson.fromJson(responseBody, ErrorMessageCreateTrafficLightModel.class);
                Assert.assertEquals(message, errorMessageModel.getError());
            } else {
                Assert.fail("A resposta da API não contém um corpo JSON.");
            }
        } else {
            Assert.fail("A resposta da API é nula ou não contém um corpo.");
        }
    }
}
