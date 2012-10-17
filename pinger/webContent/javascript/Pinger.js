var Pinger = {
	startTime : "",

	listener: {onSuccess: {}, onError:{}},

	_start : function() {
		this.startTime = new Date().getTime();
	},

	_getTimeSinceStart : function() {
		return Number(new Date().getTime()) - Number(this.startTime);
	},
	
	addListener : function(listener){
		Pinger.listener = listener;
	},

	ping : function(url) {
		this._start();

		$.ajax({
			url : url,
			cache : false,
			timeout : 5000,
			success: function(data){
				Pinger.listener.onSuccess({
				responseTime : Pinger._getTimeSinceStart(),
				processingTime : data.processingTime,
				errormessage : "noError"
			});
			},
			
			error: function(jqXHR, message){
				Pinger.listener.onError({jqXHR: jqXHR, errormessage: message});	
			}
		})
	}
} 