JsMockito.Integration.JsTestDriver();

describe("this is how PingerViewModel should behave", function() {

  it("checks a call to pinger", function(){
  	//given
  	var mockPinger = mock(Pinger);
	//when
	PingerViewModel.setPinger(mockPinger);
	PingerViewModel.accessResource();	
	
	//then
    verify(mockPinger).ping("ping.do");
  });
});