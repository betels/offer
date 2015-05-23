/**
 * 
 */
(function($) {

	$(document).ready(function() {
		/**
		 * Main screen offers list 
		 */
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function(position){
				var lattitude = position.coords.latitude,
					longitude = position.coords.longitude;
					
				var coordinate = {
					"latitude": lattitude,
					"longitude": longitude
				};
				
		
				$.ajax({
					url : utils.baseUrl + "publicOffers",
					type : "POST",
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					data : JSON.stringify(coordinate),
					success : function(response) {
		
						$('#header').html('<h1 class="text-align-center">Offers Near to You:</h1>');
						var offers = response.results, 
							maincontent = $('#maincontent'), 
							temp = "";
		
						maincontent.empty();
						

						var content = "<table>";
		
						for ( i = 0; i < offers.length; i++) {
							content += '<tr><td>' + 
											'<a href="#pagetwo">' + 
												'<div class="offer-image-div">' +
													'<img src="' + utils.imagesDir + offers[i].imageName + '"/>' +
													'<div class="offer-name-address">' + 
														'<h2>' + offers[i].mealName  + '</h2>' +
														'<div class="offer-address">' + offers[i].address.address + '</div>' + 
														'<div class="offer-price">'+
															'<span class="old-price">€' + offers[i].oldPrice + '</span>'+
															'<span class="new-price"> Now €' + offers[i].newPrice + '</span>'+
														'</div>' + 	
														'<div class="offer-time">' + utils.getTimeLeft(offers[i].endTime) + '<span> Left</span></div>'+													
													'</div>' +
												'</div>' +
												'<input type="hidden" class="meal-id" value="' + offers[i].mealId + '" />' +
											'</a>' + 
											
										'</td></tr><tr class="spacer"></tr>';

						}
						
						content += "</table>";
						maincontent.append(content);
					},
					error : function(xhr, error) {
						alert(error);
					}
				});
			});
		}
		
		/**
		 * View selected offer details
		 */
		$(document).on("click", "a[href='#pagetwo']", function(event) {
			var id = $(this).find("input.meal-id").val();
			$.ajax({
					url : utils.baseUrl + "publicOffers?id=" + id,
					type : "GET",
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					mimeType: 'application/json',					
					success : function(response) {
						
						var offer = response.result,
							offerDetail = $('#offer-detail');
							
							offerDetail.empty(); // Clear previously injected offer from the 
							
						var aDayHourse = 24 * 60 * 60 * 1000;
						var hoursLeft = Math.abs((offer.endTime - new Date().getTime()) / aDayHourse);
													
						var detail = '<div class="offer-image-div">' +
										'<img src="' + utils.imagesDir + offer.imageName + '"/>' +
										'<div class="offer-name-address">' + 
											'<h2>' + offer.mealName  + '</h2>' +
											'<div class="offer-address">' + offer.address.address + '</div>' + 
											'<div class="offer-price">'+
												'<span class="old-price"> €' + offer.oldPrice + '</span>'+
												'<span class="new-price"> Now €' + offer.newPrice + '</span>'+
											'</div>' + 
											'<div class="offer-time">' + utils.getTimeLeft(offer.endTime) + '<span> Left</span></div>'+												
										'</div>' +
									'</div>' +
									'<div class="offer-description"><label>Description</label>' + offer.description + '</div>' + 
									'<div id="offer-map">map comes in here</div>'; 
						
						offerDetail.append(detail);
						
						// Set meal name to the header 
						$('#offer-detail-title').empty().append(offer.mealName);
						
						//-- Map start here
						var latitude = offer.address.latitude,
							longitude = offer.address.longitude,
						    mapOptions = {
							zoom : 12,
							center : new google.maps.LatLng(latitude, longitude)
						};
			
						var map = new google.maps.Map(document.getElementById('offer-map'), mapOptions);
			
						var marker = new google.maps.Marker({
							position : map.getCenter(),
							map : map,
							title : offer.address.address
						});
			
						var infowindow = new google.maps.InfoWindow({
							content : offer.address.address
						});
						
						infowindow.open(map, marker);
						
						var circle = new google.maps.Circle({
							map : map,
							radius : 1609,
							strokeColor : "#0000FF",
							strokeOpacity : 0.4,
							strokeWeight : 3,
							fillColor : "#0000FF",
							fillOpacity : 0.2
						});
					
						circle.bindTo('center', marker, 'position');
						//-- Map end here	
					},
					error : function(xhr, error) {
						alert(error);
					}
				});
		});
		
	});		
})(jQuery);