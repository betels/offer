
var utils = {
	//baseUrl: "http://localhost:8080/Offer/",
	baseUrl: "http://offer-mealoffer.rhcloud.com/Offer/",
	
	imagesDir: "http://offer-mealoffer.rhcloud.com/Offer/offerimages/",
	//imagesDir: "http://localhost:8080/Offer/offerimages/",
	
	
	redirect : function(page) {
		window.location.href = this.baseUrl + page;
		callback();
	},
	/**
	 * 
	 */
	getOffers : function() {
		$.ajax({
			url : this.baseUrl + "Offer",
			type : "GET",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(response) {
				$('#header').html('<h1 class="text-align-center">Meals:</h1>');
				var offers = response.results, maincontent = $('#maincontent'), temp = "";

				maincontent.empty();

				var content = "<table><tr>" + "<th>Meal Id</th>" + "<th>Meal Name</th>" + "<th>Start Date/Time</th>" + "<th>End Date/Time</th>" + "<th>Address</th>" + "<th>Old Price</th>" +"<th>New Price</th>" + "<th>Description</th>" +"<th></th>" + "<th>Action</th></tr>";

				for ( i = 0; i < offers.length; i++) {
					var temptr = "";
					if (i % 2 != 0) {
						temptr = '<tr class="gray-row"><td class="id-column">';
					} else {
						temptr += '<tr><td class="id-column">';
					}
				    content += temptr + 
				    			'<div class="offer-image">' + '<img src="/Offer/offerimages/' + offers[i].imageName + '" /></div>' +
				    			'<input type="hidden" class="meal-id" value="' + offers[i].mealId + '" />' +
				     			'</td><td>' + offers[i].mealName + 
								'</td><td>' + offers[i].startTime + '</td><td>' + offers[i].endTime + 
								'</td><td>' + offers[i].address.address + '</td><td>'  + offers[i].oldPrice +
								'</td><td>' + offers[i].newPrice + '</td> <td>' + offers[i].description + 
								'</td><td>' + '<a href="delete">Delete</a>' + 
								'</td></tr><tr class="spacerOffer"></tr>';

				}
				content += "</table>";
				$('#maincontent').append(content);
			},
			error : function(xhr, error) {
				alert(error);
			}
		});
	},
	/**
	 * 
	 */
	toggleButtons : function() {
		var allcookies = document.cookie;
		var cookiearray = allcookies.split(';');

		for (var i = 0; i < cookiearray.length; i++) {
			name = cookiearray[i].split('=')[0];
			value = cookiearray[i].split('=')[1];
			if (name == "userid") {
				$('#userid').val(value);
				// Hidden to save user id only
				$("#login").css({
					"display" : "none"
				});
				$("#my-offers").css({
					"display" : "block"
				});
				$("#logout").css({
					"display" : "block"
				});
				$("#offerregister").css({
					"display" : "block"
				});
				$("#signup").css({
					"display" : "none"
				});
				break;
			} else {
				$('#userid').val("");
				$("#login").css({
					"display" : "block"
				});
				$("#my-offers").css({
					"display" : "none"
				});
				$("#logout").css({
					"display" : "none"
				});
				$("#offerregister").css({
					"display" : "none"
				});
				$("#signup").css({
					"display" : "block"
				});
			}
		}
	},
	/**
	 * 
	 */
	validateOfferForm : function(mName, sTime, eTime, address, imageName,phone,postalCode,sTimeSpinner,eTimeSpinner,newPrice,oldPrice,description) {
		
			var isValid = true;

			if (mName == "" || mName == undefined) {
				$('#reg-form-mname').css({
					'border' : '2px solid red'
				});
				isValid = false;
			}else{
				$('#reg-form-mname').css({
					'border' : ''
				});
			}
			if (sTime == "" || sTime == undefined) {
				$('#reg-form-stime').css({
					'border' : '2px solid red'
				});
				isValid = false;
			}else{
				$('#reg-form-stime').css({
					'border' : ''
				});
			}
			if (eTime == "" || eTime == undefined) {
				$('#reg-form-etime').css({
					'border' : '2px solid red'
				});
				isValid = false;
			}else{
				$('#reg-form-etime').css({
					'border' : ''
				});
			}
			if (address == "" || address == undefined) {
				$('#reg-form-address').css({
					'border' : '2px solid red'
				});
				isValid = false;
			}else{
				$('#reg-form-address').css({
					'border' : ''
				});
			}
				if (oldPrice == "" || oldPrice == undefined) {
				$('#reg-form-oldPrice').css({
					'border' : '2px solid red'
				});
				isValid = false;
			}else{
				$('#reg-form-oldPrice').css({
					'border' : ''
				});
			}
				if (newPrice == "" || newPrice == undefined) {
				$('#reg-form-newPrice').css({
					'border' : '2px solid red'
				});
				isValid = false;
			}else{
				$('#reg-form-newPrice').css({
					'border' : ''
				});
			}
				if (description == "" || description == undefined) {
				$('#reg-form-description').css({
					'border' : '2px solid red'
				});
				isValid = false;
			}else{
				$('#reg-form-description').css({
					'border' : ''
				});
			}
			if (imageName == "" || imageName == undefined) {
				$('#reg-form-imageName').css({
					'border' : '2px solid red'
				});
               isValid = false;
			}else{
				$('#reg-form-imageName').css({
					'border' : ''
				});
			}
				if (phone == "" || phone == undefined) {
				$('#reg-form-phone').css({
					'border' : '2px solid red'
				});
               isValid = false;
			}else{
				$('#reg-form-phone').css({
					'border' : ''
				});
			}		
			if (postalCode == "" || postalCode == undefined) {
				$('#reg-form-postalcode').css({
					'border' : '2px solid red'
				});
               isValid = false;
			}else{
				$('#reg-form-postalcode').css({
					'border' : ''
				});
			}
			if (sTimeSpinner == "" || sTimeSpinner == undefined) {
				$('#stime-spinner').css({
					'border' : '2px solid red'
				});
               isValid = false;
			}else{
				$('#stime-spinner').css({
					'border' : ''
				});
			}
				if (eTimeSpinner == "" || eTimeSpinner == undefined) {
				$('#etime-spinner').css({
					'border' : '2px solid red'
				});
               isValid = false;
			}else{
				$('#etime-spinner').css({
					'border' : ''
				});
			}
			
			return isValid;
	},
	/**
	 * 
	 * @param {Object} formData
	 * @param {Object} callback
	 */
	previewImages: function(formData, callback){
           $.ajax({
	           url : this.baseUrl + "image",
	            type: 'POST',
	            data:  formData,
	            mimeType:"multipart/form-data",
	            contentType: false,
	            cache: false,
	            processData:false,
	            success: function(data, textStatus, jqXHR)
	            {
			   		callback(data);
	            },
	            error: function(jqXHR, textStatus, errorThrown) 
	            {
	 				//TODO
	            }           
	       });
	},
	/**
	 * 
	 * @param {Object} address
	 * @param {Object} callBack
	 */
	 getCoordinate: function(address,callBack) {
	
		var geo = new google.maps.Geocoder;
		var addressCriteria = {
			"address" : address
		};
		
		geo.geocode(addressCriteria, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				var cordi = results[0].geometry.location;
				callBack(cordi);
			} else {
				alert("Geocode was not successful for the following reason: " + status);
			}
		});
	},
	/**
	 * 
	 */
	// @ Reference http://stackoverflow.com/questions/25623007/jquery-countdown-timer-display-only-hours-minutes-and-seconds-calculated-from-da
	getTimeLeft: function (endTime){
	    var now = new Date();
	    var distance = new Date(endTime) - now;

		var _second = 1000,
		_minute = _second * 60,
		_hour = _minute * 60,
		_day = _hour * 24,

	    days = Math.floor(distance / _day),
	    hours = Math.floor((distance % _day) / _hour),
	    minutes = Math.floor((distance % _hour) / _minute),
	    seconds = Math.floor((distance % _minute) / _second);

		var timeLeft = Math.abs(days) + ' days  ' + Math.abs(hours) + ' hrs  ' + Math.abs(minutes) + ' mins  ' + Math.abs(seconds) + ' secs';
		return timeLeft;
	},
	/**
	 * 
 * @param {Object} email
	 */
	validateEmail:function (email) {
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	}

};
