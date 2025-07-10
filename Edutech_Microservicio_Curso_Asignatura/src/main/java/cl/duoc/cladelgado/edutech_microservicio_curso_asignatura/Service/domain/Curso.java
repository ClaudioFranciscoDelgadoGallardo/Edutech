package cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CURSO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Curso {

    @Id
    @JsonProperty("curso")
    @Column(name = "ID_CURSO")
    private Long curso;

    @JsonProperty("nombreCurso")
    @Column(name = "NOMBRE_CURSO", nullable = false)
    private String nombreCurso;

    @JsonProperty("rutUsuario")
    @Column(name = "USUARIO_RUT_US", nullable = false)
    private Long rutUsuario;
}
