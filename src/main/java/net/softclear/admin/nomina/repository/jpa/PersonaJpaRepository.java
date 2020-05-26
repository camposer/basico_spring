package net.softclear.admin.nomina.repository.jpa;

import net.softclear.admin.nomina.model.Persona;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaJpaRepository extends
        CrudRepository<Persona, Long>,
        PersonaJpaRepositoryCustom {

    List<Persona> findByNombreAndApellido(
            String nombre,
            String apellido
    );

    List<Persona> findByNombreOrApellido(
            String nombre,
            String apellido,
            Pageable pageable
    );

    @Query(
            value = "select p from Persona p where p.nombre like concat('%', :text, '%') or p.apellido like concat('%', :text, '%')",
            nativeQuery = false
    ) // jql
    List<Persona> findContainsText(@Param("text") String text);
}
