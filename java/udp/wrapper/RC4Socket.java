package udp.wrapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public abstract class RC4Socket 
	extends Socket implements Wrapper<Socket> {
	
	RC4InputStream inStream;
	RC4OutputStream outStream;

	@Override
	public InputStream getInputStream() throws IOException {
		if (inStream == null) {
			inStream = WrapperFactory.createWrapper(getSource().getInputStream(), RC4InputStream.class);
		}
		return inStream;
	}
	
	@Override
	public OutputStream getOutputStream() throws IOException {
		if (outStream == null) {
			outStream = WrapperFactory.createWrapper(getSource().getOutputStream(), RC4OutputStream.class);
		}
		return outStream;
	}
	
}
