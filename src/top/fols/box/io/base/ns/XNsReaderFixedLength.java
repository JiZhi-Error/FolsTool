package top.fols.box.io.base.ns;

import java.io.IOException;
import java.io.Reader;
import top.fols.box.io.interfaces.XInterfaceStreamFixedLength;

public class XNsReaderFixedLength extends Reader implements XInterfaceStreamFixedLength<Reader> {
	private Reader stream;
	protected long maxCount;
	protected long nowCount;
	protected boolean fixed;
	public XNsReaderFixedLength(Reader stream, long maxCount) {
		if (stream == null)
			throw new NullPointerException("stream for null");
		if (maxCount < 0)
			maxCount = 0;
		this.stream = stream;
		this.maxCount = maxCount;
		this.nowCount = 0;
		this.fixed = true;
	}



	@Override
	public int read() throws IOException {
		if (fixed && nowCount + 1 > maxCount)
			return -1;
		int read = stream.read();
		if (read != -1)
			nowCount++;
		return read;
	}
    public int read(char[] b, int off, int len) throws java.io.IOException {
		if (len < 0)
			return -1;
		else if (fixed) {
			if (nowCount + len > maxCount) {
				len = (int)(maxCount - nowCount);
				if (len < 1)
					return -1;
			}
			if (nowCount + len > maxCount)
				return - 1;
		}
		int read = stream.read(b, off, len);
		if (read != -1)
			nowCount += read;
		return read;
	}



	public boolean ready() throws java.io.IOException {
		return stream.ready();
	}
    public long skip(long n) throws java.io.IOException {
		return stream.skip(n);
	}
    public void reset() throws java.io.IOException {
		stream.reset();
	}
    public boolean markSupported() {
		return stream.markSupported();
	}
	public void close() throws java.io.IOException {
		stream.close();
	}
    public void mark(int readlimit) throws IOException {
		stream.mark(readlimit);
	}



	public long getFixedLengthFree() {
		return maxCount - nowCount;
	}
	public boolean isFixedLengthAvailable() {
		return !fixed || fixed && nowCount < maxCount;
	}

	public long getFixedLengthUseSize() {
		return nowCount;
	}
	public void resetFixedLengthUseSize() {
		nowCount = 0;
	}

	public long getFixedLengthMaxSize() {
		return maxCount;
	}
	public void setFixedLengthMaxSize(long maxCount) {
		this.maxCount = maxCount;
	}

	public void setFixedLength(boolean b) {
		this.fixed = b;
	}
	public boolean getFixedLength() {
		return fixed;
	}




	public Reader getStream() {
		return stream;
	}

}

