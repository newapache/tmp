package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationSqls.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.DisplayImage;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.ProductImage;
import kr.or.connect.reservation.dto.ProductPrice;

@Repository
public class DetailDao {
	
 	private NamedParameterJdbcTemplate jdbc;
    private RowMapper<DisplayImage> displayImageRowMapper = BeanPropertyRowMapper.newInstance(DisplayImage.class);
    private RowMapper<DisplayInfo> displayInfoRowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class);
    private RowMapper<ProductImage> productImageRowMapper = BeanPropertyRowMapper.newInstance(ProductImage.class);
    private RowMapper<ProductPrice> productPriceRowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class);
  
    public DetailDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }
    
    public List<DisplayImage> selectDisplayImage(Integer displayInfoId) {	 
		Map<String, Integer> params = new HashMap<>();
		params.put("displayInfoId", displayInfoId);
    return jdbc.query(SELECT_DISPLAY_IMAGE, params, displayImageRowMapper);
    }
    
    public List<DisplayInfo> selectDisplayInfo(Integer displayInfoId) {	 
		Map<String, Integer> params = new HashMap<>();
		params.put("displayInfoId", displayInfoId);
    return jdbc.query(SELECT_DISPLAY, params, displayInfoRowMapper);
    }
    
    public List<ProductImage> selectProductImage(Integer productId) {	 
		Map<String, Integer> params = new HashMap<>();
		params.put("productId", productId);
    return jdbc.query(SELECT_PRODUCT_IMAGE, params, productImageRowMapper);
    }
    
    public List<ProductPrice> selectProductPrice(Integer productId) {	 
		Map<String, Integer> params = new HashMap<>();
		params.put("productId", productId);
    return jdbc.query(SELECT_PRODUCT_PRICE, params, productPriceRowMapper);
    }

    public String selectMaImage(Integer productId) {	 
		Map<String, Integer> params = new HashMap<>();
		params.put("productId", productId);
    return jdbc.queryForObject(SELECT_MA_IMAGE, params, String.class);
    }
    
    public String selectEtImage(Integer productId) {	 
		Map<String, Integer> params = new HashMap<>();
		params.put("productId", productId);
    return jdbc.queryForObject(SELECT_ET_IMAGE, params, String.class);
    }    
}
