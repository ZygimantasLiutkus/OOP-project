package server.database;

import commons.Activity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

/**
 * Tests for activity repository.
 */
public class TestActivityRepository implements ActivityRepository {

  public final List<Activity> activities = new ArrayList<>();
  public final List<String> used = new ArrayList<>();

  /**
   * Adds the provided name to the used list.
   *
   * @param s the name of the called method
   */
  private void call(String s) {
    this.used.add(s);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Activity> findAll() {
    call("findAll");
    return activities;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Activity> findAll(Sort sort) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Page<Activity> findAll(Pageable pageable) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Activity> findAllById(Iterable<String> strings) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long count() {
    return activities.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteById(String s) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(Activity entity) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAllById(Iterable<? extends String> strings) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAll(Iterable<? extends Activity> entities) {

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
  public <S extends Activity> S save(S entity) {
    call("save");
    entity.id = String.valueOf(activities.size());
    activities.add(entity);
    return entity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Activity> List<S> saveAll(Iterable<S> entities) {
    List<S> list = new ArrayList<S>();
    for (S a : entities) {
      list.add(a);
    }
    return list;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Activity> findById(String s) {
    return find(s);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean existsById(String s) {
    call("existsById");
    return find(s).isPresent();
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
  public <S extends Activity> S saveAndFlush(S entity) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Activity> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAllInBatch(Iterable<Activity> entities) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAllByIdInBatch(Iterable<String> strings) {

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
  public Activity getOne(String s) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Activity getById(String s) {
    call("getById");
    Optional<Activity> optional = find(s);
    return optional.orElse(null);
  }

  /**
   * Finds the activity with the provided id.
   *
   * @param s the id of the activity to find
   * @return the activity that was found
   */
  private Optional<Activity> find(String s) {
    return activities.stream().filter(x -> Objects.equals(x.getId(), s)).findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Activity> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Activity> List<S> findAll(Example<S> example) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Activity> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Activity> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Activity> long count(Example<S> example) {
    return activities.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Activity> boolean exists(Example<S> example) {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends Activity, R> R findBy(Example<S> example,
                                          Function<FluentQuery.FetchableFluentQuery<S>,
                                              R> queryFunction) {
    return null;
  }
}
