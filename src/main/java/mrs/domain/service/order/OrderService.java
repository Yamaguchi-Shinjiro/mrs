package mrs.domain.service.order;

import mrs.domain.model.*;
import mrs.domain.repository.OrderDataCustomRepository;
import mrs.domain.repository.OrderDataRepository;
import mrs.domain.repository.ProductRepository;
import static mrs.domain.service.order.ProductSpecifications.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("deprecation")
@Service
@Transactional
public class OrderService {
	private final OrderDataRepository orderDataRepository;
	private final OrderDataCustomRepository orderDataCustomRepository;
	private final ProductRepository productRepository;
	
	public OrderService(OrderDataRepository orderDataRepository, OrderDataCustomRepository orderDataCustomRepository, ProductRepository productRepository) {
		this.orderDataRepository = orderDataRepository;
		this.orderDataCustomRepository = orderDataCustomRepository;
		this.productRepository = productRepository;
	}

	public Page<OrderData> findList(Pageable pageable) {
		return orderDataRepository.findAll(pageable);
	}

	public void create(OrderData orderData) {
		orderDataCustomRepository.save(orderData);
	}

	public OrderData edit(OrderData orderData) {
		return orderDataRepository.save(orderData);
	}

	public void cancel(@P("product") OrderData orderData) {
		orderDataRepository.delete(orderData);
	}

	public OrderData findOne(String orderNo) {
		return orderDataRepository.findById(orderNo).orElse(null);
	}

	public Page<Product> findProductList(Product product, Pageable pageable) {
		return productRepository.findAll(Specification.where(productIdContains(product.getProductId()))
				.and(productNameContains(product.getProductName())), pageable);
	}
	
	public Product findProduct(String productId) {
		return productRepository.findById(productId).orElse(null);
	}
}