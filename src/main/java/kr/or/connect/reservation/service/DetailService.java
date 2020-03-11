package kr.or.connect.reservation.service;

import java.util.List;
import kr.or.connect.reservation.dto.*;

public interface DetailService {
	public List<DisplayImage> getDisplayImage(Integer displayId);
	public List<DisplayInfo> getDisplayInfo(Integer displayId);
	public List<ProductImage> getProductImage(Integer productId);
	public List<ProductPrice> getProductPrice(Integer productId);
	public String getMaImage(Integer productId);
	public String getEtImage(Integer productId);
}
