package mrs.app.order;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;
import mrs.domain.model.OrderProduct;
import mrs.domain.model.Product;

@Data
public class OrderEditForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String productIdSearch;
	
	private String productNameSearch;
	
	private String addProductId;
	
	private Integer delLineNo;
	
	private Page<Product> pageInfo;
	
	private List<Product> products;
	
	private List<OrderProduct> cartProducts;
	
}