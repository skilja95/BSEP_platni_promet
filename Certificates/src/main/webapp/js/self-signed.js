var generateSSC = "ssccontroller/generateSSC";
var getKeyStores = "ssccontroller/getKeystores";


$(document).on('submit', '.genCertificate', function(e) {
	e.preventDefault();
	var fileName = $('.fileName').find(":selected").val();
	var alias = $(this).find("input[name=alias]").val();
	var password = $(this).find("input[name=password]").val();
	
	var serialNumber = $(this).find("input[name=serialNumber]").val();
	var commonName = $(this).find("input[name=commonName]").val();
	var organisationUnit = $(this).find("input[name=organisationUnit]").val();
	var organisationName = $(this).find("input[name=organisationName]").val();
	var localityName = $(this).find("input[name=localityName]").val();
	var stateName = $(this).find("input[name=stateName]").val();
	var country = $(this).find("input[name=country]").val();
	var lifeTime = $(this).find("input[name=lifeTime]").val();

	console.log(organisationUnit);
	$.ajax({
		type : 'POST',
		url : generateSSC,
		contentType : 'application/json',
		dataType : "text",
		data : formToJSONSSC(fileName,alias, password, serialNumber, commonName, organisationUnit, organisationName, localityName,
				stateName, country, lifeTime),
		success : function(data) {
			if(data == "OK")
				window.location.href = "http://localhost:8080/CertGen.html";
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
	
});

function formToJSONSSC(fileName, alias, password, serialNumber, commonName, organisationUnit, organisationName, localityName,
		stateName, country, lifeTime) {
	return JSON.stringify({
		"fileName" : fileName,
		"alias" : alias,
		"password" : password,
		"serialNumber" : serialNumber,
		"commonName" : commonName,
		"organisationUnit" : organisationUnit,
		"organisationName" : organisationName,
		"localityName" : localityName,
		"stateName" : stateName,
		"country" : country,
		"lifeTime" : lifeTime,
	});
}

//-------------------popunjavanje drop-down
printKeyStores();


function printKeyStores() {
	$.ajax({
		type : 'GET',
		url : getKeyStores,
		contentType : 'application/json',
		dataType : "json",
		success : function(data) {
			$(".fileName").empty();
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
			var keystores = $(".fileName");
			$.each(list, function(index, aliases) {
				var li = $('<option value="'+aliases+'">' + aliases + ' </option>');
				$(keystores).append(li);
				
			});

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
}