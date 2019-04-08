package top.fols.box.io.base;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import top.fols.box.annotation.XAnnotations;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.XObjects;

public class XInputStream<T extends InputStream> extends InputStream {
	public static XInputStream wrap(InputStream in) {
		return new XInputStream(in);
	}

	private int readBreak = XStaticFixedValue.Stream_ReadBreak;
	public int read() throws java.io.IOException {
		irc = false;
		int read = stream.read();
		if (read == readBreak)
			irc = true;
		return read;
	}
    public int read(byte[] b) throws java.io.IOException {
		return read(b, 0, b.length);
	}
    public int read(byte[] b, int off, int len) throws java.io.IOException {
		irc = false;
		int read = stream.read(b, off, len);
		if (read == readBreak)
			irc = true;
		return read;
	}
    public long skip(long n) throws java.io.IOException {
		return stream.skip(n);
	}
    public int available() throws java.io.IOException {
		return stream.available();
	}
    public void close() throws java.io.IOException {
		stream.close();
	}
    public synchronized void mark(int readlimit) {
		stream.mark(readlimit);
	}
    public synchronized void reset() throws java.io.IOException {
		stream.reset();
	}
    public boolean markSupported() {
		return stream.markSupported();
	}





	public byte[] readLine() throws IOException {
		return readLine(Byte_NextLineN);
	}
	private boolean irc = false;
	private boolean irs = false;
	@XAnnotations("this no buffered")
	public byte[] readLine(byte separator) throws IOException {
		int bufsize = 64 * 1024;
		byte[] buf = new byte[bufsize];
		int size = 0;
		int rb;

		irc = false;
		irs = false;
		do {
			if ((rb = stream.read()) == readBreak) {
				irc = true;
				if (size == 0)
					return null;
				break;
			}
			buf[size++] = (byte) rb;
			if (size >= buf.length) {
				byte[] originbuf = buf;
				byte[] newbuf = new byte[size + bufsize];
				System.arraycopy(buf, 0, newbuf, 0, buf.length);
				buf = newbuf;
				originbuf = null;
			}
		}
		while (rb != separator);// 读取一行10代表\n
		byte[] bytearr = Arrays.copyOfRange(buf, 0, size);
		if (size != 0)
			irs = bytearr[bytearr.length - 1] == separator;
		buf = null;
		return bytearr.length == 0 ? null: bytearr;
	}
	@XAnnotations("last read stream result equals -1")
	public synchronized boolean isReadComplete() {
		return irc;
	}
	public synchronized boolean readLineIsReadToSeparator() {
		return irs;
	}
	public T getStream() {
		return stream;
	}



	public static byte Byte_NextLineN = XStaticFixedValue.Byte_NextLineN;
	private final T stream;

	public XInputStream(T inputstream) {
		this.stream = XObjects.requireNonNull(inputstream);
	}
}
