package udp.wrapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class UDPSocket extends Socket {
	
	@Override
	public InputStream getInputStream() throws IOException {
		return null;
	}
	
	@Override
	public OutputStream getOutputStream() throws IOException {
		return null;
	}
	
	@Override
	public void setSoTimeout(int timeout) throws SocketException {		
	}
	
	@Override
	public int getSoTimeout() throws SocketException {
		return 0;
	}
	
	@Override
	public InetAddress getInetAddress() {
		return null;
	}

	@Override
	public InetAddress getLocalAddress() {
		return null;
	}

	@Override
	public int getPort() {
		return 0;
	}

	@Override
	public int getLocalPort() {
		return 0;
	}

}
