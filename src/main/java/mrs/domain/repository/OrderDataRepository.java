package mrs.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mrs.domain.model.OrderData;

@Repository
public interface OrderDataRepository extends JpaRepository<OrderData, String>  {

}
