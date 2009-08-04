package udp.wrapper;

import java.io.IOException;
import java.io.InputStream;

import udp.crypto.RC4;

public abstract class RC4InputStream 
	extends InputStream implements Wrapper<InputStream> {

	private boolean isEncrypted = false;
	private int preRead[] = new int[3];
    private int pos = -1;
    private RC4 rc4;

    public boolean isEncrypted() {
		return isEncrypted;
	}

	@Override
	public int read() throws IOException {
		initPos();
		System.out.println(pos);		
    	if (pos < 2) {    		    		
    		pos++;
    		return preRead[pos];
    	}

    	if (this.isEncrypted)
    		return rc4.rc4((byte)getSource().read());
    	else
    		return getSource().read();
	}
    
	@Override
	public int read(byte[] b) throws IOException {
		initPos();
		if (pos < 2) {
			int left = 2-pos;
			if (b.length <= left) {
				for(int i=0; i<b.length; i++) {
					pos++;
					b[i] = (byte)preRead[pos];
				}
				return b.length;
			} else {
				for(int i=0; i<left; i++) {
					pos++;
					b[i] = (byte)preRead[pos];
				}
				int result = getSource().read(b, left, b.length-left) + left;
				if (this.isEncrypted && result > 0)
					rc4.irc4(b, left, result-left);
				return result;
			}
		}
		
		int result = getSource().read(b);
		if (this.isEncrypted && result > 0)
			rc4.irc4(b, 0, result);
		return result;
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		initPos();
		if (pos < 2) {
			int left = 2-pos;
			if (len <= left) {
				for(int i=0; i<len; i++) {
					pos++;
					b[off+i] = (byte)preRead[pos];
				}
				return len;
			} else {
				for(int i=0; i<left; i++) {
					pos++;
					b[off+i] = (byte)preRead[pos];
				}
				int result = getSource().read(b, off+left, len-left) + left;
				if (this.isEncrypted && result > 0)
					rc4.irc4(b, off+left, result-left);
				return result;
			}
		}

		int result = getSource().read(b, off, len);
		if (this.isEncrypted && result > 0)
			rc4.irc4(b, off, result);
		return result;
	}
	
    private boolean isEncryptedHttpHeader(int[] buf) {
    	if ( (buf[0]=='G' && buf[1]=='E' && buf[2]=='T') ||
    		 (buf[0]=='P' && buf[1]=='U' && buf[2]=='T') ||
    		 (buf[0]=='H' && buf[1]=='E' && buf[2]=='A') ||
    		 (buf[0]=='P' && buf[1]=='O' && buf[2]=='S') )
    		return false;
    	return true;
    }

	private void initPos() throws IOException {
		if (pos < 0)
		{
			// 预读3个字节，决定是否需要解码 GET/POS/HEA/PUT
			preRead[0] = getSource().read();
			preRead[1] = getSource().read();
			preRead[2] = getSource().read();
			this.isEncrypted = isEncryptedHttpHeader(preRead);
			if (this.isEncrypted) {
				rc4 = new RC4();
				preRead[0] = rc4.rc4((byte)preRead[0]);
				preRead[1] = rc4.rc4((byte)preRead[1]);
				preRead[2] = rc4.rc4((byte)preRead[2]);
			}
		}
	}
}
