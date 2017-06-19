var searchFieldURL = "main/revokeCertificate";


$(document).on('submit', '.searchForm', function(e) {
	e.preventDefault();
	var searchField = $(this).find("input[name=searchField]").val();

	$.ajax({
		type : 'POST',
		url : searchFieldURL,
		contentType : 'application/json',
		dataType : "text",
		data : formToJSONRevoke(searchField),
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
				toastr.info('Certificate successfully revoked.');
				 
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
	
});

function formToJSONRevoke(searchField) {
	return JSON.stringify({
		"searchField" : searchField,

	});
}