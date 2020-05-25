package net.softclear.admin.nomina.service;

import net.softclear.admin.nomina.exception.PersonaNoEncontradaException;
import net.softclear.admin.nomina.model.Persona;
import net.softclear.admin.nomina.repository.PersonaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class PersonaServiceTest {
    @MockBean // crea un mock y mételo en el contexto como primary
    private PersonaRepository repository;

    @Autowired
    private PersonaService service;

    private final Persona persona1 = new Persona(1L, "Rodo", "Campos");
    private final Persona persona2 = new Persona(2L, "Kike", "Campos");

    @Test
    public void getPersonas_whenEmpty_returnsEmptyArray() {
        when(repository.getPersonas()).thenReturn(new ArrayList<>());

        List<Persona> personas = service.getPersonas();
        assertEquals(0, personas.size());
    }

    @Test
    public void getPersonas_whenNotEmpty_returnsArrayWithElements() {
        when(repository.getPersonas()).thenReturn(
                Arrays.asList(new Persona[] { persona1, persona2 })
        );

        List<Persona> personas = service.getPersonas();
        assertEquals(2, personas.size());
        assertTrue(personas.contains(persona1));
        assertTrue(personas.contains(persona2));
    }

    @Test
    public void agregarPersona_success() {
        service.agregarPersona(persona1);
        verify(repository).agregarPersona(persona1); // spy
    }

    @Test
    public void actualizarPersona_success() {
        service.actualizarPersona(persona1);
        verify(repository).actualizarPersona(persona1);
    }

    @Test
    public void actualizarPersona_whenDoesntExist_throwsException() {
        doThrow(new PersonaNoEncontradaException())
                .when(repository).actualizarPersona(persona1);
        assertThrows(RuntimeException.class,
                () -> service.actualizarPersona(persona1));
    }

    @Test
    public void removerPersona_success() {
        service.removerPersonaPorId(99L);
        verify(repository).removerPersonaPorId(99L);
    }

    @Test
    public void removerPersona_whenDoesntExist_throwsException() {
        doThrow(new PersonaNoEncontradaException())
                .when(repository).removerPersonaPorId(99L);
        assertThrows(PersonaNoEncontradaException.class,
                () -> service.removerPersonaPorId(99L));
    }

//    @Configuration
//    static class Config {
//        @Bean
//        public PersonaRepository testRepository() {
//            return mock(PersonaRepository.class);
//        }
//
//        @Bean
//        public PersonaService testService(
//                @Qualifier("testRepository") PersonaRepository testRepository
//        ) {
//            return new PersonaService(testRepository);
//        }
//    }
}
