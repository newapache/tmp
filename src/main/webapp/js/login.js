function validateEmail() {
	var emailValue = document.querySelector("[name='resrv_email']").value;
	$(".wrong_msg").css("display", "none");

	var eValid = (/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)
			.test(emailValue);
	if (!eValid) {
		$(".wrong_msg").css("display", "block");
	}
	return eValid;
}
