package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import model.UserModel;
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

public class UserService {
    private final UserModel userModel = new UserModel();
    public final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .serializeSpecialFloatingPointValues()
            .create();
    private final String baseUrl = "http://localhost:8080";
    public Response response;
    public Long userId;
    String schemasPath = "src/test/resources/schemas/";
    JSONObject jsonSchema;
    private final ObjectMapper mapper = new ObjectMapper();

    public void setFieldsDelivery(String field, String value) {
        switch (field) {
            case "email" -> {
                    userModel.setEmail(value);
            }
            case "password" -> {
                    userModel.setPassword(value);
            }
            default -> throw new IllegalStateException("Unexpected field: " + field);
        }
    }

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

    public void registerUser(String endPoint) {
        String url = baseUrl + endPoint;
        String bodyToSend = gson.toJson(userModel);
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(bodyToSend)
                .when()
                .post(url)
                .then()
                .extract()
                .response();

        if (response == null || response.getBody() == null) {
            System.out.println("Erro: A resposta para criação de usuário é nula.");
        } else {
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody().asString());
        }
    }

    public Long retrieveUserId() {
        if (response != null && response.getStatusCode() == 201) {
            UserModel createdUser = gson.fromJson(response.getBody().asString(), UserModel.class);
            return createdUser.getUserId();
        } else {
            throw new IllegalStateException("Erro: não foi possível recuperar o ID do usuário. A resposta está inválida.");
        }
    }

    public void getById(long userId) {
        String url = baseUrl + "/api/users/" + userId;
        String token = obterTokenAutenticacao();
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    public void deleteTUser(long userId) {
        String url = baseUrl + "/api/users/" + userId;
        String token = obterTokenAutenticacao();

        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(url)
                .then()
                .extract()
                .response();

        if (response == null || response.getBody() == null) {
            System.out.println("Erro: A resposta para deleção de usuário é nula.");
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
            case "Cadastro bem-sucedido de um novo usuário" -> jsonSchema = loadJsonFromFile(schemasPath + "successful-created-user.json");
            case "Consulta bem sucedida de um usuário pelo id" -> jsonSchema = loadJsonFromFile(schemasPath + "successful-created-user.json");
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
