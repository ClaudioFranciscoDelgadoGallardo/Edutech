package cl.duoc.cladelgado.edutech_microservicio_curso_asignatura.Service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ASIGNATURA")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Asignatura {

    @Id
    @JsonProperty("asignatura")
    @Column(name = "ID_ASIGNATURA")
    private Long asignatura;

    @JsonProperty("curso")
    @Column(name = "CURSO_ID_CURSO", nullable = false)
    private Long curso;
}