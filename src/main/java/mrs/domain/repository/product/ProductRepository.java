package mrs.domain.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.domain.model.Product;

public interface ProductRepository extends JpaRepository<Product, String>  {

}
