package kr.or.connect.reservation.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.CategoryDao;
import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryDao categorydao;

	@Override
	public List<Category> getCategory() {
		List<Category> list = categorydao.selectAll();
		return list;
	}
	@Override
	public String getCategoyName(Integer categoryId) {
		return categorydao.selectCategoryName(categoryId);
	}
}
