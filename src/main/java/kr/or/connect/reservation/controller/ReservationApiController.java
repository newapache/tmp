package kr.or.connect.reservation.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.reservation.dto.Booking;
import kr.or.connect.reservation.dto.Comment;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.ImageFile;
import kr.or.connect.reservation.dto.Reservation;
import kr.or.connect.reservation.dto.ReservationCancel;
import kr.or.connect.reservation.dto.ReservationPrice;
import kr.or.connect.reservation.service.CommentService;
import kr.or.connect.reservation.service.DetailService;
import kr.or.connect.reservation.service.ReservationService;

@RestController
@RequestMapping(path = "/api/reservations")
public class ReservationApiController {

	@Autowired
	ReservationService reservationService;

	@Autowired
	DetailService detailService;
	
	@Autowired
	CommentService commentService;
	
	public static final String PATH = "/tmp/";

	@PostMapping
	public Reservation makeBooking(@RequestBody Reservation reservation, HttpServletRequest request) {
		Reservation reservationInfo = reservationService.addReservation(reservation, reservation.getPrices());
		for (ReservationPrice price : reservation.getPrices()) {
			reservationService.addReservationPrice(price);
		}
		return reservationInfo;
	}

	@PostMapping("/{reservationInfoId}/comments")
	public Map<String, Object> addComments(@PathVariable(name = "reservationInfoId") Long id,
			@RequestParam("file") MultipartFile file, @RequestParam("comment") String comment,
			@RequestParam("reservationInfoId") String reservationInfoId, @RequestParam("productId") String productId,
			@RequestParam("score") String score) throws IOException {
	
		//uploads File
		String savedFileName = uploadUniqueFile(file.getOriginalFilename(), file.getBytes());
		
		//save DataBase 
		ImageFile fileInfo = new ImageFile();	
		fileInfo.setContentType(file.getContentType()); 
		fileInfo.setFileName(file.getOriginalFilename()); 
		fileInfo.setSaveFileName(PATH + savedFileName); 
		fileInfo.setCreateDate(getCurrentDate());
		fileInfo.setModifyDate(getCurrentDate());
		fileInfo.setDeleteFlag((long) 0);
		commentService.addFileInfo(fileInfo);
			
		Comment commentObj = new Comment();
		commentObj.setComment(comment);
		commentObj.setReservationInfoId(Long.parseLong(reservationInfoId));
		commentObj.setProductId(Long.parseLong(productId));
		commentObj.setScore(Long.parseLong(score));
		commentObj.setCreateDate(getCurrentDate());
		commentObj.setModifyDate(getCurrentDate());
		commentService.addComment(commentObj);
		commentService.addCommentImage(commentObj, fileInfo);
	
		//make view
		return makeCommentInfoMap(commentObj);
	}
	
	private String uploadUniqueFile(String originName, byte[] file) throws IOException {
		String dateFlag = getCurrentDate().replace(" ", "_");
		String savedName = dateFlag + "_" + originName;
		File target = new File(PATH, savedName);
		FileCopyUtils.copy(file, target);
		return savedName;
	}

	private static String getCurrentDate() {
		java.util.Date date = new java.util.Date();
		java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = dateformat.format(date);
		return currentTime;
	}
	
	private Map<String, Object> makeCommentInfoMap(Comment commentObj){
		Map<String, Object> CommentMap = new HashMap<>();
		CommentMap.put("comment", commentObj.getComment());
		CommentMap.put("commentId", commentObj.getCommentId());
		CommentMap.put("createDate", commentObj.getCreateDate());
		CommentMap.put("modifyDate", commentObj.getModifyDate());
		CommentMap.put("productId", commentObj.getProductId());
		CommentMap.put("reservationInfoId", commentObj.getReservationInfoId());
		CommentMap.put("score", commentObj.getScore());
		CommentMap.put("commentImage", commentObj.getCommentImage());
		return CommentMap;
	} 
	
	@GetMapping("/{reservationId}")
	public ReservationCancel deleteBooking(@PathVariable(name = "reservationId") Long reservationId) {
		reservationService.cancelReservation(reservationId.intValue());
		ReservationCancel reservationCancel = reservationService.getCancelInfo(reservationId.intValue());
		return reservationCancel;
	}

	@GetMapping
	public Map<String, Object> getBooking(
			@RequestParam(name = "reservationEmail", required = true) String email) {

		List<Booking> bookingList = reservationService.getBookingInfo(email);

		for (Booking booking : bookingList) {
			List<DisplayInfo> displayInfo = detailService.getDisplayInfo(booking.getDisplayInfoId().intValue());
			booking.setDisplayInfo(displayInfo.get(0));
			booking.setTotalPrice((long) reservationService.getTotalPrice(booking.getReservationInfoId().intValue()));
		}

		Map<String, Object> map = new HashMap<>();
		map.put("reservations", bookingList);
		map.put("size", bookingList.size());
		return map;
	}

}
