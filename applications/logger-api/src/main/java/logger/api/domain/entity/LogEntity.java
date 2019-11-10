package logger.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author eduardo.thums
 */
@Getter
@Entity
@Table(name = "log")
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity {

	@Id
	@SequenceGenerator(name = "log_id_seq", sequenceName = "log_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "log_id_seq", strategy = GenerationType.SEQUENCE)
	private Long id;

	private Long startDate;

	private Long endDate;

	public LogEntity(Long startDate, Long endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
