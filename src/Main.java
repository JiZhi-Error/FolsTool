
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import top.fols.box.io.XStream;
import top.fols.box.io.base.ns.XNsByteArrayOutputStream;
import top.fols.box.io.base.ns.XNsInputStreamFixedLength;
import top.fols.box.io.os.XFile;
import top.fols.box.io.os.XFileEdit;
import top.fols.box.io.os.XFileTool;
import top.fols.box.io.os.XRandomAccessFileInputStream;
import top.fols.box.io.os.XRandomAccessFileOutputStream;
import top.fols.box.lang.XClass;
import top.fols.box.lang.XString;
import top.fols.box.lang.reflect.tools.XReflectObjCmf;
import top.fols.box.net.XURL;
import top.fols.box.net.XURLConnectionTool;
import top.fols.box.net.XURLParam;
import top.fols.box.statics.XStaticSystem;
import top.fols.box.time.XTiming;
import top.fols.box.util.XArrays;
import top.fols.box.util.XCycleSpeedLimiter;
import top.fols.box.util.XEncodingDetect;
import top.fols.box.util.XFixelArrayFill;
import top.fols.box.util.XMessageDigest;
import top.fols.box.util.messagedigest.XMessageDigestInterface;

/*
 * 直接使用别的库的类 类名最后会有Utils
 */
