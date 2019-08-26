package com.dao;

import com.exception.InternalServerError;
import com.model.Number;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class PalindromeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_NUMBERS = "SELECT * FROM NUMBERS";

    public long saveNumber(Number number) throws InternalServerError {
        try {
            entityManager.persist(number);
            return number.getId();
        } catch (Exception e) {
            throw new InternalServerError("saving failed");
        }
    }

    public void updateNumber(Number number) throws InternalServerError {
        try {
            entityManager.merge(number);
        } catch (Exception e) {
            throw new InternalServerError("saving failed");
        }
    }

    public List<Number> getNumbers() throws InternalServerError {
        try {
            Query query = entityManager.createNativeQuery(SELECT_NUMBERS, Number.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new InternalServerError("getting is failed");
        }
    }

    public Number findById(long id) throws InternalServerError {
        try {
            return entityManager.find(Number.class, id);
        } catch (Exception e) {
            throw new InternalServerError("finding is failed");
        }
    }
}
