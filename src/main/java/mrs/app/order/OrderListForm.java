package mrs.app.order;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderListForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private String selected;
}