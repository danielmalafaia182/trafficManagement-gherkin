package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import model.TrafficLightModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class CreateTrafficLightService {
    private final TrafficLightModel trafficLightModel = new TrafficLightModel();
    public final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .serializeSpecialFloatingPointValues()
            .create();
    private final String baseUrl = "http://localhost:8080";
    public Response response;
    public Long trafficLightId;
    String schemasPath = "src/test/resources/schemas/";
    JSONObject jsonSchema;
    private final ObjectMapper mapper = new ObjectMapper();

    // Configura os campos do Model
    public void setFieldsDelivery(String field, String value) {
        switch (field) {
            case "latitude" -> {
                try {
                    trafficLightModel.setLatitude(Double.parseDouble(value));
                } catch (NumberFormatException e) {
                    trafficLightModel.setLatitude(Double.NaN);
                }
            }
            case "longitude" -> {
                try {
                    trafficLightModel.setLongitude(Double.parseDouble(value));
                } catch (NumberFormatException e) {
                    trafficLightModel.setLongitude(Double.NaN);
                }
            }
            default -> throw new IllegalStateException("Unexpected field: " + field);
        }
    }

    // Autenticação para obtenção do token
    private String obterTokenAutenticacao() {
        RestAssured.defaultParser = Parser.JSON;

        String email = "dmalafaia.iff@gmail.com";
        String password = "123456";

        Response authResponse = given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}")
                .when()
                .post(baseUrl + "/auth/login");

        if (authResponse == null || authResponse.getBody() == null) {
            System.out.println("Erro: Falha na autenticação. A resposta do token é nula.");
            return null;
        }

        String token = authResponse.path("token");

        System.out.println("Auth Token: " + token);

        if (token == null) {
            throw new IllegalStateException("Erro: Token não foi gerado. Verifique as credenciais.");
        }

        return token;
    }

    // Cria um novo semáforo
    public void createTrafficLight(String endPoint) {
        String url = baseUrl + endPoint;
        String bodyToSend = gson.toJson(trafficLightModel);
        String token = obterTokenAutenticacao();

        if (token == null) {
            System.out.println("Erro: A criação do semáforo foi abortada. Token de autenticação está nulo.");
            return;
        }

        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(bodyToSend)
                .when()
                .post(url)
                .then()
                .extract()
                .response();

        if (response == null || response.getBody() == null) {
            System.out.println("Erro: A resposta para criação de semáforo é nula.");
        } else {
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody().asString());
            System.out.println("Response Headers: " + response.getHeaders().toString());
        }
    }

    public Long retrieveTrafficLightId() {
        if (response != null && response.getStatusCode() == 201) {
            TrafficLightModel createdTrafficLight = gson.fromJson(response.getBody().asString(), TrafficLightModel.class);
            return createdTrafficLight.getTrafficLightId();
        } else {
            throw new IllegalStateException("Erro: não foi possível recuperar o ID do semáforo. A resposta está inválida.");
        }
    }

    public void deleteTrafficLight(Long trafficLightId) {
        String url = baseUrl + "/api/trafficLights/" + trafficLightId;
        String token = obterTokenAutenticacao();

        if (token == null) {
            System.out.println("Erro: A deleção do semáforo foi abortada. Token de autenticação está nulo.");
            return;
        }

        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(url)
                .then()
                .extract()
                .response();

        if (response == null || response.getBody() == null) {
            System.out.println("Erro: A resposta para deleção de semáforo é nula.");
        } else {
            System.out.println("Response Status Code: " + response.getStatusCode());
        }
    }

    private JSONObject loadJsonFromFile(String filePath) throws IOException, JSONException {
        String jsonContent;
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            jsonContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
        JSONTokener tokener = new JSONTokener(jsonContent);
        return new JSONObject(tokener);
    }


    public void setContract(String contract) throws IOException, JSONException {
        switch (contract) {
            case "Cadastro bem-sucedido de um novo semáforo" -> jsonSchema = loadJsonFromFile(schemasPath + "successful-created-traffic-light.json");
            default -> throw new IllegalStateException("Unexpected contract" + contract);
        }
    }

    public Set<ValidationMessage> validateResponseAgainstSchema() throws IOException, JSONException {
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema schema = schemaFactory.getSchema(jsonSchema.toString());
        JsonNode jsonResponseNode = mapper.readTree(jsonResponse.toString());
        Set<ValidationMessage> schemaValidationErrors = schema.validate(jsonResponseNode);
        return schemaValidationErrors;
    }
}
