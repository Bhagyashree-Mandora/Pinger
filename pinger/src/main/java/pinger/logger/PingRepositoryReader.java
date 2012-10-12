package pinger.logger;

import java.util.List;

import pinger.servlet.Ping;

public interface PingRepositoryReader {
	List<Ping> readAll();
}
