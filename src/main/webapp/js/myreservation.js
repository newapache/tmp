
function CardUi(cardList) {
	this.cardList = cardList;
	this.email = document.getElementById("email").getAttribute("value");
	this.bookingData = null;
	this.totalSize = 0;
	this.cancelCnt = 0;
	this.usedCnt = 0;
	this.reservedCnt = 0;
    this.setCards(this.cardList, this.email);
}

CardUi.prototype = {
		setCards : function(cardList, email) {		
			this.getCardData(email);
        	setTimeout(function() {
        		var reservationInfo = JSON.parse(cardObj.bookingData);
        		var reservations = reservationInfo.reservations;
        		var utc = new Date().toJSON().slice(0,10);
        		var today = Date.parse(utc);
        		this.totalSize = reservationInfo.size;
        		
        		if(this.totalSize > 0) $(".list_cards").css("display", "block");
        		else {
        			$(".err").css("display", "block");
        			return;
        		}
        		
        		for (var i = 0; i < reservations.length; i++) {
        			var reservationDate = (JSON.stringify(reservations[i].reservationDate)).replace(/\\n/g, "").replace(/\"/g, "").split(" ")[0];
        			reservationDate = Date.parse(reservationDate);
        			var cancelFlag = (JSON.stringify(reservations[i].cancelYn)).replace(/\\n/g, "").replace(/\"/g, "");
        			if (cancelFlag == "1"){
        				this.makeTemplate(reservations[i], "card_canceled", cardList[2]);
        				this.cancelCnt += 1;
        			}
        			else if(reservationDate < today){
        				this.makeTemplate(reservations[i], "card_used", cardList[1]);
        				this.usedCnt += 1;
        			}
        			else{
        				this.makeTemplate(reservations[i], "card", cardList[0]);
        				this.reservedCnt += 1;
        			}
        		}
    		this.setCardSummary();
        	}.bind(this), 1000);
        }

		,setCardSummary : function(){
			if(this.cancelCnt == 0 )$(".card.cancel").css("display", "none");
    		if(this.usedCnt == 0 )$(".card.used").css("display", "none");
    		if(this.reservedCnt == 0 )$(".card.confirmed").css("display", "none");	
    		
    		document.querySelector("#book_total").innerText = this.totalSize;
    		document.querySelector("#book_reserved").innerText = this.reservedCnt;
    		document.querySelector("#book_used").innerText = this.usedCnt;
    		document.querySelector("#book_canceled").innerText = this.cancelCnt;
		}

		,getCardData : function(email) {
            var oReq = new XMLHttpRequest();
            oReq.addEventListener("load", function () {
            	cardObj.bookingData = oReq.responseText;
            });
           	oReq.open("GET", "api/reservations?reservationEmail=" + email);
            oReq.send(); 
        }
        	
		,makeTemplate : function(reservation, templateName, insertElem){
			var template = document.getElementById(templateName).innerText;
			var bindTemplate = Handlebars.compile(template);
			var input = {
					"reservationId" : (JSON.stringify(reservation.reservationInfoId)).replace(/\\n/g, "").replace(/\"/g, ""),
					"title" : (JSON.stringify(reservation.displayInfo.productDescription)).replace(/\\n/g, "").replace(/\"/g, ""),
					"reservationDate" : (JSON.stringify(reservation.reservationDate)).replace(/\\n/g, "").replace(/\"/g, "").split(" ")[0],
					"placeStreet" : (JSON.stringify(reservation.displayInfo.placeStreet)).replace(/\\n/g, "").replace(/\"/g, ""),
					"placeName" : (JSON.stringify(reservation.displayInfo.placeName)).replace(/\\n/g, "").replace(/\"/g, ""),
					"totalPrice" : (JSON.stringify(reservation.totalPrice)).replace(/\\n/g, "").replace(/\"/g, ""),
				};
			var resultHTML = bindTemplate(input);
			var article = document.createElement("article");
			article.setAttribute("class", "card_item");
			article.innerHTML = resultHTML;
			insertElem.appendChild(article);
		}
}

var handleCancelEvent = {
		registerCancelEvent : function(){
			var bookingId = '';
			$(".booking_cancel").children(".btn").click(function() {
				$(".pop_tit").children().text($(this).parent().parent().children(".tit").text());
				$(".popup_booking_wrapper").css("display", "block");
				bookingId = $(this).parent().parent().children(".booking_number").text();
			});
			
			$(".btn_bottom").click(function() {	
				if ($(this).attr("id") == "cancel_booking") { 		
					handleCancelEvent.sendCancelRequest(bookingId);	
				}			
				$(".popup_booking_wrapper").css("display", "none");
			});
			
			$(".popup_btn_close").click(function() {
				$(".popup_booking_wrapper").css("display", "none");
			});
		}

	,sendCancelRequest : function(id){
		var url = "api/reservations/" + id;
		var resultHTML = '';
		var oReq = new XMLHttpRequest();
		oReq.onreadystatechange = function(){
			if(this.readyState == 4 && this.status == 200){
				var cancelInfo = JSON.parse(this.responseText);
				if(cancelInfo.cancelYn == 1){
					handleCancelEvent.moveCanceledCard(id);
					handleCancelEvent.setCancelSummary();
				}
			}
		}
		oReq.open("GET", url);
		oReq.send();
	}

	,moveCanceledCard : function(id){
		var cardToCancel = $('#' + id).closest('article');
		$(".articles.canceld").append(cardToCancel);
		cardToCancel.find("button").css("display", "none");
	}

	,setCancelSummary : function(){
		var reservedCnt = parseInt(document.querySelector("#book_reserved").innerText) - 1;
		var cancelCnt = parseInt(document.querySelector("#book_canceled").innerText) + 1;
		document.querySelector("#book_reserved").innerText = reservedCnt;
		document.querySelector("#book_canceled").innerText = cancelCnt;
			
		if(cancelCnt == 1)$(".card.cancel").css("display", "block");
		if(reservedCnt == 0 )$(".card.confirmed").css("display", "none");
	}
}


var cardObj;
document.addEventListener("DOMContentLoaded", function() {
		var cardList = document.querySelectorAll(".articles");  
		cardObj = new CardUi(cardList);
		
		setTimeout(function() {
			handleCancelEvent.registerCancelEvent();
			
		}, 1000);
});
		


		