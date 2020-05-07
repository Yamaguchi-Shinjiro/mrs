package mrs.domain.service.product;

import mrs.domain.model.*;
import mrs.domain.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("deprecation")
@Service
@Transactional
public class ProductService {
	private final ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Page<Product> findList(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	public Product create(Product product) {
		return productRepository.save(product);
	}

	public Product edit(Product product) {
		return productRepository.save(product);
	}

	public void cancel(@P("product") Product product) {
		productRepository.delete(product);
	}

	public Product findOne(String productId) {
		return productRepository.findById(productId).orElse(null);
	}
}