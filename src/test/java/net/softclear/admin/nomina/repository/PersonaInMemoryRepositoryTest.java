package net.softclear.admin.nomina.repository;

import net.softclear.admin.nomina.exception.PersonaNoEncontradaException;
import net.softclear.admin.nomina.model.Persona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class PersonaInMemoryRepositoryTest {
    @Autowired
    private List<Persona> personasInterna;

    @Autowired
    private PersonaRepository repository;

    private final Persona persona1 = new Persona(1L, "Rodo", "Campos");
    private final Persona persona2 = new Persona(2L, "Kike", "Campos");

    @Test // happy path
    public void getPersonas_sinElementos_returnsEmptyList() {
        List<Persona> personas = repository.getPersonas();
        assertEquals(0, personas.size());
    }

    @Test
    public void getPersonas_conElementos_returnsElementos() {
        personasInterna.add(persona1);
        personasInterna.add(persona2);

        List<Persona> personas = repository.getPersonas();
        assertEquals(2, personas.size());
        assertTrue(personas.contains(persona1));
        assertTrue(personas.contains(persona2));
    }

    @Test
    public void agregarPersona_success() {
        repository.agregarPersona(persona1);
        repository.agregarPersona(persona2);

        assertEquals(2, personasInterna.size());
        assertTrue(personasInterna.contains(persona1));
        assertTrue(personasInterna.contains(persona2));
    }

    @Test
    public void removerPersona_success() {
        personasInterna.add(persona1);
        personasInterna.add(persona2);

        repository.removerPersonaPorId(persona1.getId());

        assertEquals(1, personasInterna.size());
        assertFalse(personasInterna.contains(persona1));
        assertTrue(personasInterna.contains(persona2));
    }

    @Test // sad path
    public void removerPersona_throwsExceptionsWhenIdDoesntExist() {
        assertThrows(PersonaNoEncontradaException.class,
                () -> repository.removerPersonaPorId(1L));
    }

    @Test
    public void actualizarPersona_success() {
        personasInterna.add(persona1);
        personasInterna.add(persona2);

        Persona personaModificada = new Persona(1L, "Juan", "Pérez");

        repository.actualizarPersona(personaModificada);

        assertEquals(2, personasInterna.size());
        assertTrue(personasInterna.contains(personaModificada));
        assertTrue(personasInterna.contains(persona2));

    }

    @Test // sad path
    public void actualizarPersona_throwsExceptionsWhenIdDoesntExist() {
        // TODO Implementar test
    }

    // TODO Incluir casos borde

    @Configuration
    static class Config {
        @Bean
        public List<Persona> personasInterna() {
            // Otra opción: Retornar un mock de lista
            // La opción actual utiliza fake object
            return new ArrayList<>();
        }

        @Bean
        // @Scope("singleton") => default
        public PersonaRepository personaInMemoryRepositoryForTest(
                List<Persona> personasInterna
        ) {
            return new PersonaInMemoryRepository(personasInterna);
        }
    }

}
