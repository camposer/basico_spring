package net.softclear.admin.nomina.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class PersonaJpaRepositoryCustomImpl implements PersonaJpaRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> findAllApellidos() {
        String jql = "select p.apellido from Persona p";
        Query q = entityManager.createQuery(jql);
        return q.getResultList();
    }
}
