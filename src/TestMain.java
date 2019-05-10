import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import top.fols.box.io.XStream;
import top.fols.box.io.base.XReader;
import top.fols.box.io.base.ns.XNsByteArrayInputStream;
import top.fols.box.io.base.ns.XNsByteArrayOutputStream;
import top.fols.box.io.base.ns.XNsInputStreamFixedLength;
import top.fols.box.io.os.XFile;
import top.fols.box.io.os.XFileEdit;
import top.fols.box.io.os.XFileTool;
import top.fols.box.io.os.XRandomAccessFileInputStream;
import top.fols.box.io.os.XRandomAccessFileOutputStream;
import top.fols.box.lang.XSequences;
import top.fols.box.lang.XString;
import top.fols.box.lang.impl.sequences.XLongSequenceImpl;
import top.fols.box.lang.impl.sequences.XObjectSequenceImpl;
import top.fols.box.lang.reflect.optdeclared.XReflectAccessible;
import top.fols.box.lang.reflect.optdeclared.XReflectMatcher;
import top.fols.box.net.XURL;
import top.fols.box.net.XURLParam;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.time.XTiming;
import top.fols.box.util.XArray;
import top.fols.box.util.XArrays;
import top.fols.box.util.XCycleSpeedLimiter;
import top.fols.box.util.XUnicodeSimple;

/*
 * 直接使用别的库的类 类名最后会有Utils
 */
abstract class k {
	public abstract void v();
	protected void dddd() {
		System.out.println();
	}
}

