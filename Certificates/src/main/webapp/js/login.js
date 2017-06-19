var loginURL = "main/loginUser";

//-----------LOGOVANJE
$(document).on('submit', '.loginForm', function(e) {
	e.preventDefault();
	console.log("registration begin");
	var email = $(this).find("input[name=emailLogin]").val();
	var password = $(this).find("input[name=passwordLogin]").val();
	$.ajax({
		type : 'POST',
		url : loginURL,
		contentType : 'application/json',
		dataType : "text",
		data : formToJSONLogin(email, password),
		success: function(data) {
			if(data == "User" || data=="Admin") {
				window.location.href = "http://localhost:8080/CertGen.html";
			}
			
		}
	});
});

function formToJSONLogin(email, password) {
	return JSON.stringify({
		"email" : email,
		"password" : password,
	});
}