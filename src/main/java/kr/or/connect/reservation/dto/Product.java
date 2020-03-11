package kr.or.connect.reservation.dto;

public class Product {

	private Long productId;
	private Long displayInfoId;
	private String productImageUrl;
	private String productDescription;
	private String placeName;
	private String productContent;

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String description) {
		this.productDescription = description;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String place_name) {
		this.placeName = place_name;
	}

	public String getProductContent() {
		return productContent;
	}

	public void setProductContent(String content) {
		this.productContent = content;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long id) {
		this.productId = id;
	}

	public String getProductImageUrl() {
		return productImageUrl;
	}

	public void setProductImageUrl(String file_name) {
		this.productImageUrl = file_name;
	}

	public Long getDisplayInfoId() {
		return displayInfoId;
	}

	public void setDisplayInfoId(Long display_id) {
		this.displayInfoId = display_id;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ",displayInfoId=" + displayInfoId + ",productImageUrl="
				+ productImageUrl + ", productDescription=" + productDescription + ", placeName=" + placeName
				+ ", productContent=" + productContent + "]";
	}

}
