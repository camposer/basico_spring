package net.softclear.admin.nomina.repository;

import net.softclear.admin.nomina.NominaApplication;
import net.softclear.admin.nomina.exception.PersonaNoEncontradaException;
import net.softclear.admin.nomina.model.Persona;
import net.softclear.admin.nomina.repository.jpa.PersonaJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Order.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

// Integration test
// TODO Incluir casos borde
@DataJpaTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        NominaApplication.class,
        PersonaDbRepositoryTest.Config.class
})
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
//@Transactional // auto rollback después de cada test
public class PersonaDbRepositoryTest {
    @Autowired
    private PersonaJpaRepository jpaRepository;

    @Autowired
    private PersonaRepository repository;

    private final Persona persona1 = new Persona(1L, "Rodo", "Campos");
    private final Persona persona2 = new Persona(2L, "Kike", "Campos");

    @BeforeEach
    public void setUp() {
        jpaRepository.deleteAll();
    }

    @Nested
    class GetPersona {
        @Test // happy path
        public void sinElementos_returnsEmptyList() {
            List<Persona> personas = repository.getPersonas();
            assertEquals(0, personas.size());
        }

        @Test
        public void conElementos_returnsElementos() {
            jpaRepository.save(persona1);
            jpaRepository.save(persona2);

            List<Persona> personas = repository.getPersonas();
            assertEquals(2, personas.size());
            assertTrue(personas.contains(persona1));
            assertTrue(personas.contains(persona2));
        }
    }

    @Nested
    class AgregarPersona {
        @Test
        public void success() {
            List<Persona> tmp = repository.getPersonas();

            repository.agregarPersona(persona1);
            repository.agregarPersona(persona2);

            List<Persona> personas = iterableAsList(jpaRepository.findAll());
            assertEquals(2, personas.size());
            assertTrue(personas.contains(persona1));
            assertTrue(personas.contains(persona2));
        }
    }

    @Nested
    class RemoverPersona {
        @Test
        public void success() {
            jpaRepository.save(persona1);
            jpaRepository.save(persona2);

            repository.removerPersonaPorId(persona1.getId());

            List<Persona> personas = iterableAsList(jpaRepository.findAll());
            assertEquals(1, personas.size());
            assertFalse(personas.contains(persona1));
            assertTrue(personas.contains(persona2));
        }

        @Test // sad path
        public void throwsExceptionsWhenIdDoesntExist() {
            assertThrows(PersonaNoEncontradaException.class,
                    () -> repository.removerPersonaPorId(1L));
        }
    }

    @Nested
    class ActualizarPersona {
        @Test
        public void success() {
            jpaRepository.save(persona1);
            jpaRepository.save(persona2);

            Persona personaModificada = new Persona(1L, "Juan", "Pérez");

            repository.actualizarPersona(personaModificada);

            List<Persona> personas = iterableAsList(jpaRepository.findAll());
            assertEquals(2, personas.size());
            assertTrue(personas.contains(personaModificada));
            assertTrue(personas.contains(persona2));

        }
    }

    @Test
    public void pruebitas_weon() {
        jpaRepository.save(persona1);
        jpaRepository.save(persona2);

        List<Persona> r = jpaRepository.findByNombreAndApellido("Rodo", "Campos");
        System.out.println(r);
        assertNotNull(r);

        Pageable pageable = PageRequest.of(1, 1, Sort.by(Order.asc("nombre")));
        r = jpaRepository.findByNombreOrApellido("Rodo", "Campos", pageable);
        System.out.println(r);
        assertNotNull(r);

        r = jpaRepository.findContainsText("odo");
        System.out.println(r);
        assertNotNull(r);

        List<String> apellidos = jpaRepository.findAllApellidos();
        System.out.println(apellidos);
        assertNotNull(apellidos);
    }

    @Configuration
    static class Config {
        @Bean
        public PersonaRepository personaDbRepository(
                PersonaJpaRepository jpaRepository
        ) {
            return new PersonaDbRepository(jpaRepository);
        }
    }

    private <T extends Object> List<T> iterableAsList(Iterable<T> iterable) {
        List<T> result = new ArrayList<>();
        iterable.forEach(result::add);
        return result;
    }

}
