package kr.or.connect.reservation.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.Product;
import kr.or.connect.reservation.service.ProductService;
import kr.or.connect.reservation.dao.ProductDao;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productdao;

	@Override
	@Transactional
	public List<Product> getProductByCategory(Integer start, Integer end, Integer categoryId) {
		List<Product> list = productdao.selectCategory(start, end, categoryId);
		return list;
	}

	public List<Product> getProduct(Integer start, Integer end) {
		List<Product> list = productdao.selectProduct(start, end);
		return list;
	}

	@Override
	public int getCount() {
		return productdao.selectCount(); 
	}
	
	@Override
	public int getProductId(Integer displayId) {
		return productdao.selectProductId(displayId);
	}
	
	@Override
	public List<Category> getCategoryCount() {
		return productdao.selectCategoryCount();
	}
}
