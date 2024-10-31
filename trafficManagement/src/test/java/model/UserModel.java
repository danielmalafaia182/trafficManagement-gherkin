package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class UserModel {
    @Expose
    private Long userId;
    @Expose
    private String email;
    @Expose
    private String password;
}
