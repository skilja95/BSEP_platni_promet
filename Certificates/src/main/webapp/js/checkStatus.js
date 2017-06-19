var searchFieldURL = "main/checkStatus";


$(document).on('submit', '.searchForm', function(e) {
	e.preventDefault();
	var searchField = $(this).find("input[name=searchField]").val();

	$.ajax({
		type : 'POST',
		url : searchFieldURL,
		contentType : 'application/json',
		dataType : "text",
		data : formToJSONStatus(searchField),
		success : function(data) {
			if (data == "OK") {

				toastr.options = {
					"closeButton" : true,
					"debug" : false,
					"newestOnTop" : false,
					"progressBar" : false,
					"positionClass" : "toast-top-right",
					"preventDuplicates" : false,
					"onclick" : null,
					"showDuration" : "300",
					"hideDuration" : "1000",
					"timeOut" : "5000",
					"extendedTimeOut" : "1000",
					"showEasing" : "swing",
					"hideEasing" : "linear",
					"showMethod" : "fadeIn",
					"hideMethod" : "fadeOut"
				}
				toastr.info('Certificate is OK.');
				 
			}
			
			if (data == "NOT") {

				toastr.options = {
					"closeButton" : true,
					"debug" : false,
					"newestOnTop" : false,
					"progressBar" : false,
					"positionClass" : "toast-top-right",
					"preventDuplicates" : false,
					"onclick" : null,
					"showDuration" : "300",
					"hideDuration" : "1000",
					"timeOut" : "5000",
					"extendedTimeOut" : "1000",
					"showEasing" : "swing",
					"hideEasing" : "linear",
					"showMethod" : "fadeIn",
					"hideMethod" : "fadeOut"
				}
				toastr.info('Certificate is revoked.');
				 
			}
			
			if (data == "unknown") {

				toastr.options = {
					"closeButton" : true,
					"debug" : false,
					"newestOnTop" : false,
					"progressBar" : false,
					"positionClass" : "toast-top-right",
					"preventDuplicates" : false,
					"onclick" : null,
					"showDuration" : "300",
					"hideDuration" : "1000",
					"timeOut" : "5000",
					"extendedTimeOut" : "1000",
					"showEasing" : "swing",
					"hideEasing" : "linear",
					"showMethod" : "fadeIn",
					"hideMethod" : "fadeOut"
				}
				toastr.info('Serial number does not exists.');
				 
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
	
});

function formToJSONStatus(searchField) {
	return JSON.stringify({
		"searchField" : searchField,

	});
}