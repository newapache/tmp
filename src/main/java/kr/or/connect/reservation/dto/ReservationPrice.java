package kr.or.connect.reservation.dto;

public class ReservationPrice {
	
	private Long id;
	private Long reservation_info_id;
	private Long product_price_id;
	private Long count; 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getReservation_info_id() {
		return reservation_info_id;
	}
	public void setReservation_info_id(Long reservation_info_id) {
		this.reservation_info_id = reservation_info_id;
	}
	public Long getProduct_price_id() {
		return product_price_id;
	}
	public void setProduct_price_id(Long product_price_id) {
		this.product_price_id = product_price_id;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "ReservationPrice [id=" + id + ", reservation_info_id=" + reservation_info_id + ", product_price_id="
				+ product_price_id + ", count=" + count + "]";
	}

}
