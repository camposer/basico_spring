package net.softclear.admin.nomina.controller;

import net.softclear.admin.nomina.model.Persona;
import net.softclear.admin.nomina.service.PersonaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/personas")
public class PersonaController {
    private final PersonaService service;

    public PersonaController(PersonaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Persona> getPersonas() {
        return service.getPersonas();
    }

    @PostMapping
    public ResponseEntity postPersona(@RequestBody Persona persona) {
        try {
            service.agregarPersona(persona);
        } catch (RuntimeException e) {
            return new ResponseEntity(INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
