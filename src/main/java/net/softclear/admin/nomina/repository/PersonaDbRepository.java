package net.softclear.admin.nomina.repository;

import net.softclear.admin.nomina.exception.PersonaNoEncontradaException;
import net.softclear.admin.nomina.model.Persona;
import net.softclear.admin.nomina.repository.jpa.PersonaJpaRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonaDbRepository implements PersonaRepository {
    private final PersonaJpaRepository repository;

    public PersonaDbRepository(PersonaJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Persona> getPersonas() {
        List<Persona> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public void agregarPersona(Persona persona) { // TODO Controlar actualizaciones
        repository.save(persona);
    }

    @Override
    public void removerPersonaPorId(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new PersonaNoEncontradaException();
        }
    }

    @Override
    public void actualizarPersona(Persona persona) {
        repository.save(persona);
    }
}