public class TestMain extends k {
	public static class XBase64Simple {
		private static char[] base64EncodeChars = new char[] {   
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',   
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',   
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',   
			'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',   
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',   
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v',   
			'w', 'x', 'y', 'z', '0', '1', '2', '3',   
			'4', '5', '6', '7', '8', '9', '+', '/' };   
		private static byte[] base64DecodeChars = new byte[] {   
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,   
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,   
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,   
			52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,   
			-1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,   
			15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,   
			-1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,   
			41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };
		public static String encode(byte[] data) {   
			StringBuilder sb = new StringBuilder();   
			int len = data.length;   
			int i = 0;   
			int b1, b2, b3;   
			try {
				while (i < len) {   
					b1 = data[i++] & 0xff;   
					if (i == len) {   
						sb.append(base64EncodeChars[b1 >>> 2]);   
						sb.append(base64EncodeChars[(b1 & 0x3) << 4]);   
						sb.append("==");   
						break;   
					}   
					b2 = data[i++] & 0xff;   
					if (i == len) {   
						sb.append(base64EncodeChars[b1 >>> 2]);   
						sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);   
						sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);   
						sb.append("=");   
						break;   
					}   
					b3 = data[i++] & 0xff;   
					sb.append(base64EncodeChars[b1 >>> 2]);   
					sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);   
					sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);   
					sb.append(base64EncodeChars[b3 & 0x3f]);   
				}
			} catch (Exception e) {
				e = null;
			}
			return sb.toString();   
		}   

		public static byte[] decode(String str) throws UnsupportedEncodingException {   
			StringBuilder sb = new StringBuilder();   
			byte[] data = str.getBytes("US-ASCII");   
			int len = data.length;   
			int i = 0;   
			int b1, b2, b3, b4;
			try {
				while (i < len) {   
					/* b1 */   
					do {
						b1 = base64DecodeChars[data[i++]];   
					} while (i < len && b1 == -1);   
					if (b1 == -1) break;   

					/* b2 */   
					do {   
						b2 = base64DecodeChars[data[i++]];   
					} while (i < len && b2 == -1);   
					if (b2 == -1) break;   
					sb.append((char)((b1 << 2) | ((b2 & 0x30) >>> 4)));   

					/* b3 */   
					do {   
						b3 = data[i++];   
						if (b3 == 61) return sb.toString().getBytes("ISO-8859-1");   
						b3 = base64DecodeChars[b3];   
					} while (i < len && b3 == -1);   
					if (b3 == -1) break;   
					sb.append((char)(((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));   

					/* b4 */   
					do {
						b4 = data[i++];   
						if (b4 == 61) return sb.toString().getBytes("ISO-8859-1");   
						b4 = base64DecodeChars[b4];   
					} while (i < len && b4 == -1);   
					if (b4 == -1) break;   
					sb.append((char)(((b3 & 0x03) << 6) | b4));   
				}
			} catch (Exception e) {
				e = null;
			}
			return sb.toString().getBytes("ISO-8859-1");   
		}   
	}
	
	
	
	
	@Override
	public void v() {
		// TODO: Implement this method
	}

	@Override
	protected void dddd() {
		// TODO: Implement this method
		super.dddd();
	}

	public TestMain() {}
	public TestMain(CharSequence[] s, TestMain t, CharSequence tt, int gggg, XStaticFixedValue ooh) {;}
	public static void main() {System.out.println("=");}
	private static int k = 8;

	public static void main(String[] args) {
		long start;
		try {
			Thread.sleep(500);
			start = System.currentTimeMillis();
			System.out.println(Integer.toHexString(15));
			System.out.println(Integer.toHexString(16));
			System.out.println(Integer.toHexString(32));
			
			System.out.println(XUnicodeSimple.encode("ajcjcoeoejnnc\n")); 
			System.out.println(XUnicodeSimple.decode(XUnicodeSimple.encode("\n")));
			System.out.println(XUnicodeSimple.decode(XUnicodeSimple.encode(XString.repeat("蔡徐坤634768iieoeodococc",500000))).length());
			System.out.println(XSequences.deepIndexOf(
								   XSequences.wrapArr(new int[]{1,1,2,3}),
								   XSequences.wrapArr(new int[]{1}),
								   1,
								   2
							   ));
			System.out.println(XSequences.deepLastIndexOf(
								   XSequences.wrapArr(new int[]{1,1,2,3}),
								   XSequences.wrapArr(new int[]{1,1}),
								   1,
								   0
							   ));
			System.out.println("_____");


			XNsByteArrayOutputStream oo = new XNsByteArrayOutputStream();
			System.out.println(XStream.copyFixedLength(new XNsByteArrayInputStream(XString.Number.getBytes()), oo, 3, 8, true));
			System.out.println(new String(oo.toByteArray()));

			String[] testStrArr = new String[1];
			XSequences.arraycopy(XSequences.wrap(new Object[]{8}), 0, XSequences.wrap(testStrArr), 0, 1);
			System.out.println(Arrays.toString(testStrArr));

			XArray.copyOfConversion(new int[]{1,2,3,4,5}, new long[]{});

			int testlength = 1000000;
			Object[] testObjArr = (Object[]) XArray.newInstance(Object.class, testlength, 0L);
			long[] testLongArr = new long[testlength];

			System.out.println(XArrays.equals(new int[]{4}, new int[]{4}));
			System.out.println(XArrays.equalsRange(new int[]{4,5,7,9,10}, 2, new int[]{4,7,9}, 1, 2));
			System.out.println(XArrays.deepEqualsRange(new Object[]{new int[]{4,7,9},"a","b"}, 0, new Object[]{new int[]{4,7,9}}, 0, 1));

			XTiming ff0 = XTiming.newAndStart();

			System.out.println(XSequences.deepIndexOf(
								   XSequences.wrapArr(new Object[]{0,new Object[]{null,new int[]{8}},new int[]{123},new Object[][]{{null,6}},"cxk",5}),
								   XSequences.wrapArr(new Object[]{new Object[]{null,new int[]{8}},new int[]{123}}),
								   0,
								   6
							   ));
			System.out.println(XSequences.deepLastIndexOf(
								   XSequences.wrapArr(new Object[]{0,new Object[]{null,new int[]{8}},new int[]{123},new Object[][]{{null,6}},"cxk",5}),
								   XSequences.wrapArr(new Object[]{new Object[]{null,new int[]{8}},new int[]{123}}),
								   6,
								   0  
							   ));

			System.out.println(XSequences.deepIndexOf(
								   XSequences.wrapArr(new int[]{1,2,3}),
								   XSequences.wrapArr(new long[]{1,2,3}),
								   0,
								   3
							   ));
			System.out.println("&" + ff0.endAndGetEndLessStart());
			System.out.println();

			XTiming ff1 = XTiming.newAndStart();
			XArrays.arraycopyTraverse(testLongArr, 0, testObjArr, 0, testObjArr.length);
			System.out.println(ff1.endAndGetEndLessStart());

			XTiming ff2 = XTiming.newAndStart();
			XObjectSequenceImpl testObjArrS = new XObjectSequenceImpl(testObjArr);
			XLongSequenceImpl testLongArrS = new XLongSequenceImpl(testLongArr);
			XSequences.arraycopy(testLongArrS, 0, testLongArrS, 0, testObjArrS.length());
			System.out.println(ff2.endAndGetEndLessStart());


			testLongArrS = null;
			testObjArrS = null;
			System.out.println();
			Class mainclass = TestMain.class;

			Method[] ms0 = XReflectAccessible.getMethodsAll(mainclass);
			for (Method m:ms0) System.out.println(m);
			System.out.println();
			System.out.println(Arrays.toString(mainclass.getMethods()));
			System.out.println(Arrays.toString(mainclass.getDeclaredMethods()));
			System.out.println();

			XReflectMatcher.defaultInstance.getMethod(XReader.class, "read", new char[0]);
			XReflectMatcher.defaultInstance.getMethod(mainclass, "main", new String[]{});
			System.out.println(XReflectMatcher.defaultInstance.getField(mainclass, "k"));

			XTiming jsq0 = XTiming.newAndStart();
			for (int i = 0;i < 1000000;i++) {
				XReflectMatcher.defaultInstance.getConstructor(mainclass,
															   new String[]{}, new TestMain(), "", 1, new XStaticFixedValue());
			}
			System.out.println(jsq0.endAndGetEndLessStart());

			System.out.println();
			Thread.sleep(69999999);

			XTiming st0 = XTiming.newAndStart();
			XFileEdit.ReadOption xfer = new XFileEdit.ReadOption(new File("/storage/emulated/0/_RedmiNote7/miui_LAVENDER_V10.2.13.0.PFGCNXM_85f3f1b262_9.0.zip"));
			System.out.println(xfer.indexOf("cxkxyz".getBytes(), 0, xfer.length()));
			System.out.println(st0.endAndGetEndLessStart());
			System.out.println();

			System.out.println();


			XURLParam xurlp = new XURLParam("?from=1012852s&%E8%94%A1%E5%BE%90%E5%9D%A4=%E8%A1%8C%E4%B8%BA");
			System.out.println(xurlp.get("蔡徐坤"));
			xurlp.clear();
			xurlp.put("哈哈哈", "你球打的像蔡徐坤");
			xurlp.put("??", "esm");
			System.out.println(xurlp);
			System.out.println(xurlp.param2URLFormat());
			System.out.println(xurlp.get("哈哈哈"));

			String urlStr = "http://tester:123456@www.baidu.com?sb?a=b&b=c&c=d#abc";
			URL url = new URL(urlStr);
			String protocol = url.getProtocol();
			String host = url.getHost();
			int port = url.getPort();
			int defaultPort = url.getDefaultPort();
			String query = url.getQuery();
			String ref = url.getRef();
			String user = url.getUserInfo();
			String authority = url.getAuthority();
			String file = url.getFile();
			//Object content = url.getContent();
			System.out.println(url.getPath());

			System.out.println("______");
			String testUrl;
			testUrl = "http://tester:123456@www.baidu.com/s/k?s/sb?a=b  #abc/";
			//testUrl = "http://127.0.0.1:7777/_HM4X/1qw/r/t/b";
			//testUrl = "http://127.0.0.1:7777?/post=4/_HM4X/1qw/r/t/b";
			//testUrl = "http://127.0.0.1:7777/l/..?path=%2F_PhoneFile%2F/listmode=1";
			testUrl = "http://tester:123456@www.baidu.com//a///b///c/..//?sb//../..///?a=b&b=c&c=d//../#cxk";

			System.out.println(testUrl);
			XURL tsx = new XURL(testUrl).abs();
			System.out.println(tsx);
			Method[] ms = XURL.class.getMethods();
			for (Method m:ms) {
				if (m.getParameterTypes().length == 0) {
					System.out.println(m.getName() + " ==> " + m.invoke(tsx));
				}
			}

			System.out.println("______");
			System.out.println(XURL.abs("    http://127.0.0.1:7777/_HM4X/k/h/hk?                /u&r#?a=8#??_#hash.html#?a=b").getParam() + ",");
			System.out.println(XURL.abs("http://127.0.0.1:7777/_HM4X/1qw/r/t/b").getParent());
			System.out.println("_________");
			System.out.println(XURL.abs(("http://_HM4X:nmsl/gvv/e/ec/cc../.././gg/sss/")).getParent());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/gvv/e/ec/cc../.././gg/sss/u")).getParent());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/gvv/e/ec/cc../.././gg/sss")).getParent());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/sss/..")).getParent());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/sss/a/..")).getParent());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/..")).getParent());
			System.out.println(XURL.abs(("http://_HM4X:nmsl")).getParent());

			System.out.println(XURL.abs(("http://_HM4X:nmsl/aaa/bbb")).getParent());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/aa")).getParent());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/..")).getParent());
			System.out.println(XURL.abs(("http://_HM4X:nmsl")).getDirName());
			System.out.println("_________");

			System.out.println(XURL.abs(("http://a.com/gvv/e/ec/cc../.././gg/sss/")).getFilePath());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/gvv/e/ec/cc../.././gg/sss/u")).getFilePath());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/gvv/e/ec/cc../.././gg/sss")).getFilePath());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/sss/..")).getFilePath());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/sss/a/..")).getFilePath());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/a/../..")).getFilePath());
			System.out.println(XURL.abs(("http://_HM4X:nmsl")).getFilePath());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/aaa/bbb")).getFilePath());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/aa")).getFilePath());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/..")).getFilePath());
			System.out.println(XURL.abs(("http://_HM4X:nmsl/aa/a/s/")).getParent().getUrlFormat());
			System.out.println(XURL.abs("http://_HM4X:nmsl/sb?a=a&k=m").getRoot());

			System.out.println("_________");
			Thread.sleep(1000000);


			System.out.println(XFileTool.getFormatPath("//XSt/*?:]/tt/////.//./././a/b/v//x//a/v**v//n///...//../../();").equals(new File("//XSt/*?:]/tt/////.//./././a/b/v//x//a/v**v//n///...//../../();").getCanonicalPath()));
			System.out.println(XFileTool.getFormatPath("..//XSt/*?:]/tt/////.//././///////../.././a/b/v//x//a/v**v//n///...//../../();"));
			System.out.println(new File("..//XSt/*?:]/tt/////.//././///////../.././a/b/v//x//a/v**v//n///...//../../();"));
			System.out.println(XFileTool.getFormatPath("hhh*/.././//////gggghjj/../..ggg/rrrf"));
			Thread.sleep(1000000);

			System.out.println("\t耗时:" + (System.currentTimeMillis() - start));

			System.out.println("____");

		} catch (Exception e) {
			e.printStackTrace();
		}



		/* test copy file */
		try { 
			start = System.currentTimeMillis();

			Thread.sleep(100000000);
			start = System.currentTimeMillis();

			System.out.println(XArray.toString(new XFile("/storage/emulated/0/AppProjects/XposedBridgeApi-82.jar").getBytes(0, 3)));

			Thread.sleep(100000);

			XCycleSpeedLimiter xioread = new XCycleSpeedLimiter();
			xioread.setCycleMaxSpeed(24 * 1024 * 1024);//限制 每个周期时间内 最多读18M 每个块8192
			xioread.setCycle(500);
			xioread.setLimit(true);

			System.out.println(xioread.isLimit());
			System.out.println(xioread);
			XFileThreadCopy tread = null;
			for (int i = 0;i < 10;i++) {
				tread = new XFileThreadCopy("/sdcard/AppProjects/android-22.jar", "/sdcard/" + i + ".test", xioread);
				tread.start();
			}
			while (true) {
				if (false)
					break;
				//Thread.sleep(1000);
				//System.out.println("当前FileIO读写取速度:" + XFileTool.FileFormatSize(xioread.getCycleUseSpeedEverySecondMax()) + " /s   剩余:" + XFileTool.FileFormatSize(xioread.getCycleFreeSpeed()));
				//if(xioread.getEverySecondAverageSpeed()>0)
				System.out.println("当前平均速度:" + XFileTool.fileUnitFormat(xioread.getAverageSpeed()) + "/S");
			}

			System.out.println();
			System.out.println("\t耗时:" + (new Date().getTime() - start));
			start = new Date().getTime();
			System.out.println();
//			System.out.println("启动完成");
		} catch (Exception e) {
			e.printStackTrace();

		}
	}




	public static class XFileThreadCopy extends Thread {
		private String srcPath;//原文件地址
		private String destPath;//目标文件地址
		private long start,end;//start指定起始位置，end指定结束位置
		private XCycleSpeedLimiter Xiolimit = null;
		//构造CopyThread方法
		public XFileThreadCopy(String srcPath, String destPath, long start, long end) {
			this(srcPath, destPath, start, end, null);
		}
		public XFileThreadCopy(String srcPath, String destPath) {
			this(srcPath, destPath, 0, new File(srcPath).length());
		}
		public XFileThreadCopy(String srcPath, String destPath, long start, long end, XCycleSpeedLimiter xio) {
			this.srcPath = srcPath;//要复制的源文件路径
			this.destPath = destPath;//复制到的文件路径
			this.start = start;//复制起始位置
			this.end = end;//复制结束位置
			this.Xiolimit = xio;//数据流速度限制器
		}
		public XFileThreadCopy(String srcPath, String destPath, XCycleSpeedLimiter xio) {
			this(srcPath, destPath, 0, new File(srcPath).length(), xio);
		}


		public final static int state_copying = 1;
		public final static int state_copyComplete = 2;
		public final static int state_copyException = 4;
		private double copyPercentage = 0;
		private int state = 0;
		private Exception e;
		public int getCopyState() {
			return state;
		}
		public Exception getCopyException() {
			return e;
		}
		public double getCopyPercentage() {
			return copyPercentage;
		}

		public void run() {
			try {
				state = state_copying;
				copyPercentage = 0;
				e = null;

				//创建一个只读的随机访问文件
				RandomAccessFile randomIn = new RandomAccessFile(srcPath, "r");
				//创建一个可读可写的随机访问文件
				RandomAccessFile randomOut = new RandomAccessFile(destPath, "rw");
				randomIn.seek(start);// 将输入跳转到指定位置
				randomOut.seek(start);// 从指定位置开始写
				long copylength = end - start;

				InputStream in;
				in = new XRandomAccessFileInputStream(randomIn);
				in = new XNsInputStreamFixedLength(in, copylength);
				in = XCycleSpeedLimiter.wrap(in, Xiolimit);

				OutputStream out;
				out = new XRandomAccessFileOutputStream(randomOut, start);
				//out = XCycleSpeedLimiter.wrap(out, Xiolimit);

				byte[] buffer = new byte[8192];
				int read = -1;
				long length = 0;
				while (true) {
					if ((read = in.read(buffer)) == -1)
						break;

					out.write(buffer, 0, read);
					length += read;
					copyPercentage = ((double)length / (double)copylength) * 100;
				}
				out.close();//从里到外关闭文件
				in.close();//关闭文件
				state = state_copyComplete;
			} catch (Exception e) {
				this.e = e;
				state = state_copyException;
			}
		}
	}


}

