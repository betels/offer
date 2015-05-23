(function($) {

	/**
	 * Main stars from here
	 */
	$(document).ready(function() {

		/****   ***/
		utils.toggleButtons();

		/*********************************************
		 * signup btn
		 *********************************************/
		$(document).on("click", "#signup", function() {
			$.ajax({
				url : utils.baseUrl + "registrationForm.html",
				success : function(response) {
					$('#maincontent').html(response);
					$('#header').html('<h1 class="text-align-center">Register:</h1>');
				}
			});
		});

		/*********************************************
		 * register btn
		 *********************************************/
		$(document).on("click", "#register-btn", function() {
			var fName, lName, email, password;
			fName = $('#reg-form-fname').val();
			lName = $('#reg-form-lname').val();
			email = $('#reg-form-email').val();
			password = $('#reg-form-password').val();

			var isValid = true;

			if (fName == "" || fName == undefined) {
				$('#reg-form-fname').css({
					'border' : '2px solid red'
				});
				isValid = false;
			}else{
				$('#reg-form-fname').css({
					'border' : ''
				});
			}
			if (lName == "" || lName == undefined) {
				$('#reg-form-lname').css({
					'border' : '2px solid red'
				});
				isValid = false;
			}else{
				$('#reg-form-lname').css({
					'border' : ''
				});
			}
			if (email == "" || email == undefined) {
				$('#reg-form-email').css({
					'border' : '2px solid red'
				});
				isValid = false;
			}else{
				var isValidEmail = utils.validateEmail(email);
				if (isValidEmail) {
					$('#reg-form-email').css({
						'border' : ''
					});
				} else {
					$('#reg-form-email').css({
						'border' : '2px solid red'
					});
					$('#reg-form-email').val(email + " is not valid email.");
					isValid = false;
				}
			}
			if (password == "" || password == undefined) {
				$('#reg-form-password').css({
					'border' : '2px solid red'
				});
				isValid = false;
			}else{
				$('#reg-form-password').css({
					'border' : ''
				});
			}




           if(isValid){
			
			// Construct data from form
			var data = {
				"firstName" : fName,
				"lastName" : lName,
				"eMail" : email,
				"password" : password
			};

			// send data to backend via ajax
			$.ajax({
				url : utils.baseUrl +"User",
				type : "POST",
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				data : JSON.stringify(data),
				success : function(result) {
					if (result.success) {
						$("#registration-message").html("You are succesfully registered");
						var intervalId = setInterval(function() {
							$.ajax({
								url : utils.baseUrl + "login.html",
								success : function(response) {
									$('#maincontent').html(response);
									$('#header').html('<h1 class="text-align-center">Login</h1>');
									clearInterval(intervalId);
								}
							});
						}, 3000);
					} else {
						$("#registration-message").html("Registration failed");
					}
				},
				error : function(xhr, error) {
					alert(error);
				}
			});
			}else {
				$('#registration-error-message').html("Please complete values in red ");
			}
		});

		/*********************************************
		 * sign in btn
		 *********************************************/

		$(document).on("click", "#sign-btn", function() {
			var emailElemnt = $('#log-form-email'), passwordElement = $('#log-form-password'), messageElement = $("#login-error-message"), email = emailElemnt.val(), password = passwordElement.val();

			var isValid = true;

			if (email == "" || email == undefined) {
				emailElemnt.css({
					'border' : '2px solid red'
				});
				isValid = false;
			} else {
				emailElemnt.css({
					'border' : ''
					});
				}


			if (password == "" || password == undefined) {
				passwordElement.css({
					'border' : '2px solid red'
				});
				isValid = false;
			}else{
				passwordElement.css({
					'border' : ''
				});
			}

			if (isValid) {
				// Construct data from form
				var data = {
					"eMail" : email,
					"password" : password
				};
				// send data to backend via ajax
				$.ajax({
					url : utils.baseUrl+"login",
					type : "POST",
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					data : JSON.stringify(data),
					success : function(result) {
						if (result.success) {
							utils.toggleButtons();
							utils.getOffers();
						} else {
							messageElement.html("Invalid E-mail/Password combination");
						}
					},
					error : function(xhr, error) {
						messageElement.html("Error encountered");

					}
				});
			} else {
				messageElement.html("Missing E-mail/Password combination");
			}
		});

		/*********************************************
		 * login btn
		 * Inject contents of login.html into maincontent div on index.html
		 *********************************************/
		$(document).on("click", "#login", function() {
			$.ajax({
				url : utils.baseUrl + "login.html",
				success : function(response) {
					$('#maincontent').html(response);
					$('#header').html('<h1 class="text-align-center">Login</h1>');
				}
			});
		});

		/*********************************************
		 * logout btn
		 *********************************************/
		$(document).on("click", "#logout", function() {
			$.ajax({
				url : utils.baseUrl + "login",
				type : "GET",
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(result) {
					if (result.success) {
						utils.redirect("index.html");
						utils.toggleButtons();
					} else {
						$("#login-error-message").html("Invalid E-mail/Password combination");
					}
				},
				error : function(xhr, error) {
					$("#login-error-message").html("Error encountered");
				}
			});
		});

		// $('a[href^="delete"]').on("click", function(event){
		// event.preventDefault();
		// alert("delete");
		// return false;
		// });
	});

})(jQuery);

