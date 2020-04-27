package mrs.domain.service.order;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import mrs.domain.model.Product;

public class ProductSpecifications {
    public static Specification<Product> productIdContains(String productId) {
    	return StringUtils.isEmpty(productId) ? null : (root, query, cb) -> {
            return cb.like(root.get("productId"), "%" + productId + "%");
        };
    }
    public static Specification<Product> productNameContains(String productName) {
    	return StringUtils.isEmpty(productName) ? null : (root, query, cb) -> {
            return cb.like(root.get("productName"), "%" + productName + "%");
        };
    }
}
