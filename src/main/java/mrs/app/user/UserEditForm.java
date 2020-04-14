package mrs.app.user;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import mrs.app.validation.Password;
import mrs.app.validation.UserId;
import mrs.domain.model.RoleName;

@Data
public class UserEditForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static interface NewUser {};
	public static interface EditUser {};

	@NotNull
	@UserId
	private String userId;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotNull(groups = NewUser.class)
	@Password(groups = NewUser.class)
	private String password;

	private RoleName roleName;
}