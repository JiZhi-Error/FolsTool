package top.fols.box.util.md5;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import top.fols.box.util.XMessageDigest;

public class XMD5Algorithm extends OutputStream {
	public void close() {
		// TODO: Implement this method
		this.clear();
	}
	private XMD5 xmd5;
	private MessageDigest mds;

	public XMD5Algorithm() {
		init();
	}
	private XMD5Algorithm init() {
		try {
			this.mds = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			this.xmd5 = new XMD5().Init();
		}
		return this;
	}
	public void clear() {
		if (this.mds != null) {
			this.mds.reset();
		} else {
			if (this.xmd5 != null)
				this.xmd5.Init();
		}
		this.init();
	}
	public void write(byte[] b, int off, int len) {
		if (this.mds != null) {
			this.mds.update(b, off, len);
		} else {
			this.xmd5.Update(b, off, len);
		}
	}
	public void write(int b) {
		if (this.mds != null) {
			this.mds.update((byte)b);
		} else {
			this.xmd5.Update(b);
		}
	}
	public void write(byte[] b) {
		write(b, 0, b.length);
	}
	@Override
	public void flush() {
		// TODO: Implement this method
		return;
	}
	public String getHash(){
		if (this.mds != null) {
			return XMessageDigest.bufferToHex(this.mds);
		} else {
			return this.xmd5.asHex();
		}
	}
	@Override
	public String toString() {
		// TODO: Implement this method
		return getHash().toString();
	}
	
	
}
