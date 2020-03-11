package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.Product;

public interface ProductService {
	public static final Integer LIMIT = 4;
	public List<Product> getProductByCategory(Integer start, Integer end, Integer categoryId);
	public List<Product> getProduct(Integer start, Integer end);
	public int getCount();
	public int getProductId(Integer displayId);
	public List<Category> getCategoryCount();
}
