package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.Comment;
import kr.or.connect.reservation.dto.DisplayImage;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.ProductImage;
import kr.or.connect.reservation.dto.ProductPrice;
import kr.or.connect.reservation.service.CategoryService;
import kr.or.connect.reservation.service.CommentService;
import kr.or.connect.reservation.service.DetailService;
import kr.or.connect.reservation.service.ProductService;

@RestController
@RequestMapping(path = "/api/products/")
public class DisplayApiController {

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	DetailService detailService;

	@Autowired
	CommentService commentService;

	@GetMapping
	public Map<String, Object> getDisplayInfos(
			@RequestParam(name = "displayInfoId", required = true, defaultValue = "0") int displayInfoId) {

		int productId = productService.getProductId(displayInfoId);
		int totalComments = commentService.getCommentCount(productId);
		double totalScore = 0;
		double averageScore = 0;
		Long commentId = null;
		List<ProductPrice> productPrice = detailService.getProductPrice(productId);
		List<ProductImage> productImage = detailService.getProductImage(productId);
		List<DisplayImage> displayImage = detailService.getDisplayImage(displayInfoId);
		List<DisplayInfo> displayInfo = detailService.getDisplayInfo(displayInfoId);
		List<Comment> comment = commentService.getComment(productId);

		for (int i = 0; i < displayInfo.size(); i++) {
			Long categoryId = displayInfo.get(i).getCategoryId();
			String categoryName = categoryService.getCategoyName(categoryId.intValue());
			displayInfo.get(i).setCategoryName(categoryName);
		}

		for (int i = 0; i < comment.size(); i++) {
			commentId = comment.get(i).getCommentId();
			totalScore += comment.get(i).getScore();
			if (commentService.getCommentImage(commentId.intValue()).size() > 0) { // Image가 존재하는 comment찾기
				comment.get(i).setCommentImage(commentService.getCommentImage(commentId.intValue()).get(0));
			}
		}

		try {
			averageScore = totalScore / totalComments;
		} catch (ArithmeticException e) {
			averageScore = -1;	
		}

		Map<String, Object> map = new HashMap<>();
		map.put("productPrices", productPrice);
		map.put("productImages", productImage);
		map.put("displayInfoImage", displayImage);
		map.put("displayInfo", displayInfo);
		map.put("comments", comment);
		map.put("averageScore", Math.round(averageScore * 10) / 10.0);

		return map;
	}
}
