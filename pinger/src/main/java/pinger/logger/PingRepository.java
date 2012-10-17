package pinger.logger;

import java.util.List;

import pinger.servlet.Ping;

public interface PingRepository {
	void write(Ping ping);
	List<Ping> readAll();
	void closeConnection();
}