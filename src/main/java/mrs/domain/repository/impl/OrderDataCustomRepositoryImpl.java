package mrs.domain.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mrs.domain.model.OrderData;
import mrs.domain.model.OrderDetail;
import mrs.domain.repository.OrderDataCustomRepository;

@Repository
public class OrderDataCustomRepositoryImpl implements OrderDataCustomRepository {

	@Autowired
	EntityManager manager;

	@Override
	public void save(OrderData orderData) {
		Query query = manager.createNativeQuery("SELECT 'OD' || TO_CHAR(NEXTVAL('order_data_order_no_seq'),'FM00000000')");
		String orderNo = (String) query.getSingleResult();
		
		manager.createNativeQuery("INSERT INTO order_data (order_no, order_date, user_id) VALUES (?,?,?)")
				.setParameter(1, orderNo)
				.setParameter(2, orderData.getOrderDate())
				.setParameter(3, orderData.getUser().getUserId())
				.executeUpdate();
		
		for (OrderDetail detail : orderData.getOrderDetails()) {
			manager.createNativeQuery("INSERT INTO order_detail (order_no, line_no, product_id, amount, purchase_price) VALUES (?,?,?,?,?)")
				.setParameter(1, orderNo)
				.setParameter(2, detail.getLineNo())
				.setParameter(3, detail.getProduct().getProductId())
				.setParameter(4, detail.getAmount())
				.setParameter(5, detail.getPurchasePrice())
				.executeUpdate();
			
		}
	}

}
