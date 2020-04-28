package mrs.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderDataView extends OrderData {

	private static final long serialVersionUID = 1L;
	
	private Integer totalPrice;

}
