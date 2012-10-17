describe("This is how a Pinger should behave", function() {
	
	var mockPingerViewModel;
	var url = "ping.do";

	beforeEach(function() {
		mockPingerViewModel = jasmine.createSpyObj('PingerViewModel', ['onSuccess', 'onError']);
		
		Pinger.addListener(mockPingerViewModel);
	});

	it("should invoke success callback", function() {
		//Given
		var fakeData = {
			processingTime : 2000
		};
		
		// var fakeResponse = {
			// responseTime : 0,
			// processingTime : 2000,
			// errormessage : "noError"
		// }

		spyOn($, "ajax").andCallFake(function(param) {
			param.success(fakeData);
		});

		//When
		Pinger.ping(url);

		//Then
		expect(mockPingerViewModel.onSuccess).toHaveBeenCalled();
	});
	
	it("should invoke error callback", function() {
		//Given
		var fakeErrorMessage = "error";
		
		spyOn($, "ajax").andCallFake(function(param) {
			param.error(fakeErrorMessage);
		});

		//When
		Pinger.ping(url);

		//Then
		expect(mockPingerViewModel.onError).toHaveBeenCalled();
	});
	
});