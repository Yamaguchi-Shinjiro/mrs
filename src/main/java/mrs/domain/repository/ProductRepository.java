package mrs.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import mrs.domain.model.Product;

public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {

}
