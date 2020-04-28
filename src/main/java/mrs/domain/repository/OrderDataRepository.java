package mrs.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.domain.model.OrderData;

public interface OrderDataRepository extends JpaRepository<OrderData, String>  {

}
