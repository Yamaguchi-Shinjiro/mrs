package mrs.app.user;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserListForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private String selected;
}