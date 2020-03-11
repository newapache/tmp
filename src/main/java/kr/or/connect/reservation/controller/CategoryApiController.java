package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.service.CategoryService;
import kr.or.connect.reservation.service.ProductService;

@RestController
@RequestMapping(path = "/api/categories")
public class CategoryApiController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductService productService;

	@GetMapping
	public Map<String, Object> categoryList() {

		List<Category> list = categoryService.getCategory();
		List<Category> cateProduct = productService.getCategoryCount();
		
		for (int i = 0; i < list.size(); i++) {
			Integer categoryCnt = cateProduct.get(i).getCount().intValue();
			list.get(i).setCount(Long.valueOf(categoryCnt));
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("items", list);	
		return map;
	}

}
