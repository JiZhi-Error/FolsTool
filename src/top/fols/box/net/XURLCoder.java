package top.fols.box.net;

import java.net.URLDecoder;
import java.net.URLEncoder;
import top.fols.box.annotation.XAnnotations;
import top.fols.box.util.XObjects;

public class XURLCoder {
	/*
	 search %XX index
	 */
	public static class indexOfURLCode {
		private String text;
		private int start = -1;
		private int end = -1;
		private int length;

		public indexOfURLCode(String text) {
			this.text = text;
			this.length = text.length();
		}
		public boolean find() {
			int newstart = this.end < 0 ?0: this.end;
			int len = 0;
			for (int i = newstart;i < length; i++) {
				if ((len = getURLCodeLength(i)) != 0) {
					newstart = i;
					break;
				}
			}
			if (len == 0) {
				this.end = -1;
				return false;
			}
			this.start = newstart;
			while (true) {
				int length;
				if ((length = getURLCodeLength(newstart)) == 0)
					break;
				newstart += length;
			}
			this.end = newstart;
			//System.out.println("start=" + this.start + ", end=" + end);
			return true;
		}
		/*0-9
		 48>=x<=57*/
		/*a-f
		 97>=x<=102*/
		/*a-f
		 65>=x<=70*/
		@XAnnotations("%XX or +")
		private int getURLCodeLength(int start) {
			if (start < length && text.charAt(start) == '+') {
				//System.out.println(start + "+");
				return 1;
			}	
			if (start + 2 < length && text.charAt(start) == '%' && isURLCode(text.charAt(start + 1)) && isURLCode(text.charAt(start + 2))) {
				//System.out.println(start + "%");
				return 3;
			}
			return 0;
		}
		private boolean isURLCode(char c) {
			return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
		}




		public int start() {
			return start;
		}
		public int end() {
			return end;
		}
		public String group() {
			return text.substring(start, end);
		}
	}
	/*
	 [\u4E00-\u9FA5] 中文
	 %([0-9a-fA-F]{2})匹配%xx
	 适配一段范围的。(%[0-9a-fA-F]{2})+
	 */
	//格式化URL 将% 替换为%25
	public static String decodeFormat(String text) {
		return text.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
	}


	//URL解码
	public static String decodeS(String text) {
		return decodeS0(text, null);
	}
	public static String decodeS(String text, String charset) {
		if (XObjects.isEmpty(charset))
			throw new RuntimeException("encoding is empty");
		return decodeS0(text, charset);
	}
	private static String decodeS0(String text, String charset) {
		try {
			if (XObjects.isEmpty(text))
				return "";
			//"(%[0-9a-fA-F]{2})+"
			indexOfURLCode indexOf = new indexOfURLCode(text);
			StringBuilder buf = new StringBuilder();
			int start = 0,end = -1;
			if (!indexOf.find())
				return text;
			while (true) {
				end = indexOf.start();
				buf.append(text.substring(start, end)).append(charset != null ?URLDecoder.decode(indexOf.group(), charset): URLDecoder.decode(indexOf.group()));
				start = indexOf.end();

				if (!indexOf.find())
					break;
			}
			buf.append(text.substring(start));
			String result = buf.toString();
			buf = null;
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static boolean isURLEncode(String str) {
		return new indexOfURLCode(str).find();
	}





	public static String whileDecodeS(String text) {
		return whileDecodeS(text, null);
	}
	public static String whileDecodeS(String text, String charset) {
		if (XObjects.isEmpty(charset))
			throw new RuntimeException("encoding is empty");
		return whileDecodeS0(text, charset);
	}
	private static String whileDecodeS0(String text, String charset) {
		while (isURLEncode(text))
			text = decodeS(text, charset);
		return text;
	}





	
	//解码 office
	public static String decode(String str) {
		return URLDecoder.decode(decodeFormat(str));
	}
	public static String decode(String str, String charset) {
		if (XObjects.isEmpty(charset))
			throw new RuntimeException("encoding is empty");
		try {
			return URLDecoder.decode(decodeFormat(str), charset);
		} catch (Exception e) {
			return str;
		}
	}
	//编码 office
	public static String encode(String str) {
		return URLEncoder.encode(str);
	}
	public static String encode(String str, String charset) {
		if (XObjects.isEmpty(charset))
			throw new RuntimeException("encoding is empty");
		try {
			return URLEncoder.encode(str, charset);
		} catch (Exception e) {
			return str;
		}
	}
}
