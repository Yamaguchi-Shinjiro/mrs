package mrs.domain.repository;

import org.springframework.stereotype.Repository;

import mrs.domain.model.OrderData;

@Repository
public interface OrderDataCustomRepository {
	
	void save(OrderData orderData);

}
