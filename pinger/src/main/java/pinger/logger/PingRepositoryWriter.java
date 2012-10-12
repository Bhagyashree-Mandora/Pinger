package pinger.logger;

import java.io.IOException;

import pinger.servlet.Ping;

public interface PingRepositoryWriter {
	void write(Ping ping) throws IOException;
}
