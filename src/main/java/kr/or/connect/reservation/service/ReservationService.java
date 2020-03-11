package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.Booking;
import kr.or.connect.reservation.dto.Reservation;
import kr.or.connect.reservation.dto.ReservationCancel;
import kr.or.connect.reservation.dto.ReservationPrice;

public interface ReservationService {
	public Reservation addReservation(Reservation reservation, List<ReservationPrice> reservationPrices);
	public void addReservationPrice(ReservationPrice reservationPrice);
	public List<Booking> getBookingInfo(String email);
	public int getTotalPrice(Integer reservationInfoId);
	public ReservationCancel getCancelInfo(Integer reservationInfoId);
	public int cancelReservation(Integer reservationInfoId);
	public int getProductbyId(Integer reservationInfoId);
}
