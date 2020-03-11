<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="네이버 예약, 네이버 예약이 연동된 곳 어디서나 바로 예약하고, 네이버 예약 홈(나의예약)에서 모두 관리할 수 있습니다.">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
<title>네이버 예약</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>


<body>
	<input type="hidden" id="displayInfoId" value="${displayInfoId}">
	<input type="hidden" id="commentCount" value="${commentCnt}">

	<div id="container">
			<!-- [D] 예약하기로 들어오면 header에 fade 클래스 추가로 숨김 -->
			<div class="header fade">
				<header class="header_tit">
					<h1 class="logo">
						<a href="#" class="lnk_logo" title="네이버">
							<span class="spr_bi ico_n_logo">네이버</span>
						</a>
						<a href="#" class="lnk_logo" title="예약">
							<span class="spr_bi ico_bk_logo">예약</span>
						</a>
					</h1>
					<a href="${pageContext.request.contextPath}/bookinglogin" class="btn_my">
						<span title="예약확인">예약확인 </span>
					</a>
				</header>
			</div>
			
			<div class="ct">
				<div class="wrap_review_list">
						<div class="review_header">
							<div class="top_title gr">
							<a href="${pageContext.request.contextPath}/detail?id=${displayInfoId}" class="btn_back" title="이전 화면으로 이동">
								<i class="fn fn-backward1"></i>
							</a>
							<h2>
								<a class="title" href="#">오디컴퍼니 주식회사</a>
							</h2>
						</div>
						</div>
				<div class="section_review_list">
					<div class="review_box">
						<h3 class="title_h3">예매자 한줄평</h3>
							<div class="short_review_area">
								<div class="grade_area">
									<!-- [D] 별점 graph_value는 퍼센트 환산하여 width 값을 넣어줌 -->
									<span class="graph_mask"> <em class="graph_value"
										style="width: 0%;"></em>
									</span> <strong class="text_value"> <span id="average_score"></span>
										<em class="total">5.0</em>
									</strong> <span class="join_count"><em class="green"
										id="total_comment_cnt"></em> 등록</span>
								</div>
	
								<ul class="list_short_review">
								</ul>
							</div>

						<p class="guide">
							<i class="spr_book2 ico_bell"></i> <span>네이버 예약을 통해 실제 방문한 이용자가 남긴 평가입니다.</span>
						</p>
					</div>
				</div>
			</div>
		</div>
		<hr>
	</div>
	
	<footer>
		<div class="gototop">
			<a href="#" class="lnk_top"> <span class="lnk_top_text">TOP</span>
			</a>
		</div>
		<div id="footer" class="footer">
			<p class="dsc_footer">네이버(주)는 통신판매의 당사자가 아니며, 상품의정보, 거래조건, 이용 및
				환불 등과 관련한 의무와 책임은 각 회원에게 있습니다.</p>
			<span class="copyright">© NAVER Corp.</span>
		</div>
	</footer>


	<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.2/handlebars.min.js"></script>
	<script>
	   var displayInfo = {
				displayCommentCount : document.getElementById("commentCount").getAttribute("value"),
				displayInfoId : document.getElementById("displayInfoId").getAttribute("value"),
				displayData : null,
				getDisplayInfo : function() {
					var oReq = new XMLHttpRequest();
					oReq.addEventListener("load", function() {
						this.displayData = JSON.parse(oReq.responseText);
					}.bind(this));
					oReq.open("GET", "api/products/?displayInfoId=" + this.displayInfoId);
					oReq.send();
				}
			}
	   
	   function calcCommentTotal(data) {
			var avg = document.getElementById("average_score");
			var totalCnt = document.getElementById("total_comment_cnt");
			var graphValue = (parseFloat(data.averageScore) / 5.0) * 100;

			avg.innerText = JSON.stringify(data.averageScore);
			totalCnt.innerText = JSON.stringify(data.comments.length) + '건';

			document.querySelector(".graph_value").style.width = graphValue + "%";
		}

		function setComments(data) {
			calcCommentTotal(data);
			var comments = data.comments;
			var title = data.displayInfo[0].productDescription;
			if (comments == "") return;

			var template = document.getElementById("comment").innerText;
			var bindTemplate = Handlebars.compile(template);
			var resultHTML = '';

			for (var i = 0; i < displayInfo.displayCommentCount; i++) {
				var comment = comments[i];
				var commentImg = null;
				var imageId = null;
				if(comment.commentImage != null){
					 imageId = (JSON.stringify(comment.commentImage.fileId)).replace(/\\n/g, "").replace(/\"/g, "");
					 commentImg = (JSON.stringify(comment.commentImage.saveFileName)).replace(/\\n/g, "").replace(/\"/g, "");
				}
				
				var input = {
					"commentId" : (JSON.stringify(comment.commentId)).replace(/\\n/g, "").replace(/\"/g, ""),
					"title" :(JSON.stringify(title)).replace(/\\n/g, "").replace(/\"/g, ""),
					"comment" : (JSON.stringify(comment.comment)).replace(/\\n/g, "").replace(/\"/g, ""),
					"score" : comment.score,
					"email" : (JSON.stringify(comment.reservationEmail)).replace(/\\n/g, "").replace(/\"/g, "").split('@')[0],
					"reservationDate" : (JSON.stringify(comment.reservationDate)).replace(/\\n/g, "").replace(/\"/g, "").split(' ')[0],
					"commentImg" : commentImg,
					"imageId" : imageId
				};
				resultHTML += bindTemplate(input);
			}
			
			document.querySelector(".list_short_review").innerHTML = resultHTML;
		}
	   
		document.addEventListener("DOMContentLoaded", function() {
			displayInfo.getDisplayInfo();
			setTimeout(function() {
				setComments(displayInfo.displayData);
			}, 1000);
		});
</script>


	<script type="comment_template" id="comment">
								<li class="list_item">
									<div>
										<div class="review_area">
      									  {{#if commentImg}}
										   <div class="thumb_area">
                                              <a href="#" class="thumb" title="이미지 크게 보기">
											  <img width="90" height="90" src="${pageContext.request.contextPath}/displayById?id={{imageId}}" alt="리뷰이미지"/>
                                              </a> <span class="img_count" style="display:none;">1</span> 
                                          </div>
										  {{/if}}
											<h4 class="resoc_name">{{title}}</h4>
											<p class="review">
												{{comment}}
											</p>	
										</div>
										<div class="info_area">
											<div class="review_info">
												<span class="grade">{{score}}</span> <span class="name">{{email}}</span>
												<span class="date">{{reservationDate}} 방문</span>
											</div>
										</div>
									</div>
								</li>
 	   </script>

</body>
</html>