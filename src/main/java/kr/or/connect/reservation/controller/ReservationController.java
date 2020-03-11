package kr.or.connect.reservation.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.connect.reservation.dto.Booking;
import kr.or.connect.reservation.service.CommentService;
import kr.or.connect.reservation.service.DetailService;
import kr.or.connect.reservation.service.ProductService;
import kr.or.connect.reservation.service.ReservationService;

@Controller
public class ReservationController {

	@Autowired
	DetailService detailService;

	@Autowired
	ProductService productService;

	@Autowired
	CommentService commentService;

	@Autowired
	ReservationService reservationService;
	
	public static final String DEFAULT_PARAM_VAL = "0";

	@GetMapping(path = "/main")
	public void main() {
	}

	@GetMapping(path = "/bookinglogin")
	public void booking() {
	}

	@GetMapping(path = "/myreservation")
	public void myreservation() {
	}
	
	@GetMapping(path = "/reviewWrite")
	public void reviewWriteForm(@RequestParam(name = "reservationInfoId", required = false, defaultValue = DEFAULT_PARAM_VAL) int reservationInfoId, ModelMap model) {
		int productId = reservationService.getProductbyId(reservationInfoId);
		model.addAttribute("reservationInfoId", reservationInfoId);
		model.addAttribute("productId", productId);
	}
	

	@GetMapping(path = "/review")
	public void review(@RequestParam(name = "id", required = false, defaultValue = DEFAULT_PARAM_VAL) int displayInfoId,
			ModelMap model) {
		int productId = productService.getProductId(displayInfoId);
		int commentCnt = commentService.getCommentCount(productId);

		model.addAttribute("displayInfoId", displayInfoId);
		model.addAttribute("commentCnt", commentCnt);
	}

	@GetMapping(path = "/detail")
	public void sendMainImage(@RequestParam(name = "id", required = false, defaultValue = DEFAULT_PARAM_VAL) int displayInfoId,
			ModelMap model) {
		int productId = productService.getProductId(displayInfoId);
		String maImageName = detailService.getMaImage(productId);
		model.addAttribute("maImageName", maImageName);
		model.addAttribute("displayInfoId", displayInfoId);
	}

	@GetMapping(path = "/reserve")
	public void reserve(@RequestParam(name = "id", required = false, defaultValue = DEFAULT_PARAM_VAL) int displayInfoId,
			ModelMap model) {
		int productId = productService.getProductId(displayInfoId);
		String maImageName = detailService.getMaImage(productId);
		Calendar calendar = new GregorianCalendar(Locale.KOREA);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int randomNum = (int) (Math.random() * 5);
		
		model.addAttribute("reservationDate", year + "-" + month + "-" + (day + randomNum));
		model.addAttribute("displayInfoId", displayInfoId);
		model.addAttribute("productId", productId);
		model.addAttribute("maImageName", maImageName);
	}

	@PostMapping(path = "/confirm")
	public String confirmBooking(@RequestParam(name = "resrv_email", required = true, defaultValue = DEFAULT_PARAM_VAL) String email, HttpSession session,
			RedirectAttributes redirectAttr) {

		List<Booking> bookingList = reservationService.getBookingInfo(email);

		if (bookingList.size() == 0) {
			session.removeAttribute("email");
			session.removeAttribute("hasEmail");
			return "redirect:/main";
		} else {
			session.setAttribute("email", email);
			session.setAttribute("hasEmail", "true");
			return "redirect:/myreservation"; // 로그인성공
		}
	}
	
	
	

}
