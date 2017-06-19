var uploadURL = "customersCertificates/uploadFILE";
var getSubjectData = "customersCertificates/getCertAliases";
var generateCertByCSRURL = "customersCertificates/generateCertByCSR";

function setValueToHidden() {
    $('[name=fileName]').val($('#filePost')[0].files[0].name);
}

$(document).on('submit', '.loadCSR', function(e) {
	e.preventDefault();
	var fileName = $(this).find("[name=fileName]").val();

	$.ajax({
		type : 'POST',
		url : uploadURL,
		contentType : 'application/json',
		dataType : "json",
		data : formToJSONLoad(fileName),
		success : function(data) {
				printCSRData(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
	
});

function formToJSONLoad(fileName) {
	return JSON.stringify({
		"fileName" : fileName,
	});
}

function printCSRData(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an
	// object (not an 'array of one')
	$('.csrDataString').empty();
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list,
			function(index, csrPom) {
				var option = $('<form class="acceptCSR"> ' +
						'<div class="form-group"> ' +
						' <label for="concept" class="col-sm-3 control-label">Issuer cert alias:</label>' +
						'<div class="col-sm-9"> ' +
						'	<select class="form-control subjectCertificate"></select> ' +

						'</div> ' +
					'</div>' +
						'<div class="form-group"> ' +
						' <label for="concept" class="col-sm-3 control-label">KeyStore password(ALL):</label>' +
						'<div class="col-sm-9"> ' +
						'	<input type="password" class="form-control" name="password" >' +

						'</div> ' +
					'</div>' +

										'<hr>'+
										'<div class="form-group"> ' +
										' <label for="concept" class="col-sm-3 control-label">Alias:</label>' +
										'<div class="col-sm-9"> ' +
										'	<input type="text" class="form-control" name="alias"> ' +
										'</div> ' +
									'</div>' +
									
									'<div class="form-group"> ' +
									' <label for="concept" class="col-sm-3 control-label">Serial number:</label>' +
									'<div class="col-sm-9"> ' +
									'	<input type="text" class="form-control" name="serialNumber" ' +
									'		value="'+ csrPom.serialNumber+' " readonly> ' +
									'</div> ' +
								'</div>' +
										
						'<div class="form-group"> ' +
						' <label for="concept" class="col-sm-3 control-label">Common name:</label>' +
						'<div class="col-sm-9"> ' +
						'	<input type="text" class="form-control" name="commonName" ' +
						'		value="'+ csrPom.commonName+' " readonly> ' +
						'</div> ' +
					'</div>' +
					'<div class="form-group"> ' +
					' <label for="concept" class="col-sm-3 control-label">Organisation name:</label>' +
					'<div class="col-sm-9"> ' +
					'	<input type="text" class="form-control" name="organisationName" ' +
					'		value="'+ csrPom.organisation+' " readonly> ' +
					'</div> ' +
				'</div>' +
				'<div class="form-group"> ' +
				' <label for="concept" class="col-sm-3 control-label">Organisation Unit:</label>' +
				'<div class="col-sm-9"> ' +
				'	<input type="text" class="form-control" name="organisationUnit" ' +
				'		value="'+ csrPom.organisationUnit+' " readonly> ' +
				'</div> ' +
			'</div>' +
			'<div class="form-group"> ' +
			' <label for="concept" class="col-sm-3 control-label">State:</label>' +
			'<div class="col-sm-9"> ' +
			'	<input type="text" class="form-control" name="stateName" ' +
			'		value="'+ csrPom.state+' " readonly> ' +
			'</div> ' +
		'</div>' +
				'<div class="form-group"> ' +
				' <label for="concept" class="col-sm-3 control-label">Country:</label>' +
				'<div class="col-sm-9"> ' +
				'	<input type="text" class="form-control" name="country" ' +
				'		value="'+csrPom.country+'" readonly> ' +
				'</div> ' +
			'</div>'+
			'<div class="form-group"> ' +
			' <label for="concept" class="col-sm-3 control-label">Locale:</label>' +
			'<div class="col-sm-9"> ' +
			'	<input type="text" class="form-control" name="localityName" ' +
			'		value="'+csrPom.locale+'" readonly> ' +
			'</div> ' +
		'</div>'+
	
	'<button class="btn btn-success btn-block login" type="submit">Accept CSR request</button> '+
	'</form>');
				$('.csrDataString').append(option);
				printSubjectData();
			});
}




function printSubjectData() {
	console.log('insert subject data into drop-down');
	$.ajax({
		type : 'GET',
		url : getSubjectData,
		contentType : 'application/json',
		dataType : "json",
		success : function(data) {
			$(".subjectCertificate").empty();
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
			var subjectSertificates = $(".subjectCertificate");
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



//-------------------------
//GET DATA FROM HTML


$(document).on('submit', '.acceptCSR', function(e) {
	e.preventDefault();
	var subjectCertificate=$('.subjectCertificate').find(":selected").val();
	var password = $(this).find("input[name=password]").val();
	var alias = $(this).find("input[name=alias]").val();
	
	var serialNumber = $(this).find("input[name=serialNumber]").val();
	var commonName = $(this).find("input[name=commonName]").val();
	var organisationUnit = $(this).find("input[name=organisationUnit]").val();
	var organisationName = $(this).find("input[name=organisationName]").val();
	var localityName = $(this).find("input[name=localityName]").val();
	var stateName = $(this).find("input[name=stateName]").val();
	var country = $(this).find("input[name=country]").val();



	$.ajax({
		type : 'POST',
		url : generateCertByCSRURL,
		contentType : 'application/json',
		dataType : "text",
		data : formToJSONbyCSR(password, alias, subjectCertificate, serialNumber, commonName, organisationUnit, organisationName, localityName,
				stateName, country),
		success : function(data) {
			if(data == "OK")
				window.location.href = "http://localhost:8080/CertGen.html";
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
	
});

function formToJSONbyCSR(password, alias, subjectCertificate, serialNumber, commonName, organisationUnit, organisationName, localityName,
		stateName, country) {
	return JSON.stringify({
		"password" : password,
		"alias" : alias,
		"subjectCertificate" : subjectCertificate,
		"serialNumber" : serialNumber,
		"commonName" : commonName,
		"organisationUnit" : organisationUnit,
		"organisationName" : organisationName,
		"localityName" : localityName,
		"stateName" : stateName,
		"country" : country,
	});
}