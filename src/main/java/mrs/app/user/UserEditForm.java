package mrs.app.user;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

import lombok.Data;
import mrs.domain.model.RoleName;

@Data
public class UserEditForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "必須です")
	private String userId;

	@NotNull(message = "必須です")
	private String firstName;

	@NotNull(message = "必須です")
	private String lastName;

	@NotNull(message = "必須です")
	private String password;

	@NotNull(message = "必須です")
	private RoleName roleName;
}