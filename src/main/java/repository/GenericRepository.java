package repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID> {
    Optional<T> getById(ID id);

    List<T> getAll();

    T save(T entity);

    void deleteBy(ID id);

    T update(T entity);
}
