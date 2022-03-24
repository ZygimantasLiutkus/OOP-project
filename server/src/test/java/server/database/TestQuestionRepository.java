package server.database;

import commons.Question;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

/**
 * Tests for the question repository.
 */
public class TestQuestionRepository implements QuestionRepository {
  /**
   * {@inheritDoc}
   */
  @Override
  public List<Question> findAll() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Question> findAll(Sort sort) {
    return null;
  }

  /**
   * Returns a {@link Page} of entities meeting the paging restriction provided in the
   * {@code Pageable} object.
   *
   * @param pageable object containing paging restriction.
   * @return a page of entities
   */
  @Override
  public Page<Question> findAll(Pageable pageable) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Question> findAllById(Iterable<Long> longs) {
    return null;
  }

  /**
   * Returns the number of entities available.
   *
   * @return the number of entities.
   */
  @Override
  public long count() {
    return 0;
  }

  /**
   * Deletes the entity with the given id.
   *
   * @param aLong must not be {@literal null}.
   * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
   */
  @Override
  public void deleteById(Long aLong) {

  }

  /**
   * Deletes a given entity.
   *
   * @param entity must not be {@literal null}.
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  public void delete(Question entity) {

  }

  /**
   * Deletes all instances of the type {@code T} with the given IDs.
   *
   * @param longs must not be {@literal null}. Must not contain {@literal null} elements.
   * @throws IllegalArgumentException in case the given {@literal ids} or one of its
   *                                  elements is {@literal null}.
   * @since 2.5
   */
  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  /**
   * Deletes the given entities.
   *
   * @param entities must not be {@literal null}. Must not contain {@literal null} elements.
   * @throws IllegalArgumentException in case the given {@literal entities} or one of its
   *                                  entities is {@literal null}.
   */
  @Override
  public void deleteAll(Iterable<? extends Question> entities) {

  }

  /**
   * Deletes all entities managed by the repository.
   */
  @Override
  public void deleteAll() {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Question> S save(S entity) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Question> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  /**
   * Retrieves an entity by its id.
   *
   * @param aLong must not be {@literal null}.
   * @return the entity with the given id or {@literal Optional#empty()} if none found.
   * @throws IllegalArgumentException if {@literal id} is {@literal null}.
   */
  @Override
  public Optional<Question> findById(Long aLong) {
    return Optional.empty();
  }

  /**
   * Returns whether an entity with the given id exists.
   *
   * @param aLong must not be {@literal null}.
   * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
   * @throws IllegalArgumentException if {@literal id} is {@literal null}.
   */
  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  /**
   * Flushes all pending changes to the database.
   */
  @Override
  public void flush() {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Question> S saveAndFlush(S entity) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Question> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  /**
   * Deletes the given entities in a batch which means it will create a single query. This
   * kind of operation leaves JPAs first level cache and the database out of sync. Consider
   * flushing the {@link EntityManager} before calling this method.
   *
   * @param entities entities to be deleted. Must not be {@literal null}.
   * @since 2.5
   */
  @Override
  public void deleteAllInBatch(Iterable<Question> entities) {

  }

  /**
   * Deletes the entities identified by the given ids using a single query. This kind of
   * operation leaves JPAs first level cache and the database out of sync. Consider
   * flushing the {@link EntityManager} before calling this method.
   *
   * @param longs the ids of the entities to be deleted. Must not be {@literal null}.
   * @since 2.5
   */
  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  /**
   * Deletes all entities in a batch call.
   */
  @Override
  public void deleteAllInBatch() {

  }

  /**
   * Returns a reference to the entity with the given identifier. Depending on how the
   * JPA persistence provider is implemented this is very likely to always return an
   * instance and throw an {@link EntityNotFoundException} on first access. Some of them
   * will reject invalid identifiers immediately.
   *
   * @param aLong must not be {@literal null}.
   * @return a reference to the entity with the given identifier.
   * @see EntityManager#getReference(Class, Object) for details on when an exception is thrown.
   */
  @Override
  public Question getOne(Long aLong) {
    return null;
  }

  /**
   * Returns a reference to the entity with the given identifier. Depending on how the JPA
   * persistence provider is implemented this is very likely to always return an instance and
   * throw an {@link EntityNotFoundException} on first access. Some of them will reject invalid
   * identifiers immediately.
   *
   * @param aLong must not be {@literal null}.
   * @return a reference to the entity with the given identifier.
   * @see EntityManager#getReference(Class, Object) for details on when an exception is thrown.
   * @since 2.5
   */
  @Override
  public Question getById(Long aLong) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Question> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Question> List<S> findAll(Example<S> example) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Question> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Question> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Question> long count(Example<S> example) {
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Question> boolean exists(Example<S> example) {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Question, R> R findBy(
      Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }
}
