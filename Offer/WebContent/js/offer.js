(function($) {

	$(document).ready(function() {

		/**
		 * Register an offer
		 */
		
			
		$(document).on("click", "#offer-register-btn", function() {
			var mName, sTime, eTime, address, imageName, postalCode, phone, fullAddress, country, eTimeSpinner, sTimeSpinner, newPrice,oldPrice,description;
			mName = $('#reg-form-mname').val();
			sTime = $('#reg-form-stime').val();
			eTime = $('#reg-form-etime').val();
			address = $('#reg-form-address').val();
			postalCode = $('#reg-form-postalcode').val();
			phone = $('#reg-form-phone').val();
			imageName = $("#image-name").val();//$('#reg-form-imageName').val();
			eTimeSpinner = $('#etime-spinner').val();
			sTimeSpinner = $('#stime-spinner').val();
			newPrice = $('#reg-form-newPrice').val();
			oldPrice = $('#reg-form-oldPrice').val();
			description = $('#reg-form-description').val();
			country = "Ireland";
			fullAddress = address + ", " + postalCode + ", " + country;

			var parsedSDate = sTime, parsedSTime = sTimeSpinner;

			startDateAndTime = parsedSDate + 'T' + parsedSTime + '.919Z';

			var parsedEDate = eTime, parsedETime = eTimeSpinner;

			endDateAndTime = parsedEDate + 'T' + parsedETime + '.919Z';

			var isValid = utils.validateOfferForm(mName, sTime, eTime, address, imageName, phone, postalCode, sTimeSpinner, eTimeSpinner,newPrice,oldPrice,description);

			if (isValid) {

				utils.getCoordinate(fullAddress, function(coordinate) {
					console.log('coordinate.lat(), coordinate.lng() ' + coordinate.lat() + ", " + coordinate.lng());
					// Construct data from form
					var data = {
						"mealName" : mName,
						"startTime" : startDateAndTime,
						"endTime" : endDateAndTime,
						"address" : {
							"address" : fullAddress,
							"postalCode" : postalCode,
							"country" : country,
							"phone" : phone,
							"latitude" : coordinate.lat() ,
							"longitude" : coordinate.lng()
						},
						"imageName" : imageName,
						"newPrice" : newPrice,
						"oldPrice" : oldPrice,
						"description" : description
					};

					// send data to backend via ajax
					$.ajax({
						url : utils.baseUrl + "Offer",
						type : "POST",
						contentType : "application/json; charset=utf-8",
						dataType : "json",
						mimeType: 'application/json',
						data : JSON.stringify(data),
						success : function(result) {
							if (result.success) {
								utils.getOffers();
							} else {
								$("#offer-registration-message").html("Registration failed");
							}
						},
						error : function(xhr, error) {
							alert(error);
						}
					});					
				});
			} else {
				$('#offer-registration-error-message').html("Please complete values in red ");
			}
		});
		
		/**
		 * image preview 
		 */
		
		$(document).on('change', '#reg-form-imageName', function(event) {
			var formData = new FormData();
			jQuery.each($('#reg-form-imageName')[0].files, function(i, file) {
				formData.append('image', file);
			});

			var $images = $('#image-preview');
			utils.previewImages(formData, function(response) {
				var $parsed = JSON.parse(response),
				    $imagePreview =$('#image-preview');
				$imagePreview.html("");// Clear content evry time if content exists before appending new
				if($parsed.sucess){
					var $img = $('<img src="/Offer/offerimages/' + $parsed.fileName + '"/>');
					$imagePreview.append($img);
					$("#image-name").val($parsed.fileName);				
				} else {
					var $span = $('<span style="color: red;">' + $parsed.errormessage + '</span>');
					$imagePreview.append($span);			
				}
			});
		});

		/**
		 *  offer register view
		 */
		$(document).on("click", "#offerregister", function() {
			$.ajax({
				url : utils.baseUrl + "offer-register-form.html",
				success : function(response) {
					$('#maincontent').html(response);
					$('#header').html('<h1 class="text-align-center">Register an Offer</h1>');
				}
			});
		});

		/**
		 *
		 */
		$(document).on("click", "a[href='delete']", function(event) {
			event.preventDefault();
			var id = $(this).parent().siblings(".id-column").find("input.meal-id").val();
			var isDelete = confirm("Are you sure you want to delete this record?");
			if (isDelete) {
				$.ajax({
					url : utils.baseUrl+"Offer?mealId=" + id,
					type : "DELETE",
					async : false,
					success : function(response) {
						if (response.success) {
							utils.getOffers();
						}
					}
				});
			}
		});

		/**
		 *
		 */
		$(document).on("click", "#home", function() {
			utils.redirect("index.html");
			utils.toggleButtons();
		});
		
		
		/**
		 *
		 */
		$(document).on("click", "#my-offers", function() {
			utils.getOffers();
			utils.toggleButtons();
		});

	});

})(jQuery);
