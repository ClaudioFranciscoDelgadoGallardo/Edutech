package cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SOPORTE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Soporte {
    @Id
    @JsonProperty("soporte")
    @Column(name = "ID_SOPORTE")
    private Long soporte;

    @JsonProperty("nombreSoporte")
    @Column(name = "NOMBRE_SOPORTE", nullable = false)
    private String nombreSoporte;

    @JsonProperty("apellidoSoporte")
    @Column(name = "APELLIDO_SOPORTE", nullable = false)
    private String apellidoSoporte;

}