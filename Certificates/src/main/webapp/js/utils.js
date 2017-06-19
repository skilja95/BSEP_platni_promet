var checkRoleURL = "main/checkRole";

checkRole();

function checkRole() {
	$.ajax({
		type : 'GET',
		url : checkRoleURL,
		contentType : 'application/json',
		dataType : "text",
		success : function(data) {
			var login = $("#loginButton");
			var logout = $("#logoutButton");
			var GenerateKeystore = $('#GenerateKeystore');
			var GenerateSelfSigned = $('#GenerateSelfSigned');
			var CertificateBYCA = $('#CertificateBYCA');
			var CertificateForCustomers = $('#CertificateForCustomers');
			var LoadCSR = $('#LoadCSR');
			var revokeCertificate = $('#revokeCertificate');
			var certificateStatus = $('#certificateStatus');
			
			
			if(data=="null") {
				logout.hide();
				login.show();
				certificateStatus.show();
				GenerateKeystore.hide();
				GenerateSelfSigned.hide();
				CertificateBYCA.hide();
				CertificateForCustomers.hide();
				LoadCSR.hide();
				revokeCertificate.hide();
				//window.location.href = "http://localhost:8080/LoginPage.html";
			} else if (data=="Admin") {
				logout.show();
				login.hide();
				
				GenerateKeystore.show();
				GenerateSelfSigned.show();
				CertificateBYCA.show();
				CertificateForCustomers.hide();
				LoadCSR.show();
				revokeCertificate.show();
				certificateStatus.show();
			} else if (data=="User") { 
				logout.show();
				login.hide();
				
				GenerateKeystore.hide();
				GenerateSelfSigned.hide();
				CertificateBYCA.hide();
				CertificateForCustomers.show();
				LoadCSR.hide();
				revokeCertificate.hide();
				certificateStatus.show();
			} else {
				logout.show();
				login.hide();
				certificateStatus.show();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
}