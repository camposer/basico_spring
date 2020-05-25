package net.softclear.admin.nomina.repository;

import net.softclear.admin.nomina.model.Persona;

import java.util.List;

public interface PersonaRepository {
    List<Persona> getPersonas();

    void agregarPersona(Persona persona);

    void removerPersonaPorId(Long id);

    void actualizarPersona(Persona persona);
}
