package logger.api.repository;

import logger.api.domain.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author eduardo.thums
 */
public interface LogRepository extends JpaRepository<LogEntity, Long> {
}
