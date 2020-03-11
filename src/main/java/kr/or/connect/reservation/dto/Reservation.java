package kr.or.connect.reservation.dto;

import java.util.List;

public class Reservation {
	private Long product_id;
	private Long display_info_id;
	private String reservation_name;
	private String reservation_tel;
	private String reservation_email;
	private String reservation_date;
	private List<ReservationPrice> prices;

	public List<ReservationPrice> getPrices() {
		return prices;
	}
	public void setPrices(List<ReservationPrice> prices) {
		this.prices = prices;
	}
	
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public Long getDisplay_info_id() {
		return display_info_id;
	}
	public void setDisplay_info_id(Long display_info_id) {
		this.display_info_id = display_info_id;
	}
	public String getReservation_name() {
		return reservation_name;
	}
	public void setReservation_name(String reservation_name) {
		this.reservation_name = reservation_name;
	}
	public String getReservation_tel() {
		return reservation_tel;
	}
	public void setReservation_tel(String reservation_tel) {
		this.reservation_tel = reservation_tel;
	}
	public String getReservation_email() {
		return reservation_email;
	}
	public void setReservation_email(String reservation_email) {
		this.reservation_email = reservation_email;
	}
	public String getReservation_date() {
		return reservation_date;
	}
	public void setReservation_date(String reservation_date) {
		this.reservation_date = reservation_date;
	}
	
	@Override
	public String toString() {
		return "Reservation [product_id=" + product_id + ", display_info_id=" + display_info_id + ", reservation_name="
				+ reservation_name + ", reservation_tel=" + reservation_tel + ", reservation_email=" + reservation_email
				+ ", reservation_date=" + reservation_date + ", prices=" + prices + "]";
	}

	


}