package net.softclear.admin.nomina.service;

import net.softclear.admin.nomina.exception.PersonaNoEncontradaException;
import net.softclear.admin.nomina.model.Persona;
import net.softclear.admin.nomina.repository.PersonaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {
    private final PersonaRepository repository;

    public PersonaService(PersonaRepository repository) {
        this.repository = repository;
    }

    public List<Persona> getPersonas() {
        return repository.getPersonas();
    }

    public void agregarPersona(Persona persona) {
        repository.agregarPersona(persona);
    }

    public void actualizarPersona(Persona persona) {
        try {
            repository.actualizarPersona(persona);
        } catch (PersonaNoEncontradaException e) {
            throw new RuntimeException(e);
        }
    }

    public void removerPersonaPorId(Long id) {
        repository.removerPersonaPorId(id);
    }
}
