
var displayInfo = {
	displayCommentCount : 3
	,displayInfoId : document.getElementById("displayInfoId").getAttribute("value")
	,displayData : null
	,getDisplayInfo : function() {
		var oReq = new XMLHttpRequest();
		oReq.addEventListener("load", function() {
			this.displayData = JSON.parse(oReq.responseText);
		}.bind(this));
		oReq.open("GET", "api/products/?displayInfoId=" + this.displayInfoId);
		oReq.send();
	}
}

var setDataObj = {
	setContent : function() {
		var content = displayInfo.displayData.displayInfo[0].productContent;
		var p = document.querySelector(".dsc");
		p.innerText = JSON.stringify(content).replace(/\\n/g, "").replace(/\"/g, "");

		$(".bk_more").click(function() {
			if($(this).hasClass('_open')) {
				$('.store_details').removeClass('close3');
				$(this).css("display", "none");
				$(".bk_more._close").css("display", "block");
			}
			else {
				$('.store_details').addClass('close3');
				$(this).css("display", "none");
				$(".bk_more._open").css("display", "block");
			}
			});
		}
	,setImage : function(imageInfo) {
		var etFileName = "";
		var title = displayInfo.displayData.displayInfo[0].productDescription;
		
		for (var i = 0; i < imageInfo.length; i++)
			if (JSON.stringify(imageInfo[i].type) == '"et"'){
				etFileName = JSON.stringify(imageInfo[i].saveFileName).replace(/\\n/g, "").replace(/\"/g, "");
				break;
			}
		var titleElems = document.querySelectorAll(".visual_txt_tit");
		for (var i = 0; i < titleElems.length; i++) 
			titleElems[i].innerHTML = '<span>' + JSON.stringify(title).replace(/\\n/g, "").replace(/\"/g, "") + '</span>';
		
		if (etFileName != "")
			changeStateObj.animateImage(etFileName);
	}
	,setComments : function(data) {
		this.setCommentTotal(data);
		var commentList = data.comments;
		var title = data.displayInfo[0].productDescription;
		if (commentList == "") return;

		var template = document.getElementById("comment").innerText;
		var bindTemplate = Handlebars.compile(template);
		var resultHTML = '';

		for (var i = 0; i < displayInfo.displayCommentCount; i++) {
			var comment = commentList[i];
			var commentImg = null;
			if(comment.commentImage != null)
				 commentImg = (JSON.stringify(comment.commentImage.saveFileName)).replace(/\\n/g, "").replace(/\"/g, "");
			var input = {
				"commentId" : (JSON.stringify(comment.commentId)).replace(/\\n/g, "").replace(/\"/g, ""),
				"title" :(JSON.stringify(title)).replace(/\\n/g, "").replace(/\"/g, ""),
				"comment" : (JSON.stringify(comment.comment)).replace(/\\n/g, "").replace(/\"/g, ""),
				"score" : comment.score,
				"email" : (JSON.stringify(comment.reservationEmail)).replace(/\\n/g, "").replace(/\"/g, "").split('@')[0],
				"reservationDate" : (JSON.stringify(comment.reservationDate)).replace(/\\n/g, "").replace(/\"/g, "").split(' ')[0],
				"commentImg" : commentImg
			};
			resultHTML += bindTemplate(input);
		}
		document.querySelector(".list_short_review").innerHTML = resultHTML;
	}	
	,setTabs : function(data) {
		var dpInfo = data.displayInfo[0];
		var imageInfo = data.displayInfoImage[0];
		
		var locationTemplate = document.getElementById("location_Info").innerText;
		var detailTemplate = document.getElementById("detail_Info").innerText;
		var bindLocationTemplate = Handlebars.compile(locationTemplate);
		var bindDetailTemplate = Handlebars.compile(detailTemplate);
		
		var input = {
			"placeLot" : (JSON.stringify(dpInfo.placeLot)).replace(/\\n/g, "").replace(/\"/g, ""),
			"image" : (JSON.stringify(imageInfo.saveFileName)).replace(/\\n/g, "").replace(/\"/g, ""),
			"title" : (JSON.stringify(dpInfo.productDescription)).replace(/\\n/g, "").replace(/\"/g, ""),
			"placeStreet" : (JSON.stringify(dpInfo.placeStreet)).replace(/\\n/g, "").replace(/\"/g, ""),
			"placeName" : (JSON.stringify(dpInfo.placeName)).replace(/\\n/g, "").replace(/\"/g, ""),
			"tel" : (JSON.stringify(dpInfo.telephone)).replace(/\\n/g, "").replace(/\"/g, "")
		};
		var location = bindLocationTemplate(input);
		input = {
			"content" : (JSON.stringify(dpInfo.productContent)).replace(/\\n/g, "").replace(/\"/g, "")
		};
		var detail = bindDetailTemplate(input);
		document.querySelector(".detail_area_wrap").innerHTML = detail;
		document.querySelector(".detail_location").innerHTML = location;
	}
	,setCommentTotal : function(data) {
		var avg = document.getElementById("average_score");
		var totalCnt = document.getElementById("total_comment_cnt");
		var graphValue = (parseFloat(data.averageScore) / 5.0) * 100;
		
		avg.innerText = JSON.stringify(data.averageScore);
		totalCnt.innerText = JSON.stringify(data.comments.length) + '건';
		document.querySelector(".graph_value").style.width = graphValue + "%";
	}
}

var changeStateObj = {
		selectTab : function(spanId){
			var detailTab = document.querySelector(".detail_area_wrap");
			var locationTab = document.querySelector(".detail_location");

			if (spanId == "location_span") {
				detailTab.setAttribute("class", "detail_area_wrap hide");
				locationTab.setAttribute("class", "detail_location");
			}
			if (spanId == "detail_span") {
				locationTab.setAttribute("class", "detail_location hide");
				detailTab.setAttribute("class", "detail_area_wrap");
			}
		}
      ,animateImage : function(etFileName){
    		var etImgElem = document.querySelector("#et_img");
    		etImgElem.setAttribute("src", "/reservation3/displayByName?name=" + etFileName);
    	
    		$(".spr_book2").removeClass('off');
    		$(document).ready(function(){
    			$('.spr_book2').click(function(){
    				$('.visual_img').css("transition", "all 1s");
    			    if($(this).hasClass('ico_arr6_lt')) {
    			    	$('.visual_img').css("transform", "translateX(0%)");
    			    	$('#page1').removeClass('off');
    			    	$('#page2').addClass('off');
    			    }   
    			    else {
    			    	$('.visual_img').css("transform", "translateX(-100%)");
    			    	$('#page2').removeClass('off');
    			    	$('#page1').addClass('off');
    			    }
    			    });
    			});
    		}
}

document.addEventListener("DOMContentLoaded", function() {
	displayInfo.getDisplayInfo();
	
	setTimeout(function() {
		setDataObj.setImage(displayInfo.displayData.productImages);
		setDataObj.setContent();
		setDataObj.setComments(displayInfo.displayData);
		setDataObj.setTabs(displayInfo.displayData);

		var tabmenu = document.querySelector(".info_tab_lst");
		tabmenu.addEventListener("click", function(evt) {
			var evtElem = evt.target;
			var spanId = evtElem.getAttribute("id");
			changeStateObj.selectTab(spanId);
		});
	}, 1000);
});

