package com.artsiombaranau.memorize.dao.impl;

import com.artsiombaranau.memorize.dao.WordDao;
import com.artsiombaranau.memorize.model.Word;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WordDaoHibernateImpl implements WordDao {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public List<Word> findAll() {
        try (EntityManager entityManager = getEntityManager()) {
            TypedQuery<Word> query = entityManager.createNamedQuery("word_find_all", Word.class);
            return query.getResultList();
        }
    }

    @Override
    public Optional<Word> findById(UUID id) {
        try (EntityManager entityManager = getEntityManager()) {
            return Optional.ofNullable(entityManager.find(Word.class, id));
        }
    }

    @Override
    public Optional<Word> findByValue(String value) {
        try (EntityManager entityManager = getEntityManager()) {
            TypedQuery<Word> query = entityManager.createQuery("SELECT w FROM " + Word.class.getSimpleName() +
                    " w WHERE w." + Word.Fields.value + " = :value", Word.class);
            query.setParameter("value", value);
            return Optional.ofNullable(query.getSingleResult()); //TODO can be thrown an exception
        }
    }

    public Optional<Word> findByValueNamedQuery(String value) {
        try (EntityManager entityManager = getEntityManager()) {
            TypedQuery<Word> query = entityManager.createNamedQuery("word_find_by_value", Word.class);
            query.setParameter("value", value);
            return Optional.ofNullable(query.getSingleResult()); //TODO can be thrown an exception
        }
    }

    public Optional<Word> findByValueNativeQuery(String value) {
        try (EntityManager entityManager = getEntityManager()) {
            Query query = entityManager.createNativeQuery("SELECT * FROM WORD w WHERE w.VALUE = ?", Word.class);
            query.setParameter(1, value);
            return Optional.ofNullable((Word) query.getSingleResult()); //TODO can be thrown an exception
        }
    }

    public Optional<Word> findByValueCriteria(String value) {
        try (EntityManager entityManager = getEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Word> criteriaQuery = criteriaBuilder.createQuery(Word.class);

            Root<Word> root = criteriaQuery.from(Word.class);
            ParameterExpression<String> valueParameter = criteriaBuilder.parameter(String.class);
            Predicate valuePredicate = criteriaBuilder.equal(root.get(Word.Fields.value), valueParameter);

            criteriaQuery.select(root).where(valuePredicate);

            TypedQuery<Word> query = entityManager.createQuery(criteriaQuery);
            query.setParameter(valueParameter, value);
            return Optional.ofNullable(query.getSingleResult()); //TODO can be thrown an exception
        }
    }

    @Override
    public Word save(Word word) {
        try (EntityManager entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(word);
            entityManager.flush();
            entityManager.getTransaction().commit();
            return word;
        }
    }

    @Override
    public Word update(Word word) {
        try (EntityManager entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(word);
            entityManager.flush();
            entityManager.clear();
            entityManager.getTransaction().commit();
            return word;
        }
    }

    @Override
    public void delete(UUID id) {
        try (EntityManager entityManager = getEntityManager()) {
            entityManager.getTransaction().begin();
            Optional.ofNullable(entityManager.find(Word.class, id))
                    .ifPresent(entityManager::remove);
            entityManager.flush();
            entityManager.clear();
            entityManager.getTransaction().commit();
        }
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
