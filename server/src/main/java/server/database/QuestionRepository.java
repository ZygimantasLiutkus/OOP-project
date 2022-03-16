package server.database;

import commons.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the questions.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
