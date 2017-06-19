var getSubjectData = "byCACertificates/getSubjectData";
var genCertificateByCA = "byCACertificates/generateBYCA";


printSubjectData();


function printSubjectData() {
	console.log('insert issuer data into drop-down');
	$.ajax({
		type : 'GET',
		url : getSubjectData,
		contentType : 'application/json',
		dataType : "json",
		success : function(data) {
			$(".issuerSertificates").empty();
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
			var subjectSertificates = $(".issuerSertificates");
			$.each(list, function(index, aliases) {
				var li = $('<option value="'+aliases+'">' + aliases + ' </option>');
				$(subjectSertificates).append(li);
				
			});

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
}

//GET DATA FROM HTML


$(document).on('submit', '.genByCA', function(e) {
	e.preventDefault();
	var issuerSertificates=$('.issuerSertificates').find(":selected").val();
	var password = $(this).find("input[name=password]").val();
	
	var password1 = $(this).find("input[name=password1]").val();
	
	var alias = $(this).find("input[name=alias]").val();
	var serialNumber = $(this).find("input[name=serialNumber]").val();
	var commonName = $(this).find("input[name=commonName]").val();
	var organisationUnit = $(this).find("input[name=organisationUnit]").val();
	var organisationName = $(this).find("input[name=organisationName]").val();
	var localityName = $(this).find("input[name=localityName]").val();
	var stateName = $(this).find("input[name=stateName]").val();
	var country = $(this).find("input[name=country]").val();
	var lifeTime = $(this).find("input[name=lifeTime]").val();


	$.ajax({
		type : 'POST',
		url : genCertificateByCA,
		contentType : 'application/json',
		dataType : "text",
		data : formToJSONBYCA(password1, password, alias, issuerSertificates, serialNumber, commonName, organisationUnit, organisationName, localityName,
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

function formToJSONBYCA(password1, password, alias, issuerSertificates, serialNumber, commonName, organisationUnit, organisationName, localityName,
		stateName, country, lifeTime) {
	return JSON.stringify({
		"password1" : password1,
		"password" : password,
		"alias" : alias,
		"issuerSertificates" : issuerSertificates,
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