package cl.duoc.cladelgado.edutech_microservico_usuario.controller.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class passwordDTO {
    @JsonProperty("password")
    private String password;

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
