package cl.duoc.cladelgado.edutech_microservico_usuario.controller;

import cl.duoc.cladelgado.edutech_microservico_usuario.controller.dto.passwordDTO;
import cl.duoc.cladelgado.edutech_microservico_usuario.service.UserService;
import cl.duoc.cladelgado.edutech_microservico_usuario.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User crearUsuario(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/allusers")
    public List<User> obtenerTodos() {
        return userService.findAll();
    }

    @GetMapping("/{rut}")
    public User obtenerPorRut(@PathVariable long rut) {
        return userService.findByRut(rut);
    }

    @PutMapping("/{rut}")
    public void actualizarUsuario(@PathVariable long rut, @RequestBody User userRequest) {
        userService.partialUpdate(rut, userRequest);
    }


    @PatchMapping("/{rut}")
    public void actualizarParcialUsuario(@PathVariable long rut, @RequestBody User userRequest) {
        userService.partialUpdate(rut, userRequest);
    }

    @DeleteMapping("/{rut}")
    public void eliminarUsuario(@PathVariable long rut) {
        userService.remove(rut);
    }

    @PutMapping("/{rut}/password")
    public void actualizarPassword(@PathVariable long rut, @RequestBody passwordDTO body) {
        userService.updatePassword(rut, body.getPassword());
    }
}

