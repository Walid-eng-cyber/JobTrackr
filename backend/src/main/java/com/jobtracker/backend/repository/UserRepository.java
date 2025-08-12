package com.jobtracker.backend.repository;

import com.jobtracker.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA provides a convenient way to define a data access layer of an application.
 * A JPA repository is an interface that extends {@link JpaRepository} and is annotated with
 * {@link Repository}. The interface provides methods for performing CRUD operations on the
 * entity associated with the repository.
 *
 * The JpaRepository interface provides the following methods:
 * - findAll(): Returns a list of all entities in the database.
 * - findAll(Sort sort): Returns a list of all entities in the database with the given sort.
 * - findAll(Pageable pageable): Returns a page of entities in the database with the given
 *   pageable configuration.
 * - findOne(ID id): Returns the entity with the given id.
 * - exists(ID id): Returns whether the entity with the given id exists.
 * - count(): Returns the number of entities in the database.
 * - delete(ID id): Deletes the entity with the given id.
 * - delete(T entity): Deletes the given entity.
 * - delete(Iterable<? extends T> entities): Deletes the given entities.
 * - deleteAll(): Deletes all entities in the database.
 * - deleteAllInBatch(): Deletes all entities in the database in a batch.
 * - deleteInBatch(Iterable<T> entities): Deletes the given entities in a batch.
 *
 * The interface also provides methods for executing custom queries:
 * - findAllBy(String queryString): Returns a list of all entities in the database that match
 *   the given query string.
 * - findAllBy(String queryString, Object[] args): Returns a list of all entities in the
 *   database that match the given query string with the given arguments.
 * - findAllBy(String queryString, Pageable pageable): Returns a page of entities in the
 *   database that match the given query string with the given pageable configuration.
 *
 * The interface also provides methods for executing native queries:
 * - findAllByNativeQuery(String queryString): Returns a list of all entities in the database
 *   that match the given native query string.
 * - findAllByNativeQuery(String queryString, Object[] args): Returns a list of all entities
 *   in the database that match the given native query string with the given arguments.
 * - findAllByNativeQuery(String queryString, Pageable pageable): Returns a page of entities
 *   in the database that match the given native query string with the given pageable
 *   configuration.
 *
 * The interface also provides methods for executing named queries:
 * - findAllByName(String name): Returns a list of all entities in the database that match
 *   the given named query.
 * - findAllByName(String name, Object[] args): Returns a list of all entities in the
 *   database that match the given named query with the given arguments.
 * - findAllByName(String name, Pageable pageable): Returns a page of entities in the
 *   database that match the given named query with the given pageable configuration.
 *
 * The interface also provides methods for executing named queries with parameters:
 * - findAllByNameAndArgs(String name, Object[] args): Returns a list of all entities in the
 *   database that match the given named query with the given arguments.
 * - findAllByNameAndArgs(String name, Object[] args, Pageable pageable): Returns a page of
 *   entities in the database that match the given named query with the given arguments and
 *   pageable configuration.
 */
/**
 * To make a repository, you only need to extend the {@link JpaRepository} and add the
 * {@link Repository} annotation.
 */

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
