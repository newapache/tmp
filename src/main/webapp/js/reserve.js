var displayInfo = {
		displayInfoId : document.getElementById("displayInfoId").getAttribute("value")
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

var setInfoObj = {
		setDescription : function(data){
			var display = data.displayInfo[0];
			var priceData = data.productPrices;
			document.getElementById("time_dsc").innerText = (JSON.stringify(display.openingHours)).replace(/\\n/g, "\n").replace(/\"/g, "");
			var priceStr = "";
			for(var i = 0; i < priceData.length; i++){
				priceStr += ((JSON.stringify(priceData[i].priceTypeName)).replace(/\\n/g, "").replace(/\"/g, "") + "  석 - " 
						+ (JSON.stringify(priceData[i].price)).replace(/\\n/g, "").replace(/\"/g, "") + "원 \n");
			}
			document.getElementById("price_dsc").innerText = priceStr;
		}
}

function ChkBtn(){
	this.registerDetailBtnEvent();
	this.registerAgreeBtnEvent();
}
ChkBtn.prototype = {
		registerDetailBtnEvent : function(){
			$(document).ready(function(){
				  $('.btn_text').click(function(){
				    if($(this).parent().parent().hasClass('open')){
				    	$(this).parent().parent().removeClass('open');
				    	$(this).text("보기");
				    }
				    else{
				    	$(this).parent().parent().addClass('open');          
				    	$(this).text("접기");
				    }
				  });
				});
		}
		,registerAgreeBtnEvent : function(){
			$("label[for='chk3']").click(function(){
	
				if($(this).hasClass("checked")){
					$(this).removeClass('checked');	
					$('.bk_btn_wrap').addClass('disable');
					validate.entireFlag = false;
				}
				else{
					$(this).addClass('checked');
					validate.checkAll();
					if(validate.entireFlag){
						$('.bk_btn_wrap').removeClass('disable');
					}
				}
			});
		}
}

function PriceBtn(data){
	 this.setPriceBtns(data);
	 this.registerButtonEvents();
}
PriceBtn.prototype = {
		setPriceBtns : function(data){
			var priceData = data.productPrices;
			
			var template = document.getElementById("price_info").innerText;
			var bindTemplate = Handlebars.compile(template);
			var resultHTML = '';
			for(var i = 0; i < priceData.length; i++){
				var input = {
						"priceType" : (JSON.stringify(priceData[i].priceTypeName)).replace(/\\n/g, "").replace(/\"/g, ""),
						"price" :(JSON.stringify(priceData[i].price)).replace(/\\n/g, "").replace(/\"/g, ""),
						"discountRate" : (JSON.stringify(priceData[i].discountRate)).replace(/\\n/g, "").replace(/\"/g, ""),
					};
				resultHTML += bindTemplate(input);
			}
			document.querySelector(".ticket_body").innerHTML = resultHTML;
		}
		
		,registerButtonEvents : function() {
			$(".btn_plus_minus.spr_book2").click(function() {
				var preCount = $(this).siblings('.count_control_input').val();
				var newCount = 0;
				if($(this).hasClass('ico_minus3')) {
					newCount = parseInt(preCount) - 1;
					if(newCount <= 0){
						$(this).addClass('disabled');
						$(this).siblings('.count_control_input').addClass('disabled');
						newCount = 0;
					}
				}
				else{
					newCount = parseInt(preCount) + 1;
					if(newCount >= 1){
						$(this).siblings('.btn_plus_minus.spr_book2.ico_minus3').removeClass('disabled');
						$(this).siblings('.count_control_input').removeClass('disabled');
					}			
				}
				$(this).siblings('.count_control_input').val(newCount);
				var price = parseInt($(this).parent().parent().siblings('.qty_info_icon').children('.product_price').children('.price').text());
				price = newCount * price;
				$(this).parent().siblings(".individual_price").children(".total_price").text(price);
				
				if(price > 0) $(this).parent().siblings(".individual_price").addClass('on_color');
				else $(this).parent().siblings(".individual_price").removeClass('on_color');
				
				var total = 0;
				$('.count_control_input').each(function(){
					total += parseInt($(this).val());
				});
				$('#totalCount').text(total);
				
				if(total == 0) validate.entireFlag = false;
			});
        }
}
        
var validate = {
		emailFlag : false
		,nameFlag : false
		,telFlag : false
		,entireFlag : false
		,validateName : function(){
			var name = document.getElementById("name").value;
		    var nError = document.getElementById("name_error");
		    nError.innerHTML = "";
		    
			if(name.length == 0) {
				nError.innerHTML = "이름 형식 오류";
				this.nameFlag = false;
				this.entireFlag = false;
			}
			else this.nameFlag = true;
		 }
		,validateTel : function(){
		    var tel = document.getElementById("tel").value;
		    var tError = document.getElementById("tel_error");
		    tError.innerHTML = "";
		    var expr = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;
		    if (!expr.test(tel)) {
		        tError.innerHTML = "전화번호 형식 오류";
				this.telFlag = false;
				this.entireFlag = false;
		    }
		    else this.telFlag = true;
		}
		
	  ,validateEmail : function(){
		    var email = document.getElementById("email").value;
		    var eError = document.getElementById("email_error");
		    eError.innerHTML = "";
		  
		    var eValid = (/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/).test(email);
		    if (!eValid) {
		    	eError.innerHTML = "이메일 형식 오류";
				this.emailFlag = false;
				this.entireFlag = false;
		    }
		    else this.emailFlag = true;
	  }
	  
	  ,checkAll : function(){
		  this.validateTel();
		  this.validateName();
		  this.validateEmail();
		  var totalCount = document.getElementById("totalCount").innerText;
		  if(this.emailFlag && this.telFlag && this.nameFlag && totalCount > 0){
				  this.entireFlag = true;
		  }
	  }
}


var requestReservation = {
		registerEvent : function(){
			var btn = document.querySelector(".bk_btn");
			btn.addEventListener("click", function() {
				validate.checkAll();
				if(!validate.entireFlag){
					return;
				}

				var pricesData = this.setPriceVal();
				var xhr = new XMLHttpRequest();
				xhr.open('POST', 'api/reservations');
				xhr.setRequestHeader('Content-type', 'application/json');
			
				var data = {
						display_info_id : document.getElementById("displayInfoId").value,
						product_id : document.getElementById("productId").value,
					   reservation_date: document.getElementById("reservationDate").value,
					   reservation_email: document.getElementById("email").value,
					  reservation_name: document.getElementById("name").value,
					  reservation_tel: document.getElementById("tel").value,
				      prices : pricesData
				};
				
				xhr.send(JSON.stringify(data));
				document.location = "main";
			}.bind(this));
		}
	 ,setPriceVal : function(){
			var priceData = displayInfo.displayData.productPrices;
			var elementList = document.querySelectorAll(".count_control_input");
			var prices = [];
			for(var i = 0; i < priceData.length; i++){
				var price_data = {
					count : elementList[i].value,
					product_price_id : (JSON.stringify(priceData[i].productPriceId)).replace(/\\n/g, "").replace(/\"/g, ""),	
					reservation_info_id : null,
					reservationInfoPriceId : null
				};
				prices.push(price_data);
			}
			return prices;
	 } 
}

document.addEventListener("DOMContentLoaded", function() {

	displayInfo.getDisplayInfo();
	setTimeout(function() {
		setInfoObj.setDescription(displayInfo.displayData);
		btnObj = new PriceBtn(displayInfo.displayData);
		chkBtnObj = new ChkBtn();
		requestReservation.registerEvent();
	}, 1000);

});








