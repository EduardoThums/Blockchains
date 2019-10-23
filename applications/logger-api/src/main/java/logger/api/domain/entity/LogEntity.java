package logger.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author eduardo.thums
 */
@Entity
@Table(name = "log")
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity {

	@Id
	@SequenceGenerator(name = "log_id_seq", sequenceName = "log_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "log_id_seq", strategy = GenerationType.SEQUENCE)
	private Long id;

	private Instant startDate;

	private Instant endDate;

	public LogEntity(Instant startDate, Instant endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
