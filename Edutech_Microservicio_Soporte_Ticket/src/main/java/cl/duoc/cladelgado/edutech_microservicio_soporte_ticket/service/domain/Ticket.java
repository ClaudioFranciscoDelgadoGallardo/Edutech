package cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TICKET")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ticket {
    @Id
    @JsonProperty("ticket")
    @Column(name = "NRO_TICKET")
    private Long ticket;

    @JsonProperty("soporte")
    @Column(name = "SOPORTE_ID_SOPORTE", nullable = false)
    private String soporte;

    @JsonProperty("rutUsuario")
    @Column(name = "USUARIO_RUT_US", nullable = false)
    private Long rutUsuario;

    @JsonProperty("causa")
    @Column(name = "CAUSA", nullable = false)
    private String causa;

    @JsonProperty("caso")
    @Column(name = "NOMBREDELCASO", nullable = false)
    private String caso;

    @JsonProperty("descripcion")
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;
}