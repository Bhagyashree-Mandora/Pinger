describe("This is how a pinger should behave", function() {


	it("should give response and pocessing time", function() {
		//given
		var url = "resources/bg-image.jpg";		
				
		//when
		var response = Pinger.ping(url);
		
		//then
		expect(response.processingTime).toBeGreaterThan(0);
		expect(response.responseTime).toBeGreaterThan(0);
	});

});