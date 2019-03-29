package top.fols.box.io.base.ns;

import top.fols.box.io.interfaces.ReleasableCache;
import top.fols.box.io.interfaces.XInterfacePrivateBuffOperat;
import top.fols.box.io.interfaces.XInterfacePrivateCharArrayBuffSearchOperat;
import top.fols.box.io.interfaces.XInterfacePrivateFixedStreamIndexOperat;
import top.fols.box.io.interfaces.XInterfacePrivateFixedStreamSizeOperat;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.XArraysUtils;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

/**
 * Writer的一个子类、可将字符写入到自带的一个缓存字符数组buf中、
 * 当buf写满时、会自动扩容。
 */
public class XNsCharArrayWriterUtils extends Writer implements  XInterfacePrivateBuffOperat<char[]>,XInterfacePrivateCharArrayBuffSearchOperat,XInterfacePrivateFixedStreamSizeOperat,XInterfacePrivateFixedStreamIndexOperat,ReleasableCache {

	@Override
	public int getSize() {
		// TODO: Implement this method
		return count;
	}


	@Override
	public int getBuffSize() {
		// TODO: Implement this method
		return buf.length;
	}



    /**
     * 用于存放写入CharArrayWriter的字符、存满自动扩容。
     */
    private char buf[];

    /**
     * buf中现有的字符数
     */
    private int count;

    /**
     * 使用默认buf大小创建CharArrayWriter。
     */
    public XNsCharArrayWriterUtils() {
    	this(32);
    }

    /**
     * 使用指定的buf大小创建CharArrayWriter。
     */
    public XNsCharArrayWriterUtils(int initialSize) {
        if (initialSize < 0) {
            throw new IllegalArgumentException("Negative initial size: "
											   + initialSize);
        }
        buf = new char[initialSize];
    }

    /**
     * 写入一个字符。
     */
    public void write(int c) {
		int newcount = count + 1;
		//如果buf存满、则将buf容量扩大1倍、并将原来buf中count字符copy到新的buf中
		if (newcount > buf.length) {
			buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
		}
		//将新写入的字符存入到buf第count个下标位置。
		buf[count] = (char)c;
		count = newcount;
	}


    /**
     * 将一个char[]的一部分写入buf中、若buf满、扩容。
     */
    public void write(char c[], int off, int len) {
		if ((off < 0) || (off > c.length) || (len < 0) ||
			((off + len) > c.length) || ((off + len) < 0)) {
		    throw new IndexOutOfBoundsException();
		} else if (len == 0) {
		    return;
		}
		int newcount = count + len;
		if (newcount > buf.length) {
			buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
		}
		System.arraycopy(c, off, buf, count, len);
		count = newcount;
    }

    /**
     * 将一个字符串写入buf中、满自动扩容
     */
    public void write(String str, int off, int len) {
		int newcount = count + len;
		if (newcount > buf.length) {
			buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
		}
		str.getChars(off, off + len, buf, count);
		count = newcount;
    }

    /**
     * 将buf中现有的字节写入到subWriter（out）中
     */
    public void writeTo(Writer out) throws IOException {
		out.write(buf, 0, count);
    }

    /**
     * 将一串有序字符序列写入buf中
     */
    public Writer append(CharSequence csq) {
		String s = (csq == null ? "null" : csq.toString());
		write(s, 0, s.length());
		return this;
    }

    /**
     * 将一串有序字符序列的一部分写入buf中
     */
    public Writer append(CharSequence csq, int start, int end) {
		String s = (csq == null ? "null" : csq).subSequence(start, end).toString();
		write(s, 0, s.length());
		return this;
    }

    /**
     * 将一个字符写入buf中
     */
    public Writer append(char c) {
		write(c);
		return this;
    }

    /**
     * 清空buf、重头开始
     */
    public void reset() {
    	count = 0;
    }

    /**
     * 将buf中内容转换成char[]
     */
    public char toCharArray()[] {
		if (count == 0)
			return XStaticFixedValue.nullcharArray;
		return Arrays.copyOf(buf, count);
    }

    /**
     * 查看当前buf中字符总数
     */
    public int size() {
    	return count;
    }

    /**
     * 将buf中字符转换成String返回
     */
    public String toString() {
		return new String(buf, 0, count);
	}

    /**
     * flush  CharArrayWriter、因此方法对CharArrayWriter没有效果、所以方法体是空！
     */
    public void flush() { }

    /**
     * 同样、关闭CharArrayWriter没有用、调用close()关闭此流、此流的方法一样能用。
     */
    public void close() { }




	public void releaseCache() {
		buf = XStaticFixedValue.nullcharArray;
		count = 0;
	}

	public void setSize(int size) {
		if (size > buf.length)
			size = buf.length;
		count = size;
	}
	public char[] getBuff() {
		return buf;
	}


	@Override
	public void seekIndex(int index) {
		setSize(index);
	}

	@Override
	public int getIndex() {
		return count;
	}



	public int indexOfBuff(char b, int start) {
		return XArraysUtils.indexOf(buf, b, start, buf.length);
	}
	public int indexOfBuff(char[] b, int start) {
		return XArraysUtils.indexOf(buf, b, start, buf.length);
	}
	public int indexOfBuff(char b, int start, int end) {
		return XArraysUtils.indexOf(buf, b, start, end);
	}
	public int indexOfBuff(char[] b, int start, int end) {
		return XArraysUtils.indexOf(buf, b, start, end);
	}


	public int lastIndexOfBuff(char b, int start) {
		return XArraysUtils.lastIndexOf(buf, b, 0, start);
	}
	public int lastIndexOfBuff(char[] b, int start) {
		return XArraysUtils.lastIndexOf(buf, b, 0, start);
	}
	public int lastIndexOfBuff(char b, int start, int end) {
		return XArraysUtils.lastIndexOf(buf, b, start, end);
	}
	public int lastIndexOfBuff(char[] b, int start, int end) {
		return XArraysUtils.lastIndexOf(buf, b, start, end);
	}


}

