package cl.duoc.cladelgado.edutech_microservico_usuario.service.domain;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USUARIO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @JsonProperty("rut")
    @Column(name = "RUT_US")
    private long rut;
    @JsonProperty("dv")
    @Column(name = "DV_US", nullable = false)
    private String dv;

    @JsonProperty("nombre")
    @Column(name = "NOMBRES_US",nullable = false)
    private String nombre;
    @JsonProperty("apellido")
    @Column(name = "APELLIDOS_US", nullable = false)
    private String apellido;

    @JsonProperty("correo")
    @Column(name = "CORREOELECTRONICO_US",unique = true, nullable = false)
    private String correo;

    @JsonProperty("rol")
    @Column(name = "ROL",nullable = false)
    private String role;

    @JsonProperty("password")
    @Column(name = "CONTRASENA",nullable = false)
    private String password;
}