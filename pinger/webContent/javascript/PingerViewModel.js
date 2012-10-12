var PingerViewModel = {

	buttonText : ko.observable("Ping Me!"),
	responseTime : ko.observable(""),
	procTime : ko.observable(""),
	networkLatency : ko.observable(""),
	errorMessage : ko.observable(""),

	accessResource : function() {

		turnViewBusy();
		var response = Pinger.ping("ping.do");

		response.done(function(result) {

			if (result.errormessage == "timeout") {
				PingerViewModel.errorMessage("Request has timed out. Ping Again!");
				PingerViewModel.buttonText("");
				PingerViewModel.responseTime("");
				PingerViewModel.procTime("");
				PingerViewModel.networkLatency("");
			} 
			
			if (result.errormessage == "unavailable") {
				PingerViewModel.errorMessage("Request is unavailable. Ping Again!");
				PingerViewModel.buttonText("");
				PingerViewModel.responseTime("");
				PingerViewModel.procTime("");
				PingerViewModel.networkLatency("");
			} 
			
			if(result.errormessage == "noError") {
				PingerViewModel.errorMessage("");
				var networkDelay = (result.responseTime - result.processingTime);
				var percentNetworkDelay = (networkDelay / result.responseTime) * 100;
				var percentProcTime = (result.processingTime / result.responseTime) * 100;
				PingerViewModel.responseTime("response time: " + result.responseTime + " ms");
				PingerViewModel.procTime("processing Time: " + result.processingTime + " ms [" + percentProcTime.toFixed(4) + "%]");
				PingerViewModel.networkLatency("network delay: " + networkDelay + " ms [" + percentNetworkDelay.toFixed(4) + "%]");
				PingerViewModel.buttonText("Ping Again!");
			}
			turnViewActive();

		});
	}
}