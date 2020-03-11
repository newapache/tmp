package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.service.ProductService;

@RestController
@RequestMapping(path = "/api/products")
public class ProductApiController {
	
	public static final String DEFAULT_PARAM_VAL = "0";
	
	@Autowired
	ProductService productService;

	@GetMapping
	public Map<String, Object> list(
			@RequestParam(name = "categoryId", required = false, defaultValue = DEFAULT_PARAM_VAL) int categoryId,
			@RequestParam(name = "start", required = false, defaultValue = DEFAULT_PARAM_VAL) int start) {
		int count = 0;
		List<Product> list = null;
		List<Category> cateProduct = productService.getCategoryCount(); // 카테고리별 상품 정보 

		if (categoryId == 0) {
			count = productService.getCount(); // 전체 상품 수 
			list = productService.getProduct(start, ProductService.LIMIT); 
		} else {
			count = cateProduct.get(categoryId - 1).getCount().intValue(); 
			list = productService.getProductByCategory(start, ProductService.LIMIT, categoryId);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("items", list);
		map.put("totalCount", count);

		return map;
	}

}
