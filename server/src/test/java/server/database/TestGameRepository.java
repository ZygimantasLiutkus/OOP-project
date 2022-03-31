package server.database;

import commons.GameEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

/**
 * Tests for Game repository.
 */
public class TestGameRepository implements GameEntityRepository {

  public final List<GameEntity> games = new ArrayList<>();
  public final List<String> used = new ArrayList<>();

  /**
   * Method to add a names of all called methods.
   *
   * @param s the name of the method.
   */
  private void call(String s) {
    used.add(s);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<GameEntity> findAll() {
    call("findAll");
    return games;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<GameEntity> findAll(Sort sort) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Page<GameEntity> findAll(Pageable pageable) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<GameEntity> findAllById(Iterable<Long> longs) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long count() {
    return games.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteById(Long aLong) {
    call("deleteById");
    if (find(aLong).isPresent()) {
      games.remove(find(aLong).get());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(GameEntity entity) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAll(Iterable<? extends GameEntity> entities) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAll() {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends GameEntity> S save(S entity) {
    call("save");
    if (entity.id == null) {
      entity.id = (long) games.size();
    }
    if (games.contains(entity)) {
      games.remove(entity);
      games.add(entity);
    } else {
      games.add(entity);
    }
    return entity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends GameEntity> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<GameEntity> findById(Long id) {
    return find(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean existsById(Long id) {
    call("existsById");
    return find(id).isPresent();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void flush() {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends GameEntity> S saveAndFlush(S entity) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends GameEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAllInBatch(Iterable<GameEntity> entities) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAllInBatch() {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GameEntity getOne(Long id) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GameEntity getById(Long id) {
    call("getById");
    Optional<GameEntity> optional = find(id);
    return optional.orElse(null);
  }

  /**
   * Method to find a game by id.
   *
   * @param id the id we are looking for
   * @return a game or an empty item
   */
  public Optional<GameEntity> find(Long id) {
    return games.stream().filter(x -> Objects.equals(x.getId(), id)).findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends GameEntity> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends GameEntity> List<S> findAll(Example<S> example) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends GameEntity> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends GameEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends GameEntity> long count(Example<S> example) {
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends GameEntity> boolean exists(Example<S> example) {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends GameEntity, R> R findBy(Example<S> example,
                                            Function<FluentQuery.FetchableFluentQuery<S>,
                                                R> queryFunction) {
    return null;
  }

  /**
   * Method to filter games by status.
   *
   * @param status the status we want to filter
   * @return the list of found games
   */
  @Override
  public List<GameEntity> findByStatus(String status) {
    return games.stream().filter(x -> x.getStatus().equals(status)).collect(Collectors.toList());
  }

  /**
   * Method to filter games by type.
   *
   * @param type the type we want to filter by
   * @return the list of found games
   */
  @Override
  public List<GameEntity> findByType(GameEntity.Type type) {
    return games.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
  }
}
