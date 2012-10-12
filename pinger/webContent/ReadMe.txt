README

1. This webapp pings a dummy server and displays response time details.
2. Set MySQL username password in web.xml and pingTrend (pingTrend.bat for Windows).
3. Run command mysql -u <username> -p < contf.txt
4. To get ping statistics in <file>,
	a. change directory to <container_path>/pinger	
	b. run ./pingTrend -f <file_path> (pingTrend for Windows)
