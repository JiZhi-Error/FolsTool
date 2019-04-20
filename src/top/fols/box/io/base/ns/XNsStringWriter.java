package top.fols.box.io.base.ns;
import java.io.Writer;
import top.fols.box.io.base.ns.XNsStringWriter;


public class XNsStringWriter extends Writer {
    private StringBuilder buf;
    public XNsStringWriter() {
        buf = new StringBuilder();
        lock = buf;
    }
    public XNsStringWriter(int initialSize) {
        if (initialSize < 0) {
            throw new IllegalArgumentException("Negative buffer size");
        }
        buf = new StringBuilder(initialSize);
        lock = buf;
    }
	@Override
    public void write(int c) {
        buf.append((char) c);
    }
	@Override
    public void write(char cbuf[], int off, int len) {
        if ((off < 0) || (off > cbuf.length) || (len < 0) ||
            ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        buf.append(cbuf, off, len);
    }
	@Override
    public void write(String str) {
        buf.append(str);
    }
	@Override
    public void write(String str, int off, int len) {
        buf.append(str, off, off + len);
    }
	@Override
    public XNsStringWriter append(CharSequence csq) {
        write(String.valueOf(csq));
        return this;
    }
	@Override
    public XNsStringWriter append(CharSequence csq, int start, int end) {
	  	if (csq == null) csq = "null";
        return append(csq.subSequence(start, end));
    }
	@Override
    public XNsStringWriter append(char c) {
        write(c);
        return this;
    }
	@Override
    public String toString() {
        return buf.toString();
    }
    public StringBuilder getBuffer() {
        return buf;
    }
	@Override
    public void flush() {
		return;
    }
	@Override
    public void close() {
		return;
    }


	public void setBuff(StringBuilder buf) {
		if (buf == null)
			throw new NullPointerException();
		this.buf = buf;
	}

}

