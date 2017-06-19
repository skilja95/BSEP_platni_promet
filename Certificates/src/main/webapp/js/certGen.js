var searchFieldURL = "main/searchData";


$(document).on('submit', '.searchForm', function(e) {
	e.preventDefault();
	var searchField = $(this).find("input[name=searchField]").val();
	var password = $(this).find("input[name=password]").val();

	$.ajax({
		type : 'POST',
		url : searchFieldURL,
		contentType : 'application/json',
		dataType : "text",
		data : formToJSONSearch(searchField,password),
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
				toastr.info('Certificate successfully downloaded.');
				 
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
	
});

function formToJSONSearch(searchField, password) {
	return JSON.stringify({
		"searchField" : searchField,
		"password" : password,

	});
}