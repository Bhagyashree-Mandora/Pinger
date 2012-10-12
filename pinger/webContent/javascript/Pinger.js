var Pinger = {
	startTime : "",

	response : {
		responseTime : "",
		processingTime : "",
		errorMessage : ""
	},

	_start : function() {
		this.startTime = new Date().getTime();
	},

	_getTimeSinceStart : function() {
		return Number(new Date().getTime()) - Number(this.startTime);
	},

	ping : function(url) {
		this._start();

		var ajaxPromise = $.ajax({
			url : url,
			cache : false,

			timeout : 5000
		});

		var deffered = $.Deferred();
		ajaxPromise.then(function(data) {

			Pinger.response = {
				responseTime : Pinger._getTimeSinceStart(),
				processingTime : data.processingTime,
				errormessage : "noError"
			};

			deffered.resolve(Pinger.response);

		}, function(jqxhr, strError) {

			if (strError == 'timeout') {
				Pinger.response = {
				responseTime : "",
				processingTime : "",
				errormessage : "timeout"
			};
			} else {
				Pinger.response = {
				responseTime : "",
				processingTime : "",
				errormessage : "unavailable"
			};
			}

			deffered.resolve(Pinger.response);
		});
		return deffered.promise();

	}
} 