var logoutURL = "main/logoutUser";


$(document).on('click', '#logoutButton', function(e) {
	e.preventDefault();
	console.log("logout");
	$.ajax({
		type : 'GET',
		url : logoutURL,
		contentType : 'application/json',
		dataType : "text",
		success : function(data) {
			if(data=="logout") {
				window.location.href = "http://localhost:8080/CertGen.html";
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
});