public class Main {
	public static void main(String[] args) {
		long start;
		try {
			XCycleSpeedLimiter xioread = new XCycleSpeedLimiter()
				.setCycleMaxSpeed(10 * 1024 * 1024)//限制 每个周期时间内 最多读18M 每个块8192
				.setCycle(1000)
				.setAverageSpeedUpdateCycleSize(1000)
				.setLimit(true);
				
			XTiming st = XTiming.newAndStart();
			XFileThreadCopy tread = null;
			for (int i = 0;i < 10;i++) {
				tread = new XFileThreadCopy("/sdcard/_SD/Phone/Src/Android Sdk Source/sources-28_r01.zip", "/sdcard/" + i + ".test", xioread);
				tread.start();
			}
			double lastSpeed = 0;
			while (true) {
				if (false)
					break;
				//Thread.sleep(1000);
				//System.out.println("当前FileIO读写取速度:" + XFileTool.FileFormatSize(xioread.getCycleUseSpeedEverySecondMax()) + " /s   剩余:" + XFileTool.FileFormatSize(xioread.getCycleFreeSpeed()));
				//if(xioread.getEverySecondAverageSpeed()>0)
				//System.out.println();
				//System.out.println(Arrays.toString(xioread.averageSpeedList.getArray()));
				//System.out.println(Arrays.toString(xioread.averageSpeedUpdateTimeList.getArray()));
				double a = xioread.getAverageSpeed();
				if (lastSpeed != a) {
					//System.out.println(a);
					lastSpeed = a;
				}
				System.out.println("当前平均速度:" + XFileTool.fileUnitFormat(lastSpeed) + "/S, Hs:" + st.end().getEndLessStart());
			}
			Thread.sleep(69999999);
//







			XFixelArrayFill<Object> fill = new XFixelArrayFill<>(10);
			fill.right(new Object[]{1,2,3});
			fill.right(new Object[]{7,8,9});
			fill.right(new Object[]{4,5,6});
			fill.right(new Object[]{10,11,12});

			System.out.println(XArrays.toString(fill.getArray()));


			XTiming st0 = XTiming.newAndStart();
			XFileEdit.ReadOption xfer = new XFileEdit.ReadOption(new File("/storage/emulated/0/_RedmiNote7/miui_LAVENDER_V10.2.13.0.PFGCNXM_85f3f1b262_9.0.zip"));
			System.out.println(xfer.indexOf("cxkxyz".getBytes(), 0, xfer.length()));
			System.out.println(st0.endAndGetEndLessStart());
			System.out.println();


			XTiming s = XTiming.newAndStart();
			XMessageDigestInterface md5 = XMessageDigest.md5Instance();
			md5.write("蔡徐坤".getBytes());
			System.out.println("/" + md5.getHash() + "/");
			System.out.println(s.endAndGetEndLessStart());

			System.out.println("/" + XStaticSystem.getMessageDigestAlgorithms() + "/");


			System.out.println();
			XFileTool.saveFile(new File("/sdcard/file"), XString.repeat("1", 9).getBytes());
			final XFileEdit.WriteOption oos = new XFileEdit.WriteOption("/sdcard/g");
			oos.setLength(100);

//			for (int i = 0;i < 100;i++) {
//				new Thread(){
//					public void run() {
//						new Thread(){
//							public void run() {
//								try {
//									oos.write(0, (System.currentTimeMillis() + "").getBytes());
//								} catch (IOException e) {
//									e.printStackTrace();
//								}
//							}
//						}.start();
//					}
//				}.start();
//			}


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

			Thread.sleep(1000000);



			XCycleSpeedLimiter limiter = new XCycleSpeedLimiter();
			limiter.setCycle(1000);
			limiter.setCycleMaxSpeed(8191);
			limiter.setLimit(true);
			while (true) {
				limiter.waitForFreeLong(8191, false);
				//System.out.println(limiter.getCycleUseSpeed() + " / " + limiter.getCycleFreeSpeed() + " | " + limiter.getEverySecondAverageSpeed());


				if (false) break;
			}

			limiter.waitForFreeLong(1);
			System.out.println(limiter.getCycleUseSpeed() + "/" + limiter.getCycleFreeSpeed());
			limiter.waitForFreeLong(1);
			System.out.println(limiter.getCycleUseSpeed() + "/" + limiter.getCycleFreeSpeed());


			System.out.println(XArrays.toString(XReflectObjCmf.defaultInstance.getObjMethods(Main.class, "main")));
			System.out.println(XFile.getNameNoExtension("/./a.d.xfe"));
			System.out.println(XFileTool.getFormatPath("//XSt/*?:]/tt/////.//./././a/b/v//x//a/v**v//n///...//../../();").equals(new File("//XSt/*?:]/tt/////.//./././a/b/v//x//a/v**v//n///...//../../();").getCanonicalPath()));
			System.out.println(XFileTool.getFormatPath("..//XSt/*?:]/tt/////.//././///////../.././a/b/v//x//a/v**v//n///...//../../();"));
			System.out.println(new File("..//XSt/*?:]/tt/////.//././///////../.././a/b/v//x//a/v**v//n///...//../../();"));
			System.out.println(XFileTool.getFormatPath("hhh*/.././//////gggghjj/../..ggg/rrrf"));

			start = System.currentTimeMillis();

			Thread.sleep(1000000);

			start = System.currentTimeMillis();
			//for (int i = 0;i < 10000 * 100;i++) new XURL ("http://_HM4X/k/h/hk?#/u&r#?a=8#??_#hash.html#?a=b"); System.out.println(System.currentTimeMillis() - start);
			System.out.println(System.currentTimeMillis() - start);



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


//			XInputStreamReaderRow Row = new XInputStreamReaderRow(XStream.wrapInputStream("".getBytes()));
//			Row.setLineSplitChar("我乃神人".toCharArray());
//			System.out.println(Arrays.toString(XObjects.tointArray(Row.readLine(false))));
//			System.out.println(Row.getReadLineReadToByteLength());
//			System.out.println(Arrays.toString(XObjects.tointArray(Row.readLine(false))));
//			System.out.println(Row.getReadLineReadToByteLength());
//			System.out.println(Arrays.toString(XObjects.tointArray(Row.readLine(false))));
//			System.out.println("\t序耗时:" + (System.currentTimeMillis() - start));
//			Thread.sleep(6666666);
//			
//			

			XURL xurl = new XURL("http://sm.ms/fff?w=hdhdhd&h=888&p///=nmsl&a=" + URLEncoder.encode(URLEncoder.encode(new String("测试一下下"))) + "   ");
			XURLParam pa = xurl.getParam();

			//https://h5.m.taobao.com/guoguo/app-myexpress-taobao/search-express.html
			XURLConnectionTool.get g = new XURLConnectionTool.get("https://m.sogou.com");
			System.out.println(g.getURLConnection().getHeaderFields());

			g = new XURLConnectionTool.get("https://h5api.m.taobao.com/h5/mtop.taobao.logisticsdetailtracequeryservice.querycpbymailno/1.0/?jsv=2.4.0&appKey=12574478&t=1550664343843&sign=88826e51125ddc937265e96df31330c7&api=mtop.taobao.logisticsDetailTraceQueryService.queryCpByMailNo&v=1.0&type=jsonp&dataType=jsonp&callback=mtopjsonp5&data=%7B%22mailNo%22%3A%22246191428421%22%7D");
			g.ua(new XFile("/sdcard/HttpUa.txt").toString()); 
			XNsByteArrayOutputStream insb = new XNsByteArrayOutputStream();
			g.read2(insb);
			System.out.println(new String(insb.toByteArray(), XEncodingDetect.getJavaEncode(insb.toByteArray())));


			Thread.sleep(1000000000);


			System.out.println("\t耗时:" + (System.currentTimeMillis() - start));

			System.out.println("____");

			InputStream in = XStream.wrapInputStream(new byte[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16});
			XNsInputStreamFixedLength fixed = new XNsInputStreamFixedLength(in, 3);
			System.out.println(fixed.read());
			System.out.println(fixed.read());
			System.out.println(fixed.read());

			System.out.println(fixed.isAvailable());
			fixed.fixed(false);
			System.out.println(fixed.isAvailable());

			fixed.setMaxUseLength(4);
			byte[] bytes = new byte[8];
			System.out.println("read=" + fixed.read(bytes));
			System.out.println(Arrays.toString(bytes));
			System.out.println(fixed.read());


			Thread.sleep(6666666);
			System.out.println(XClass.isInstance(8, Integer.class));



//			
//			File file = new File("/sdcard/_PhoneFile/ROM ROOT BASE REC/Base/CM12.1_3.0.c6-00241-M8974AAAAANAZM-1.zip");
//			byte[] bs = new XFile(file).getBytes();
//			start = new Date().getTime();
//			
//			ByteArrayOutputStreamUtils byteOutput = new ByteArrayOutputStreamUtils();
//			XHexStream.EncOutputStream Output = new XHexStream.EncOutputStream(byteOutput);
//			Output.write("123456789".getBytes());
//			System.out.println(Output.getStream());
//			System.out.println(new String(XHexStream.decode(XHexStream.encode("123456789".getBytes(),3,4))));
//			
//			ByteArrayInputStreamUtils byteInput = new ByteArrayInputStreamUtils(byteOutput.toByteArray());
//			
//			
//			
//			System.out.println(new String(XStream.inputstream.toByteArray(XHexStream.wrap(byteInput))));
//			System.out.println(new String(XHexStream.decode(byteOutput.toByteArray(),1*2,4*2)));
//			System.out.println(Arrays.toString(XHexStream.decode(byteOutput.toByteArray(),1*2,4*2)));

			Thread.sleep(555555555);

			System.out.println("\t序耗时:" + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			e.printStackTrace();
		}




		try { 
			start = System.currentTimeMillis();

			Thread.sleep(100000000);
			start = System.currentTimeMillis();

			System.out.println(XArrays.toString(new XFile("/storage/emulated/0/AppProjects/XposedBridgeApi-82.jar").getBytes(0, 3)));

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





			System.out.println("\t耗时:" + (new Date().getTime() - start));
			start = new Date().getTime();
			System.out.println();

//          XStreamHandle_TestClass.Test();
//          XDigest_TestClass.Test();
//			XUrl_TestClass.Test();
//			XArraySeach_TestClass.Test();
//			XString_TestClass.Test();
//    		XValueTransform_TestClass.Test();
//			XEscape_TestClass.Test();
//			XInputStreamRowBuffered_TestClass.Test();
//			XArrayTool_TestClass.Test();

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

