package net.softclear.admin.nomina.repository;

import net.softclear.admin.nomina.exception.PersonaNoEncontradaException;
import net.softclear.admin.nomina.model.Persona;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonaInMemoryRepository {
    private List<Persona> personas;

    public PersonaInMemoryRepository(List<Persona> personas) {
        this.personas = personas;
    }

    public PersonaInMemoryRepository() {
        this(new ArrayList<>());
    }

    public List<Persona> getPersonas() {
        return personas;
    }


    public void agregarPersona(Persona persona) {
        personas.add(persona);
    }

    public void removerPersonaPorId(Long id) {
        personas.remove(getIndex(id));
    }

    public void actualizarPersona(Persona persona) {
        personas.set(getIndex(persona.getId()), persona);
    }

    private int getIndex(Long id) {
        for (int idx = 0; idx < personas.size(); idx++) {
            Persona p = personas.get(idx);
            if (p.getId().equals(id)) {
                return idx;
            }
        }
        throw new PersonaNoEncontradaException();
    }
}
