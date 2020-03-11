package kr.or.connect.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.Booking;
import kr.or.connect.reservation.dto.Reservation;
import kr.or.connect.reservation.dto.ReservationCancel;
import kr.or.connect.reservation.dto.ReservationPrice;

import static kr.or.connect.reservation.dao.ReservationSqls.*;

@Repository
public class ReservationDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert reservationInsert;
	private SimpleJdbcInsert reservationPriceInsert;
	private RowMapper<Booking> BookingRowMapper = BeanPropertyRowMapper.newInstance(Booking.class);
	private RowMapper<ReservationCancel> ReservationRowMapper = BeanPropertyRowMapper
			.newInstance(ReservationCancel.class);
	private RowMapper<ReservationPrice> ReservationPriceRowMapper = BeanPropertyRowMapper
			.newInstance(ReservationPrice.class);

	public ReservationDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.reservationInsert = new SimpleJdbcInsert(dataSource).withTableName("reservation_info")
				.usingColumns("product_id", "display_info_id", "reservation_name", "reservation_tel",
						"reservation_email", "reservation_date", "create_date", "modify_date")
				.usingGeneratedKeyColumns("id");

		this.reservationPriceInsert = new SimpleJdbcInsert(dataSource).withTableName("reservation_info_price")
				.usingColumns("reservation_info_id", "product_price_id", "count").usingGeneratedKeyColumns("id");
	}

	public Long insertReservation(Reservation reservation) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(reservation);
		return reservationInsert.executeAndReturnKey(params).longValue();
	}

	public Long insertReservationPrice(ReservationPrice reservationPrice) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(reservationPrice);
		return reservationPriceInsert.executeAndReturnKey(params).longValue();
	}

	public List<Booking> selectBooking(String email) {
		Map<String, String> params = new HashMap<>();
		params.put("email", email);
		return jdbc.query(SELECT_BOOKING, params, BookingRowMapper);
	}

	public int selectTotalPrice(Integer reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reservationInfoId", reservationInfoId);
		return jdbc.queryForObject(SELECT_TOTAL_PRICE, params, Integer.class);
	}

	public List<ReservationCancel> selectBookingById(Long reservationId) {
		Map<String, Long> params = new HashMap<>();
		params.put("reservationId", reservationId);
		return jdbc.query(SELECT_BOOKING_BY_ID, params, ReservationRowMapper);
	}

	public List<ReservationPrice> selectBookingPriceById(Long reservationId) {
		Map<String, Long> params = new HashMap<>();
		params.put("reservationId", reservationId);
		return jdbc.query(SELECT_BOOKING_PRICE_BY_ID, params, ReservationPriceRowMapper);
	}

	public int cancelBooking(Long reservationId) {
		Map<String, ?> params = Collections.singletonMap("reservationId", reservationId);
		return jdbc.update(CANCEL_BOOKING, params);
	}
	
	public int selectProductById(Integer reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reservationId", reservationInfoId);
		return jdbc.queryForObject(SELECT_PRODUCT_BY_ID, params, Integer.class);
	}

}
