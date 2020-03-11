package kr.or.connect.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.Category;
import kr.or.connect.reservation.dto.Product;
import static kr.or.connect.reservation.dao.ReservationSqls.*;

@Repository
public class ProductDao {

	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Product> productRowMapper = BeanPropertyRowMapper.newInstance(Product.class);
	private RowMapper<Category> categoryRowMapper = BeanPropertyRowMapper.newInstance(Category.class);

	public ProductDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Product> selectCategory(Integer start, Integer end, Integer category) { // 카테고리별 상품 정보
		Map<String, Integer> params = new HashMap<>();
		params.put("start", start);
		params.put("end", end);
		params.put("category", category);
		return jdbc.query(SELECT_CATEGORY_PRODUCT, params, productRowMapper);
	}

	public List<Product> selectProduct(Integer start, Integer end) { // 전체 영역 상품 정보
		Map<String, Integer> params = new HashMap<>();
		params.put("start", start);
		params.put("end", end);
		return jdbc.query(SELECT_PRODUCT, params, productRowMapper);
	}

	public int selectCount() {
		return jdbc.queryForObject(SELECT_COUNT, Collections.emptyMap(), Integer.class);
	}

	public List<Category> selectCategoryCount() { // 카테고리별 상품 수
		return jdbc.query(SELECT_CATEGORY_COUNT, Collections.emptyMap(), categoryRowMapper);
	}

	public int selectProductId(Integer displayId) {
		try {
			Map<String, Integer> params = new HashMap<>();
			params.put("displayId", displayId);
			return jdbc.queryForObject(SELECT_PRODUCT_ID, params, Integer.class);
		} catch (EmptyResultDataAccessException e) {
			return 0;
		}
	}

}
