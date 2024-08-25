package cl.jose.huenul.springboot.globallogic.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "phone")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PhoneEntity {

	@Id
	@Column(name = "id_phone", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "number", nullable = false)
	private Long number;

	@Column(name = "city_code", nullable = false)
	private Integer cityCode;

	@Column(name = "country_code", nullable = false)
	private String countryCode;

	@ManyToMany(mappedBy = "phones")
	private List<UserEntity> users;

}
