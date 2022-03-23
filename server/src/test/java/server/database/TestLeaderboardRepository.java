package server.database;

import commons.LeaderboardEntry;
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
 * Tests for the leaderboard repository.
 */
public class TestLeaderboardRepository implements LeaderboardRepository {

  public final List<LeaderboardEntry> entries = new ArrayList<>();
  public final List<String> calledMethods = new ArrayList<>();

  /**
   * Add the called method to calledMethods list.
   *
   * @param s the name of the called method
   */
  public void add(String s) {
    calledMethods.add(s);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<LeaderboardEntry> findAll() {
    calledMethods.add("findAll");
    return entries;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<LeaderboardEntry> findAll(Sort sort) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Page<LeaderboardEntry> findAll(Pageable pageable) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<LeaderboardEntry> findAllById(Iterable<Long> longs) {
    return null;
  }

  /**
   * Method that counts the amount of entries in the DB.
   *
   * @return the number of entries in the DB
   */
  @Override
  public long count() {
    return entries.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteById(Long along) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(LeaderboardEntry entity) {

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
  public void deleteAll(Iterable<? extends LeaderboardEntry> entities) {

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
  public <S extends LeaderboardEntry> S save(S entity) {
    add("save");
    entity.setId((long) entries.size());
    entries.add(entity);
    return entity;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends LeaderboardEntry> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<LeaderboardEntry> findById(Long along) {
    return Optional.empty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean existsById(Long along) {
    add("existsById");
    return find(along).isPresent();
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
  public <S extends LeaderboardEntry> S saveAndFlush(S entity) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends LeaderboardEntry> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAllInBatch(Iterable<LeaderboardEntry> entities) {

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
  public LeaderboardEntry getOne(Long along) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LeaderboardEntry getById(Long along) {
    add("getById");
    return find(along).get();
  }

  /**
   * Finds an entry by the provided id.
   *
   * @param id the leaderboard entry's id
   * @return the found entry (if found)
   */
  private Optional<LeaderboardEntry> find(Long id) {
    return entries.stream().filter(x -> x.getId() == id).findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends LeaderboardEntry> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends LeaderboardEntry> List<S> findAll(Example<S> example) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends LeaderboardEntry> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends LeaderboardEntry> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends LeaderboardEntry> long count(Example<S> example) {
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends LeaderboardEntry> boolean exists(Example<S> example) {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends LeaderboardEntry, R> R findBy(Example<S> example,
                                                  Function<FluentQuery.FetchableFluentQuery<S>,
                                                      R> queryFunction) {
    return null;
  }
}
