package mrs.domain.model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "usr")
@Data
public class User {
	@Id
	private String userId;
	private String password;
	private String firstName;
	private String lastName;
	@Enumerated(EnumType.STRING)
	private RoleName roleName;
}