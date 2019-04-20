package top.fols.box.io;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import top.fols.box.io.base.ns.XNsByteArrayInputStream;
import top.fols.box.io.base.ns.XNsByteArrayOutputStream;
import top.fols.box.io.base.ns.XNsCharArrayReader;
import top.fols.box.io.base.ns.XNsInputStreamFixedLength;
import top.fols.box.io.base.ns.XNsReaderFixedLength;
import top.fols.box.io.base.ns.XNsStringReader;
import top.fols.box.io.base.ns.XNsCharArrayWriter;

public class XStream {
	public static final int default_streamByteArrBuffSize = 8192;
	public static final int default_streamCharArrBuffSize = 8192;

	public static int copy(byte[] input, OutputStream output) throws IOException {
		return copy(input, output, true);
	}
	public static int copy(byte[] in, OutputStream out, boolean autoflush) throws IOException {
		if (in == null)
			return 0;
		out.write(in);
		if (autoflush)
			out.flush();
		return in.length;
	}
	public static long copy(InputStream input, OutputStream output) throws IOException {
		return copy(input, output, default_streamByteArrBuffSize);
	}
	public static long copy(InputStream input, OutputStream output, int bufflen) throws IOException {
		return copy(input, output, bufflen, true);
	}
	public static long copy(InputStream in, OutputStream out, int bufflen, boolean autoflush) throws IOException {
		if (in == null)
			return 0;
		byte[] buff = new byte[bufflen <= 0 ?default_streamByteArrBuffSize: bufflen];
		int read;
		long length = 0;
		while ((read = in.read(buff)) != -1) {
			length += read;
			if (out == null)
				continue;
			out.write(buff, 0, read);
			if (autoflush)
				out.flush();
		}
		return length;
	}
	public static long copyFixedLength(InputStream input, OutputStream output, long copyLength) throws IOException {
		return copyFixedLength(input, output, default_streamByteArrBuffSize, copyLength);
	}
	public static long copyFixedLength(InputStream input, OutputStream output, int bufflen, long copyLength) throws IOException {
		return copyFixedLength(input, output, bufflen, copyLength, true);
	}
	public static long copyFixedLength(InputStream input, OutputStream output, int bufflen, long copyLength, boolean autoflush) throws IOException {
		return copy(new XNsInputStreamFixedLength(input, copyLength), output, bufflen, autoflush);
	}




	public static int copy(char[] input, Writer output) throws IOException {
		return copy(input, output, true);
	}
	public static int copy(char[] in, Writer out, boolean autoflush) throws IOException {
		if (in == null)
			return 0;
		out.write(in);
		if (autoflush)
			out.flush();
		return in.length;
	}
	public static long copy(Reader reader, Writer writer) throws IOException {
		return copy(reader, writer, default_streamCharArrBuffSize);
	}
	public static long copy(Reader reader, Writer writer, int bufflen) throws IOException {
		return copy(reader, writer, bufflen, true);
	}
	public static long copy(Reader in, Writer out, int bufflen, boolean autoflush) throws IOException {
		if (in == null)
			return 0;
		char[] buff = new char[bufflen <= 0 ?default_streamByteArrBuffSize: bufflen];
		int read;
		long length = 0;
		while ((read = in.read(buff)) != -1) {
			length += read;
			if (out == null)
				continue;
			out.write(buff, 0, read);
			if (autoflush)
				out.flush();
		}
		return length;
	}

	public static long copyFixedLength(Reader input, Writer output, long copyLength) throws IOException {
		return copyFixedLength(input, output, default_streamCharArrBuffSize, copyLength);
	}
	public static long copyFixedLength(Reader input, Writer output, int bufflen, long copyLength) throws IOException {
		return copyFixedLength(input, output, bufflen, copyLength, true);
	}
	public static long copyFixedLength(Reader input, Writer output, int bufflen, long copyLength, boolean autoflush) throws IOException {
		return copy(new XNsReaderFixedLength(input, copyLength), output, bufflen, autoflush);
	}







	public static void writeObject(OutputStream out, Object obj) throws IOException {
		new ObjectOutputStream(out).writeObject(obj);
	}
	public static Object readObject(InputStream in) throws IOException, ClassNotFoundException {
		return new ObjectInputStream(in).readObject();
	}
	public static class inputstream {
		public static String toString(InputStream input, String encoding) throws IOException {
			return new String(toByteArray(input), encoding);
		}
		public static String toString(InputStream input) throws IOException {
			return new String(toByteArray(input));
		}
		public static byte[] toByteArray(InputStream input) throws IOException {
			if (input != null) {
				XNsByteArrayOutputStream byteArrayout =  new XNsByteArrayOutputStream();
				copy(input, byteArrayout);
				byte[] bs = byteArrayout.toByteArray();
				byteArrayout.releaseBuffer();
				return bs;
			}
			return null;
		}
	}
	public static class reader {
		public static String toString(Reader input) throws IOException {
			return new String(toCharArray(input));
		}
		public static char[] toCharArray(Reader input) throws IOException {
			if (input != null) {
				XNsCharArrayWriter Arrayout =  new XNsCharArrayWriter();
				copy(input, Arrayout);
				char[] cs = Arrayout.toCharArray();
				Arrayout.releaseBuffer();
				return cs;
			}
			return null;
		}
	}
	public static class object {
		public static byte[] toByteArray(Object obj) throws IOException {
			XNsByteArrayOutputStream out = new XNsByteArrayOutputStream();
			writeObject(out, obj);
			byte[] bs = out.toByteArray();
			out.releaseBuffer();
			return bs;
		}
		public static Object toObject(byte[] bytes) throws ClassNotFoundException, IOException {
			XNsByteArrayInputStream in = new XNsByteArrayInputStream(bytes);
			return readObject(in);
		}
	}




	public static XNsByteArrayInputStream wrapInputStream(byte[] bytes) {
		return new XNsByteArrayInputStream(bytes);
	}
	public static XNsCharArrayReader wrapReader(char[] bytes) {
		return new XNsCharArrayReader(bytes);
	}
	public static XNsStringReader wrapReader(String bytes) {
		return new XNsStringReader(bytes);
	}
}
