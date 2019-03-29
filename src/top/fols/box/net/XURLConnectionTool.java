package top.fols.box.net;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import top.fols.box.io.XStream;
import top.fols.box.io.base.ByteArrayOutputStreamUtils;
import top.fols.box.io.base.XInputStreamFixedLength;
import top.fols.box.io.base.ns.XNsCharArrayReaderUtils;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.XArraysUtils;
import top.fols.box.util.XMap;
import top.fols.box.util.XObjects;

public class XURLConnectionTool {
	public static class UA {
		/*
		 设置请求头

		 直接可以使用
		 Content-xxx:xxxxx
		 Cookie:xxxxx
		 */
		private static char[] trim(char[] b) {
			if (b == null)
				return b;
			try {
				int start = 0;
				int end = b.length;
				for (int i = 0;i < b.length;i++) {
					char bi = b[i];
					if (!(bi == '\r' || bi == '\n' || bi == ' ' || bi == '\t'))
						break;
					start++;
				}
				for (int i = b.length - 1 ;i >= 0;i--) {
					if (end <= start)
						break;
					char bi = b[i];
					if (!(bi == '\r' || bi == '\n'))
						break;
					end--;
				}
				if (start == 0 && end == b.length)
					return b;
				return Arrays.copyOfRange(b, start, end);
			} catch (Exception e) {
				return b;
			}
		}
		private static void deal(String ua, UA m) {
			XNsCharArrayReaderUtils rowStreanm = new XNsCharArrayReaderUtils(ua.toCharArray());
			char byteArray[];
			char splitchar = ':';
			while ((byteArray = rowStreanm.readLine()) != null) {
				int splistCharindex = XArraysUtils.indexOf(byteArray, splitchar, 0, byteArray.length);
				String trim1 = null,trim2 = null;
				if (splistCharindex != -1) {
					trim1 = new String(byteArray, 0, splistCharindex);
					splistCharindex++;
					trim2 = new String(trim(Arrays.copyOfRange(byteArray, splistCharindex, byteArray.length)));
					if (!trim1.equals(""))
						m.Key.put(trim1, trim2);
				}
				byteArray = null;
			}
		}
		private XMap<String> Key = new XMap<String>();
		public UA() {
			this(null);
		}
		public UA(String ua) {
			if (ua != null)
				putAll(ua);
		}
		public UA put(String k, String v) {
			if (k == null)
				throw new NullPointerException();
			this.Key.put(k, v);
			return this;
		}
		public UA putAll(String Content) {
			deal(Content, this);
			return this;
		}
		public UA putAll(String... Content) {
			StringBuilder buf = new StringBuilder();
			for (String s:Content)
				buf.append(s).append(XStaticFixedValue.String_NextLineN);
			putAll(buf.toString());
			buf.delete(0, buf.length());
			return this;
		}
		public String get(String k) {
			if (k == null)
				throw new NullPointerException();
			return Key.get(k);
		}
		public XMap<String> getAll() {
			return Key;
		}
		public List<String> keys() {
			return Key.keys();
		}

		public UA reset() {
			Key.clear();
			return this;
		}
		public UA remove(String key) {
			Key.remove(key);
			return this;
		}
		@Override
		public String toString() {
			// TODO: Implement this method
			StringBuffer buf = new StringBuffer();
			for (String k:keys()) {
				buf.append(k).append(':').append(' ').append(get(k)).append("\r\n");
			}
			return buf.toString();
		}




		public void setToURLConnection(URLConnection con) {
			Iterator<String> it = Key.keySet().iterator();
			while (it.hasNext()) {
				String k = it.next();
				con.setRequestProperty(k, Key.get(k));
			}
		}
		public void addToURLConnection(URLConnection con) {
			Iterator<String> it = Key.keySet().iterator();
			while (it.hasNext()) {
				String k = it.next();
				con.addRequestProperty(k, Key.get(k));
			}
		}
	}





	public static class get {
		private URLConnection con;
		private UA ua;

