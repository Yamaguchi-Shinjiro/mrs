package mrs.app.order;

import mrs.domain.model.*;
import mrs.domain.service.order.OrderService;
import mrs.domain.service.user.ReservationUserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("orders")
@SessionAttributes(types = { OrderListForm.class, OrderEditForm.class })
public class OrderController {
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@ModelAttribute(value = "orderListForm")
	OrderListForm setUpOrderListForm() {
		return new OrderListForm();
	}

	@ModelAttribute(value = "orderEditForm")
	OrderEditForm setUpOrderEditForm() {
		OrderEditForm form = new OrderEditForm();
		form.setCartProducts(new ArrayList<OrderProduct>());
		return form;
	}

	@GetMapping
	String index(OrderListForm form, Model model, @PageableDefault(size = 10) @SortDefaults({
			@SortDefault(sort = "orderNo", direction = Direction.ASC) }) Pageable pageable) {
		Page<OrderData> orders = orderService.findList(pageable);
		model.addAttribute("page", orders);
		List<OrderData> contents = orders.getContent();
		List<OrderDataView> viewContents = new ArrayList<>();
		contents.stream().forEach(x -> {
			OrderDataView odv = new OrderDataView();
			BeanUtils.copyProperties(x, odv);
			odv.setTotalPrice(odv.getOrderDetails().stream().map(m -> m.getPurchasePrice().intValue()).reduce(0, Integer::sum));
			viewContents.add(odv);
		});
		model.addAttribute("orders", viewContents);
		return "order/list";
	}

	@GetMapping("new")
	String newOrder(OrderEditForm form, Model model, @PageableDefault(size = 5) @SortDefaults({
			@SortDefault(sort = "productId", direction = Direction.ASC) }) Pageable pageable) {
		form.setProductIdSearch(null);
		form.setProductNameSearch(null);
		form.setProducts(new ArrayList<Product>());
		form.setCartProducts(new ArrayList<OrderProduct>());
		return "order/new";
	}

	@PostMapping
	String create(@Validated OrderEditForm form, BindingResult bindingResult,
			@AuthenticationPrincipal ReservationUserDetails userDetails, Model model) {
		if (bindingResult.hasErrors()) {
			return "order/new";
		}
		OrderData order = new OrderData();
		order.setOrderDate(LocalDateTime.now());
		order.setUser(userDetails.getUser());
		List<OrderDetail> details = new ArrayList<>();
		List<OrderProduct> cartProducts = form.getCartProducts();
		int lineNo = 1;
		for (OrderProduct product : cartProducts) {
			OrderDetail detail = new OrderDetail();
			detail.setLineNo(lineNo);
			detail.setAmount(product.getAmount());
			detail.setPurchasePrice(new BigDecimal(product.getAmount()).multiply(product.getPrice()));
			Product product2 = new Product();
			product2.setProductId(product.getProductId());
			detail.setProduct(product2);
			details.add(detail);
			lineNo++;
		}
		order.setOrderDetails(details);
		orderService.create(order);
		return "redirect:/orders";
	}

	@GetMapping("edit/{orderNo}")
	String editProduct(OrderEditForm form, @PathVariable String orderNo, Model model) {
		OrderData order = orderService.findOne(orderNo);
		BeanUtils.copyProperties(order, form);
		return "order/edit";
	}

	@PostMapping("edit/{orderId}")
	String update(@Validated OrderEditForm form, BindingResult bindingResult,
			@AuthenticationPrincipal ReservationUserDetails userDetails, @PathVariable String orderNo, Model model) {
		if (bindingResult.hasErrors()) {
			return "product/edit";
		}
		OrderData order = new OrderData();
		BeanUtils.copyProperties(form, order);
		orderService.edit(order);
		return "redirect:/orders";
	}

	@PostMapping("cancel")
	String cancel(@RequestParam("orderNo") String orderNo, Model model, Pageable pageable) {
		try {
			OrderData order = orderService.findOne(orderNo);
			orderService.cancel(order);
		} catch (AccessDeniedException e) {
			model.addAttribute("error", e.getMessage());
//			return index(model, pageable);
		}
		return "redirect:/orders";
	}

	@PostMapping("products/search")
	String searchProductList(OrderEditForm form, Model model, @PageableDefault(size = 5) @SortDefaults({
			@SortDefault(sort = "productId", direction = Direction.ASC) }) Pageable pageable) {
		Product product = new Product();
		product.setProductId(form.getProductIdSearch());
		product.setProductName(form.getProductNameSearch());
		Page<Product> products = orderService.findProductList(product, pageable);
		form.setPageInfo(products);
		form.setProducts(products.getContent());
		return "order/new";
	}
	
	@GetMapping("products/next")
	String nextPage(OrderEditForm form, Model model, @PageableDefault(size = 5) @SortDefaults({
			@SortDefault(sort = "productId", direction = Direction.ASC) }) Pageable pageable) {
		return searchProductList(form, model, pageable);
	}

	@PostMapping("product/add")
	String addProduct(OrderEditForm form, Model model, @PageableDefault(size = 5) @SortDefaults({
			@SortDefault(sort = "productId", direction = Direction.ASC) }) Pageable pageable) {
		List<OrderProduct> cartProducts = form.getCartProducts();
		OrderProduct orderProduct = new OrderProduct();
		Product product = orderService.findProduct(form.getAddProductId());
		BeanUtils.copyProperties(product, orderProduct);
		orderProduct.setLineNo(cartProducts.size() + 1);
		cartProducts.add(orderProduct);
		return searchProductList(form, model, pageable);
	}
	
	@PostMapping("product/del")
	String delProduct(OrderEditForm form, Model model, @PageableDefault(size = 5) @SortDefaults({
			@SortDefault(sort = "productId", direction = Direction.ASC) }) Pageable pageable) {
		List<OrderProduct> cartProducts = form.getCartProducts();
		cartProducts.removeIf(x -> x.getLineNo() == form.getDelLineNo());
		return searchProductList(form, model, pageable);
	}
}