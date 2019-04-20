package top.fols.box.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XUnicodeSimple {
	//"([^\\x00-\\xff])"双字节字符
	public static String encode(String str) {
		String tmp;
		StringBuilder sb = new StringBuilder(1000);
		char c;
		int i, j;
		sb.setLength(0);
		try {
			for (i = 0; i < str.length(); i++) {
				c = str.charAt(i);
				if (c > 255) {
					sb.append("\\u");
					j = (c >>> 8);
					tmp = Integer.toHexString(j);
					if (tmp.length() == 1)
						sb.append("0");
					sb.append(tmp);
					j = (c & 0xFF);
					tmp = Integer.toHexString(j);
					if (tmp.length() == 1)
						sb.append("0");
					sb.append(tmp);
				} else {
					sb.append(c);
				}

			}
			return (new String(sb));
		} catch (Exception e2) {
			throw new RuntimeException(e2);
		}
	}
	public static String decode(String str) {
		int i = 0;
		try {
			Charset forName = Charset.forName("UTF-16");
			Matcher matcher = Pattern.compile("\\\\u([0-9a-fA-F]{4})").matcher(str);
			StringBuilder stringBuffer = new StringBuilder();
			while (matcher.find(i)) {
				int start = matcher.start();
				if (start > i) {
					stringBuffer.append(str.substring(i, start));
				}
				i = Integer.valueOf(matcher.group(1), 16).intValue();
				byte[] bArr = new byte[4];
				bArr[0] = (byte) ((i >> 8) & 255);
				bArr[1] = (byte) (i & 255);
				stringBuffer.append(String.valueOf(forName.decode(ByteBuffer.wrap(bArr))).trim());
				i = matcher.end();
			}
			int length = str.length();
			if (length > i) {
				stringBuffer.append(str.substring(i, length));
			}
			return stringBuffer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
