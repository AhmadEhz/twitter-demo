package org.twitter.util;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class HibernateUtil {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;
    private static final ValidatorFactory VALIDATOR_FACTORY;

    static {
        ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("default");
        VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();

    }


    public static EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }

    public static EntityManager createEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static Validator getValidator() {
        return VALIDATOR_FACTORY.getValidator();
    }
}