		public get(String url) throws IOException {
			this(new URL(url).openConnection());
		}
		public get(URLConnection con) {
			this.ua = new UA();
			this.con = XObjects.requireNonNull(con);
		}
		public URLConnection getURLConnection() {
			return con;
		}
		public get connectTimeout(int time) {
			con.setConnectTimeout(time);
			return this;
		}
		public get readTimeout(int time) {
			con.setReadTimeout(time);
			return this;
		}
		public get ua(String Content) {
			return ua(new UA(Content));
		}
		public get ua(UA a) {
			if (a == null)
				return this;
			this.ua = a;
			return this;
		}
		public UA getUA() {
			return ua;
		}

		private InputStream in;
		private OutputStream ot;
		public InputStream getInputStream() throws IOException {
			return in == null ? in = con.getInputStream(): in;

		}
		public OutputStream getOutputStream() throws IOException {
			return ot == null ? ot = con.getOutputStream(): ot;
		}


		public void disconnect() {
			try {
				if (con instanceof HttpURLConnection)
					((HttpURLConnection)con).disconnect();
				getInputStream().close();
				getOutputStream().close();
				in = null;
				ot = null;
			} catch (Exception e) {
				e = null;
			}
		}

		private boolean writeORread = false;
		public get read2(OutputStream BackOutput) throws IOException {
			if (BackOutput == null)
				return this;
			if (!writeORread) {
				ua.setToURLConnection(con);
				writeORread = true;
			}
			XStream.copy(getInputStream(), BackOutput);
			return this;
		}


