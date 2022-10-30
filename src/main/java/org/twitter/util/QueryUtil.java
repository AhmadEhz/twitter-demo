package org.twitter.util;

import org.twitter.util.exception.CustomizedIllegalArgumentException;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class QueryUtil {
    public static <T> Optional<T> getQueryResult(Supplier<TypedQuery<T>> supplier) {
        try {
            return Optional.ofNullable(supplier.get().getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public static void executeQuery(Supplier<Query> query) {
        executeQuery(query, RuntimeException::new);
    }

    public static void executeQuery(Supplier<Query> query, Supplier<? extends RuntimeException> exception) {
        int update = 0;
        try {
            update = query.get().executeUpdate();
        } catch (PersistenceException e) {
            exception.get().addSuppressed(e);
            throw exception.get();
        }
        /*if (update < 1)
            throw new CustomizedIllegalArgumentException(exceptionMessage);*/
    }
}
