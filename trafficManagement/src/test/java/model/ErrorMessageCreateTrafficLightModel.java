package model;
import com.google.gson.annotations.Expose;
import lombok.Data;
@Data
public class ErrorMessageCreateTrafficLightModel {
    @Expose
    private String error;
}