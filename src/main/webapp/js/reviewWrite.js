var postReview = {
		form : document.forms.namedItem("fileinfo")
		
		,registerEvent : function(){
			this.form.addEventListener('submit', function(ev) {
				  var oData = new FormData(this.form);
				  if(!this.validateForm(oData)){
					  return; 
				  }
				  this.sendRequest(oData);
				  ev.preventDefault();
				}.bind(this), false);
		}

		,sendRequest : function(data){
			  var oReq = new XMLHttpRequest();
			  var reservationInfoId = document.getElementById("reservationInfoId").getAttribute("value");
			  
			  oReq.open('POST', 'api/reservations/'+ reservationInfoId +'/comments', true);
			  oReq.onload = function(oEvent) {
			    if (oReq.status == 200) {
			    	document.location = "main";
			    } else {
			    	alert('upload fail');
			    }
			  };
			  oReq.send(data);
		}
		
		,validateForm : function(formData){
			var fname = formData.get('file').name;
		    var file_result = (/\.(jpe?g|png)$/i.test(fname));
		    
		    var score = formData.get('score');
		    var score_result = (/[1-5]$/.test(score));
		    if(!score_result) alert('별점을 입력하세요');
		    
		    var comment = formData.get('comment');
		    var comment_result = (/^[ㄱ-ㅎ가-힣a-zA-Z0-9!@#$%^*()\-_=+\\\|\[\]{};:\'",.<>\/~?]{5,400}$/.test(comment));
		    if(!comment_result) alert('5자 이상 입력하세요');

		    if(file_result && score_result && comment_result) return true;
		    else return false;
		}
}

function ReviewElem(){
	this.registerTextEvent();
	this.registerCountTextEvent();
	this.registerFileEvent();
}

ReviewElem.prototype = {
		registerTextEvent : function(){
				$('.review_write_info').click(function(){
					$(this).css("display", "none");
				});
				
				$(".review_textarea").focusout(function() {
					var commentText = document.querySelector(".review_textarea").value;
					if(commentText.length == 0)
						$('.review_write_info').css("display", "block");
				 });
		}

		,registerCountTextEvent : function(){
			$('.review_textarea').on('keyup',function(){
				   var charCount = $(this).val().replace(/\s/g, '').length;
					$(".result").text(charCount);
				});
		}
		
		,registerFileEvent : function(){
		    const elImage = document.querySelector("#reviewImageFileOpenInput");
		    elImage.addEventListener("change", (evt) => {
		    	  const image = evt.target.files[0];
		    	  var fileUpload = document.querySelector("#reviewImageFileOpenInput");
		          fileUpload = fileUpload.value.toLowerCase();
		          if(!(/\.(jpe?g|png)$/i.test(fileUpload))) { 
		             alert("invalide image file type");
		             return;
		         }
		          
		          $('.item').css('display','block');
		          const elImage = document.querySelector(".item_thumb");
		          elImage.src = URL.createObjectURL(image);
		          
		          $('.spr_book').on('click', function() {
		        	  $('#reviewImageFileOpenInput').val('');
		        	  $('.item').css('display','none');
		          });
		    });
		}
}

function StarRating(){
	this.registerEvent();
}

StarRating.prototype = {
		registerEvent : function(){
			$('.rating input').on('click', function(ev){
				$('.star_rank').removeClass('gray_star');
				var onStar = parseInt($(this).data('value'), 10);
				var stars = $(this).parent().children('input.rating_rdo');
				
			    for (i = 0; i < stars.length; i++) {
			        $(stars[i]).removeClass('checked');
			      }
			    for (i = 0; i < onStar; i++) {
			        $(stars[i]).addClass('checked');
			      }
			    
			    var ratingValue = parseInt($('.rating input.checked').last().data('value'), 10);
			    $('.star_rank').text(ratingValue);
			    $('#star_rating').val(ratingValue);
			    ev.preventDefault();
			});
	}
}

document.addEventListener("DOMContentLoaded", function() {
	postReview.registerEvent();
	ReviewElemEventObj = new ReviewElem();
	StarObj = new StarRating();
});


