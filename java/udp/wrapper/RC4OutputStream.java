package udp.wrapper;

import java.io.IOException;
import java.io.OutputStream;

public abstract class RC4OutputStream 
	extends OutputStream implements Wrapper<OutputStream>{

	@Override
	public void write(int b) throws IOException {
		getSource().write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		getSource().write(b);
	}
	
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		getSource().write(b, off, len);
	}
}
