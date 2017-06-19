var genKeyStoreURL = "keystoreController/generateKeystore";


$(document).on('submit', '.genKeyStore', function(e) {
	e.preventDefault();
	var fileName = $(this).find("input[name=fileName]").val();
	var password = $(this).find("input[name=password]").val();
	var fileName1 = $(this).find("input[name=fileName1]").val();
	var password1 = $(this).find("input[name=password1]").val();

	$.ajax({
		type : 'POST',
		url : genKeyStoreURL,
		contentType : 'application/json',
		dataType : "text",
		data : formToJSONKS(fileName, password, fileName1, password1),
		success : function(data) {
			window.location.href = "http://localhost:8080/CertGen.html";
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
	
});

function formToJSONKS(fileName, password, fileName1, password1) {
	return JSON.stringify({
		"fileName" : fileName,
		"password" : password,
		"fileName1" : fileName1,
		"password1" : password1,
	});
}