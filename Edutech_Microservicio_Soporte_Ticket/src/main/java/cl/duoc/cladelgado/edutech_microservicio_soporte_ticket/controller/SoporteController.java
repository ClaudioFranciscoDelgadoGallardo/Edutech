package cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.controller;

import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service.domain.Soporte;
import cl.duoc.cladelgado.edutech_microservicio_soporte_ticket.service.SoporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/soportes")
public class SoporteController {

    @Autowired
    private SoporteService soporteService;


    @PostMapping
    public Soporte crearSoporte(@RequestBody Soporte soporte) {
        return soporteService.create(soporte);
    }


    @GetMapping("/allsoportes")
    public List<Soporte> obtenerTodos() {
        return soporteService.findAll();
    }

    /** READ ONE */
    @GetMapping("/{soporte}")
    public Soporte obtenerPorId(@PathVariable String soporte) {
        return soporteService.findById(soporte);
    }

    /** FULL UPDATE */
    @PutMapping("/{soporte}")
    public void actualizarSoporte(@PathVariable String soporte,
                                  @RequestBody Soporte soporteRequest) {
        soporteService.partialUpdate(soporte, soporteRequest);
    }

    /** PARTIAL UPDATE */
    @PatchMapping("/{soporte}")
    public void actualizarParcialSoporte(@PathVariable String soporte,
                                         @RequestBody Soporte soporteRequest) {
        soporteService.partialUpdate(soporte, soporteRequest);
    }

    /** DELETE */
    @DeleteMapping("/{soporte}")
    public void eliminarSoporte(@PathVariable String soporte) {
        soporteService.remove(soporte);
    }
}
