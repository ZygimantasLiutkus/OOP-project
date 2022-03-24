package server.database;

import commons.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

/**
 * Tests for player repository.
 */
public class TestPlayerRepository implements PlayerRepository {

  public final List<Player> players = new ArrayList<>();
  public final List<String> used = new ArrayList<>();

  /**
   * Adds a called method to the used list.
   *
   * @param s the name of the method
   */
  public void call(String s) {
    used.add(s);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Player> findAll() {
    call("findAll");
    return players;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Player> findAll(Sort sort) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Page<Player> findAll(Pageable pageable) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Player> findAllById(Iterable<Long> longs) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long count() {
    return players.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteById(Long aLong) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(Player entity) {

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
  public void deleteAll(Iterable<? extends Player> entities) {

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
  public <S extends Player> S save(S entity) {
    call("save");
    entity.id = (long) players.size();
    players.add(entity);
    return entity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Player> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Player> findById(Long aLong) {
    return Optional.empty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean existsById(Long aLong) {
    return false;
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
  public <S extends Player> S saveAndFlush(S entity) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Player> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAllInBatch(Iterable<Player> entities) {

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
  public Player getOne(Long aLong) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Player getById(Long aLong) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Player> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Player> List<S> findAll(Example<S> example) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Player> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Player> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Player> long count(Example<S> example) {
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Player> boolean exists(Example<S> example) {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Player, R> R findBy(Example<S> example,
                                        Function<FluentQuery.FetchableFluentQuery<S>,
                                            R> queryFunction) {
    return null;
  }
}