		public String toString() {
			return toString(null);
		}
		public String toString(String encoding) {
			ByteArrayOutputStreamUtils BackOutput = new ByteArrayOutputStreamUtils();
			try {
				read2(BackOutput);
				if (encoding == null)
					return BackOutput.toString();
				return BackOutput.toString(encoding);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static class post {
		private URLConnection con;
		private UA ua;

		public post(String url) throws IOException {
			this(new URL(url).openConnection());
		}
		public post(URLConnection con) {
			this.ua = new UA();
			this.con = XObjects.requireNonNull(con);
			this.con.setDoOutput(true);
			this.con.setDoInput(true);
			try {
				if (con instanceof HttpURLConnection)
					((HttpURLConnection)con).setRequestMethod("POST");
			} catch (ProtocolException e) {
				throw new RuntimeException(e);
			}
		}
		public URLConnection getURLConnection() {
			return con;
		}
		public post connectTimeout(int time) {
			con.setConnectTimeout(time);
			return this;
		}
		public post readTimeout(int time) {
			con.setReadTimeout(time);
			return this;
		}
		public post ua(String Content) {
			return ua(new UA(Content));
		}
		public post ua(UA a) {
			if (a == null)
				return this;
			this.ua = a;
			return this;
		}
		public UA getUA() {
			return ua;
		}

		private InputStream in;
		private OutputStream ot;
		public InputStream getInputStream() throws IOException {
			return in == null ? in = con.getInputStream(): in;

		}
		public OutputStream getOutputStream() throws IOException {
			return ot == null ? ot = con.getOutputStream(): ot;
		}



		private boolean writeORread = false;
		public post write(byte[] b) throws IOException {
			return write(b, 0, b.length);
		}

		public post write(byte[] b, int off, int len) throws IOException {
			if (!writeORread) {
				ua.setToURLConnection(con);
				writeORread = true;
			}
			OutputStream stream = getOutputStream();
			stream.write(b, off, len);
			return this;
		}
		public post write(InputStream BackOutput) throws IOException {
			if (BackOutput == null)
				return this;
			if (!writeORread) {
				ua.setToURLConnection(con);
				writeORread = true;
			}
			XStream.copy(BackOutput, getOutputStream());
			return this;
		}
		public post write(InputStream BackOutput, long length) throws IOException {
			if (BackOutput == null)
				return this;
			if (!writeORread) {
				ua.setToURLConnection(con);
				writeORread = true;
			}
			XStream.copy(new XInputStreamFixedLength(BackOutput, length), getOutputStream());
			return this;
		}
		public void disconnect() {
			try {
				if (con instanceof HttpURLConnection)
					((HttpURLConnection)con).disconnect();
				getInputStream().close();
				getOutputStream().close();
				in = null;
				ot = null;
			} catch (Exception e) {
				e = null;
			}
		}
		public post read2(OutputStream BackOutput) throws IOException {
			if (BackOutput == null)
				return this;
			if (!writeORread) {
				ua.setToURLConnection(con);
				writeORread = true;
			}
			XStream.copy(getInputStream(), BackOutput);
			return this;
		}




		public String toString() {
			return toString(null);
		}
		public String toString(String encoding) {
			ByteArrayOutputStreamUtils BackOutput = new ByteArrayOutputStreamUtils();
			try {
				read2(BackOutput);
				if (encoding == null)
					return BackOutput.toString();
				return BackOutput.toString(encoding);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}






	public static String get(String url) throws IOException {
		return get(url, null, defaultConnectTimeout, defaultReadTimeout,  null);
	}
	public static String get(String url, String encode) throws IOException {
		return get(url, encode, defaultConnectTimeout, defaultReadTimeout,  null);
	}
	public static String get(String url, String encode,  int ConnectTimeout, int ReadTimeout, String data) throws IOException {
		get get = new get(url);
		get
			.connectTimeout(ConnectTimeout)
			.readTimeout(ReadTimeout)
			.ua(data)
			;
		String Content = get.toString(encode);
		get.disconnect();
		return Content;
	}
	public static String get(URLConnection con , String encode,  int ConnectTimeout, int ReadTimeout, String data) throws IOException {
		ByteArrayOutputStreamUtils byteoutput = new ByteArrayOutputStreamUtils();
		get(con, ConnectTimeout, ReadTimeout, new UA(data), byteoutput, false);
		if (encode != null)
			return new String(byteoutput.toByteArray(), encode);
		return new String(byteoutput.toByteArray());
	}
	public static void get(URLConnection con , int ConnectTimeout, int ReadTimeout, UA data, OutputStream BackOutput, boolean close) throws IOException {
		get get = new get(con)
			.connectTimeout(ConnectTimeout)
			.readTimeout(ReadTimeout)
			.ua(data)
			.read2(BackOutput);
		if (close)
			get.disconnect();
	}



	public static String post(String url, InputStream postdata) throws IOException {
		return post(url, null, defaultConnectTimeout, defaultReadTimeout, null, postdata);
	}
	public static String post(String url, String encode, InputStream postdata) throws IOException {
		return post(url, encode, defaultConnectTimeout, defaultReadTimeout,  null, postdata);
	}
	public static String post(String url, String encode,  int ConnectTimeout, int ReadTimeout, String ua, InputStream postdata) throws IOException {

		post get = new post(url);
		get
			.connectTimeout(ConnectTimeout)
			.readTimeout(ReadTimeout)
			.ua(ua)
			.write(postdata);
		String Content = get.toString(encode);
		get.disconnect();
		return Content;
	}
	public static String post(URLConnection con, String encode, int ConnectTimeout, int ReadTimeout, String ua, InputStream postdata) throws IOException {
		ByteArrayOutputStreamUtils byteoutput = new ByteArrayOutputStreamUtils();
		post(con, ConnectTimeout, ReadTimeout, new UA(ua), postdata, byteoutput, false);
		if (encode != null)
			return new String(byteoutput.toByteArray(), encode);
		return new String(byteoutput.toByteArray());
	}
	public static void post(URLConnection con, int ConnectTimeout, int ReadTimeout, UA uadata, InputStream postdata, OutputStream BackOutput, boolean close) throws IOException {
		post get = new post(con)
			.connectTimeout(ConnectTimeout)
			.readTimeout(ReadTimeout)
			.ua(uadata)
			.write(postdata)
			.read2(BackOutput);

		if (close)
			get.disconnect();
	}





	public static final int defaultConnectTimeout = 12000;
	public static final int defaultReadTimeout = 6000;
}
