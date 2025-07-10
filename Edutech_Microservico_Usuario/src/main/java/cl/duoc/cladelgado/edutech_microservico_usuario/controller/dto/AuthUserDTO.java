package cl.duoc.cladelgado.edutech_microservico_usuario.controller.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthUserDTO {
    @JsonProperty("email")
    private String email;
    @JsonProperty("token")
    private String token;
}
