var displayInfo = {
		categoryId : null
		,itemCount : 4
		,setCategoryId : function(clickedName) {
			if (clickedName == unescape(escape("전시")))
				this.categoryId = 1;
			else if (clickedName == unescape(escape("뮤지컬")))
				this.categoryId = 2;
			else if (clickedName == unescape(escape("콘서트")))
				this.categoryId = 3;
			else if (clickedName == unescape(escape("클래식")))
				this.categoryId = 4;
			else if (clickedName == unescape(escape("연극")))
				this.categoryId = 5;
		}
}

var getDataObj = {
		getProductInfo : function(){
			var oReq = new XMLHttpRequest();

			oReq.addEventListener("load", function() {
				var jsonObj = JSON.parse(this.responseText);
				setElementObj.setCards(jsonObj.items);
				setElementObj.setTotalCount(jsonObj.totalCount);
			});
			
			oReq.open("GET", "api/products");
			oReq.send();
		}
		,getTabInfo : function(url, clickedName) {
			var oReq = new XMLHttpRequest();
			oReq.addEventListener("load", function() {
				var jsonObj = JSON.parse(oReq.responseText);
				setElementObj.setCards(jsonObj.items);
				setElementObj.setTotalCount(jsonObj.totalCount);
			});

			displayInfo.setCategoryId(clickedName);
			oReq.open("GET", url + "?categoryId=" + displayInfo.categoryId);
			oReq.send();
		}
		,getMoreProduct : function() {
			var oReq = new XMLHttpRequest();

			oReq.addEventListener("load", function() {
				var jsonObj = JSON.parse(this.responseText);
				changeStateObj.addMoreCards(jsonObj.items, jsonObj.totalCount);
			});

			if (displayInfo.categoryId == null) {
				oReq.open("GET", "api/products?start=" + displayInfo.itemCount);
				oReq.send();
			} else {
				oReq.open("GET", "api/products?start=" + displayInfo.itemCount + "&categoryId="
						+ displayInfo.categoryId);
				oReq.send();
			}
		}
		,getPromotion : function(){
			var oReq = new XMLHttpRequest();
			oReq.addEventListener("load", function() {
				var jsonObj = JSON.parse(this.responseText);
				var data = jsonObj.items;
				var html = document.getElementById("promotionItem").innerHTML;
				var result = "";
				for(var i=0; i < data.length; i++)
					result += html.replace("{productImageUrl}", JSON.stringify(data[i].productImageUrl).replace(/\"/g, ""));
				document.querySelector(".visual_img").innerHTML = result;
			});
			oReq.open("GET", "api/promotions");
			oReq.send();
		}
}

var setElementObj = {
		setCards : function(data){
			displayInfo.itemCount = 4;
			var leftUl = "";
			var rightUl = "";

			var val = this.makeTemplate(leftUl, rightUl, data);
			document.querySelector("#left_event_box").innerHTML = val[0];
			document.querySelector("#right_event_box").innerHTML = val[1];

			var moreDiv = document.querySelector(".more")
			if (moreDiv.innerHTML == "") {
				moreDiv.innerHTML = '<button class="btn"><span>더보기</span></button>';
			}
		}
	,setTotalCount : function(data){
		var div = document.getElementById("total");
		div.innerHTML = '<p class="event_lst_txt">바로 예매 가능한 행사가 <span class="pink">'
				+ JSON.stringify(data) + '개</span> 있습니다</p>';
	}
	,makeTemplate : function(leftResultHTML, rightResultHTML, data) {
		var html = document.getElementById("itemList").innerHTML;
		for (var i = 0; i < data.length; i++) {
			var tmpHTML = html.replace("{id}",(JSON.stringify(data[i].displayInfoId)).replace(/\\n/g,"").replace(/\"/g, ""))
					.replace("{img}",(JSON.stringify(data[i].productImageUrl)).replace(/\\n/g,"").replace(/\"/g, ""))
					.replace("{description}",(JSON.stringify(data[i].productDescription)).replace(/\\n/g,"").replace(/\"/g, ""))
					.replace("{placeName}",(JSON.stringify(data[i].placeName)).replace(/\\n/g, "").replace(/\"/g, ""))
					.replace("{content}",(JSON.stringify(data[i].productContent)).replace(/\\n/g, "").replace(/\"/g, ""));
			if (i % 2 == 0)
				leftResultHTML += tmpHTML;
			else
				rightResultHTML += tmpHTML;
		}
		return [ leftResultHTML, rightResultHTML ];
	}
}

var changeStateObj = {
		addMoreCards : function(data, itemTotal) {
			displayInfo.itemCount += data.length;
			var leftUl = document.querySelector("#left_event_box").innerHTML;
			var rightUl = document.querySelector("#right_event_box").innerHTML;

			var val = setElementObj.makeTemplate(leftUl, rightUl, data);
			document.querySelector("#left_event_box").innerHTML = val[0];
			document.querySelector("#right_event_box").innerHTML = val[1];

			if (displayInfo.itemCount >= itemTotal) {
				var moreBtn = document.querySelector(".more");
				moreBtn.innerHTML = "";
			}
		}
	  ,animatePromotion : function(){
		  	var pre = -100;
		  	var imgWidth = 1200;
		  	var intervalTime = 2500;
			var target = document.querySelector(".visual_img");
		 	var interval = setInterval(slider, intervalTime);
			function slider(){
				 target.style.transition = "all 1s";
				 target.style.transform = "translateX("+ pre + "%)"
				 pre = pre - 100;
				 if(pre == -imgWidth){
					 target.style.transition = "all 0s";
					 target.style.transform = "translateX("+ 0 + "%)"
					 pre = -100;
				 }
			}	
	  }
}

document.addEventListener("DOMContentLoaded", function() {
	getDataObj.getProductInfo();

	var tabmenu = document.querySelector(".section_event_tab");
	tabmenu.addEventListener("click", function(evt) {
		if (evt.target.innerText != undefined){
			getDataObj.getTabInfo("api/products", evt.target.innerText);
		}
	});

	var more = document.querySelector(".more");
	more.addEventListener("click", function(evt) {
		getDataObj.getMoreProduct();
	});
	
	getDataObj.getPromotion();
	changeStateObj.animatePromotion();
});


