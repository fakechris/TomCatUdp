package udp.wrapper;

import java.io.IOException;
import java.io.OutputStream;

import udp.crypto.RC4;

public abstract class RC4OutputStream 
	extends OutputStream implements Wrapper<OutputStream>{

	private RC4 rc4;
	private boolean isEncrypted = false;
		
	public void setEncrypted(boolean isEncrypted) {
		this.isEncrypted = isEncrypted;
		if (this.rc4 == null) {
			this.rc4 = new RC4();
		}
	}

	@Override
	public void write(int b) throws IOException {
		if (this.isEncrypted) 
			getSource().write(rc4.rc4((byte)b));
		else
			getSource().write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		if (this.isEncrypted)
			rc4.irc4(b);
		getSource().write(b);
	}
	
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		if (this.isEncrypted)
			rc4.irc4(b, off, len);
		getSource().write(b, off, len);
	}
}
