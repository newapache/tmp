package kr.or.connect.reservation.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.ReservationDao;
import kr.or.connect.reservation.dto.Booking;
import kr.or.connect.reservation.dto.Reservation;
import kr.or.connect.reservation.dto.ReservationCancel;
import kr.or.connect.reservation.dto.ReservationPrice;
import kr.or.connect.reservation.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	ReservationDao reservatioDao;
	
	@Override
	public Reservation addReservation(Reservation reservation, List<ReservationPrice> reservationPrices) { 
		Long reservationId = reservatioDao.insertReservation(reservation);
		for(ReservationPrice price : reservationPrices) {
			price.setReservation_info_id(reservationId);
		}
		return reservation;
	}
	
	@Override
	public void addReservationPrice(ReservationPrice reservationPrice) {
		Long reservationPriceId = reservatioDao.insertReservationPrice(reservationPrice);
		reservationPrice.setId(reservationPriceId);
	}
	
	@Override
	public List<Booking> getBookingInfo(String email){
		return reservatioDao.selectBooking(email);
	}
	
	@Override
	public int getTotalPrice(Integer reservationInfoId) {
		return reservatioDao.selectTotalPrice(reservationInfoId);
	}
	
	
	@Override
	public ReservationCancel getCancelInfo(Integer reservationInfoId) {
		ReservationCancel reservationCancel = reservatioDao.selectBookingById((long) reservationInfoId).get(0);
		List<ReservationPrice> prices = reservatioDao.selectBookingPriceById((long)reservationInfoId);
		reservationCancel.setPrices(prices);
		return reservationCancel;
	}
	
	
	@Override
	@Transactional(readOnly=false)
	public int cancelReservation(Integer reservationInfoId) {
		int updateCount = reservatioDao.cancelBooking((long) reservationInfoId);
		return updateCount;
	}
	
	@Override
	public int getProductbyId(Integer reservationInfoId) {
		int productId = reservatioDao.selectProductById(reservationInfoId);
		return productId;
	}
}
