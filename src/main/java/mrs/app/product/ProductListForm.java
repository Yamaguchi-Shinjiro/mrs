package mrs.app.product;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductListForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private String selected;
}