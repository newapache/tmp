package kr.or.connect.reservation.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.DetailDao;
import kr.or.connect.reservation.dto.DisplayImage;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.ProductImage;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.service.DetailService;

@Service
public class DetailServiceImpl implements DetailService {

	@Autowired
	DetailDao detailDao;

	@Override
	@Transactional
	public List<DisplayImage> getDisplayImage(Integer displayId) {
		return detailDao.selectDisplayImage(displayId);
	}

	@Override
	@Transactional
	public List<DisplayInfo> getDisplayInfo(Integer displayId) {
		return detailDao.selectDisplayInfo(displayId);
	}

	@Override
	@Transactional
	public List<ProductImage> getProductImage(Integer productId) {
		return detailDao.selectProductImage(productId);
	}

	@Override
	@Transactional
	public List<ProductPrice> getProductPrice(Integer productId) {
		return detailDao.selectProductPrice(productId);
	}

	@Override
	public String getMaImage(Integer productId) {
		return detailDao.selectMaImage(productId);
	}

	@Override
	public String getEtImage(Integer productId) {
		return detailDao.selectEtImage(productId);
	}
}
