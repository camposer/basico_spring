package net.softclear.admin.nomina.controller;

import net.softclear.admin.nomina.model.Persona;
import net.softclear.admin.nomina.service.PersonaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

// 1. Component -> Unit Tests (CASI SIEMPRE)
// 2. Repo -> Integration Tests (SIEMPRE)
// 3. Service -> Unit Tests (usando mocks) (A VECES)
// 4. Controller -> Integration Tests (a veces con mocks) (CASI SIEMPRE)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class PersonaControllerTest {
    @LocalServerPort
    private int port;

    @MockBean
    private PersonaService service;

    @Autowired
    private TestRestTemplate restTemplate;

    private final Persona persona1 = new Persona(1L, "Rodo", "Campos");
    private final Persona persona2 = new Persona(2L, "Kike", "Campos");

    private String baseUrl;

    @BeforeEach
    public void setup() {
        baseUrl = "http://localhost:" + port;
    }

    @Nested
    class GetPersonas {
        @Test
        // GET /personas
        public void whenEmpty_returnsEmptyArray() throws Exception {
            Mockito.when(service.getPersonas()).thenReturn(
                    new ArrayList<>()
            );

            ResponseEntity<List<Persona>> resp = restTemplate
                    .exchange(
                            baseUrl + "/personas",
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<Persona>>() {});

            List<Persona> personas = resp.getBody();
            assertEquals(0, personas.size());
        }

        @Test
        // GET /personas
        public void whenEmpty_returnsArray() throws Exception {
            Mockito.when(service.getPersonas()).thenReturn(
                    Arrays.asList(new Persona[] { persona1, persona2 })
            );

            ResponseEntity<List<Persona>> resp = restTemplate
                .exchange(
                    baseUrl + "/personas",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Persona>>() {});

            List<Persona> personas = resp.getBody();
            assertEquals(2, personas.size());
            assertTrue(personas.contains(persona1));
            assertTrue(personas.contains(persona2));
        }
    }

    @Nested
    class PostPersona {
        @Test
        // POST /personas
        public void success() throws Exception {
            restTemplate
                    .postForEntity(baseUrl + "/personas", persona1, String.class);
            Mockito.verify(service).agregarPersona(persona1);
        }
    }
}
