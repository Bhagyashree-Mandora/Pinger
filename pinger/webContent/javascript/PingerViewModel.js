var PingerViewModel = {

	buttonText : ko.observable("Ping Me!"),
	responseTimeText : ko.observable(""),
	processingTimeText : ko.observable(""),
	networkLatencyText : ko.observable(""),
	isFailed : ko.observable(false),
	errorText : ko.observable(""),
	isBusy : ko.observable(false),
	pinger : Pinger,

	setPinger : function(pinger) {
		PingerViewModel.pinger = pinger;
	},

	accessResource : function() {
		PingerViewModel.isBusy(true);
		PingerViewModel.pinger.addListener(PingerViewModel);
		PingerViewModel.pinger.ping("ping.do");
	},

	onSuccess : function(result) {
		PingerViewModel.isFailed(false);

		var networkDelay = (result.responseTime - result.processingTime);
		var percentNetworkDelay = (networkDelay / result.responseTime) * 100;
		var percentProcessingTime = (result.processingTime / result.responseTime) * 100;

		PingerViewModel.responseTimeText("response time: " + result.responseTime + " ms");
		PingerViewModel.processingTimeText("processing Time: " + result.processingTime + " ms [" + percentProcessingTime.toFixed(4) + "%]");
		PingerViewModel.networkLatencyText("network delay: " + networkDelay + " ms [" + percentNetworkDelay.toFixed(4) + "%]");
		PingerViewModel.buttonText("Ping Again!");
		PingerViewModel.isBusy(false);
	},

	onError : function(jqXHR, errormessage) {
		PingerViewModel.isFailed(true);

		if (jqXHR.errormessage == "timeout") {
			PingerViewModel.errorText("Request has timed out. Ping Again!");
		}

		if (jqXHR.errormessage == "unavailable") {
			PingerViewModel.errorText("Request is unavailable. Ping Again!");
		}
		PingerViewModel.isBusy(false);
	}
}

