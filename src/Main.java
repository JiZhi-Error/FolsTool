
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Date;
import top.fols.box.io.XStream;
import top.fols.box.io.base.ByteArrayOutputStreamUtils;
import top.fols.box.io.base.XHexStream;
import top.fols.box.io.base.XIOLimiter;
import top.fols.box.io.base.XInputStreamFixedLength;
import top.fols.box.io.base.XInputStreamReaderRow;
import top.fols.box.io.base.ns.XNsInputStreamFixedLength;
import top.fols.box.io.os.XFile;
import top.fols.box.io.os.XFileTool;
import top.fols.box.io.os.XRandomAccessFileInputStream;
import top.fols.box.io.os.XRandomAccessFileOutputStream;
import top.fols.box.lang.XClassUtils;
import top.fols.box.lang.XMath;
import top.fols.box.net.XURL;
import top.fols.box.net.XURLConnectionTool;
import top.fols.box.util.XArrays;
import top.fols.box.util.XCycleSpeedLimiter;
import top.fols.box.util.XEncodingDetect;
import top.fols.box.util.XObjects;
import top.fols.box.util.md5.XMD5;
import top.fols.box.util.md5.XMD5Algorithm;

public class Main {
	public static void main(String[] args) {
		long start;
		start = System.currentTimeMillis();
		try {
			//https://h5.m.taobao.com/guoguo/app-myexpress-taobao/search-express.html
			XURLConnectionTool.get g = new XURLConnectionTool.get("https://m.sogou.com");
			System.out.println(g.getURLConnection().getHeaderFields());

			g = new XURLConnectionTool.get("https://h5api.m.taobao.com/h5/mtop.taobao.logisticsdetailtracequeryservice.querycpbymailno/1.0/?jsv=2.4.0&appKey=12574478&t=1550664343843&sign=88826e51125ddc937265e96df31330c7&api=mtop.taobao.logisticsDetailTraceQueryService.queryCpByMailNo&v=1.0&type=jsonp&dataType=jsonp&callback=mtopjsonp5&data=%7B%22mailNo%22%3A%22246191428421%22%7D");
			g.ua(new XFile("/sdcard/HttpUa.txt").toString()); 
			ByteArrayOutputStreamUtils insb = new ByteArrayOutputStreamUtils();
			g.read2(insb);
			System.out.println(new String(insb.toByteArray(), XEncodingDetect.getJavaEncode(insb.toByteArray())));


			Thread.sleep(1000000000);

			XMD5 m = new XMD5();
			m.Update(new XFile("/storage/emulated/0/AppProjects/x/folsTool/src/top/fols/box/util/ArrayListUtils.java").getBytes());
			System.out.println(m.asHex());


			OutputStream o = new FileOutputStream("/sdcard/gg");
			o.write((byte)0xff);
			o.flush();


			System.out.println("\t耗时:" + (System.currentTimeMillis() - start));
			System.out.println(XMath.getBit(2222, 1000));

			Thread.sleep(10000000);
			XCycleSpeedLimiter xioread = XIOLimiter.get("FileIO");
			xioread.setCycleMaxSpeed(1024 * 1024);//限制 每个周期时间内 最多读18M 每个块8192
			xioread.setCycle(100);
			xioread.setLimit(true);

			System.out.println(xioread.isLimit());
			System.out.println(xioread);
			XFileThreadCopy tread = null;
			for (int i = 0;i < 10;i++) {
				tread = new XFileThreadCopy("/storage/emulated/0/AppProjects/top.fols.toolboxV1.7.2T20180712x.zip", "/sdcard/" + i + ".test", xioread);
				tread.start();
			}
			while (true) {
				if (false)
					break;
				//Thread.sleep(1000);
				//System.out.println("当前FileIO读写取速度:" + XFileTool.FileFormatSize(xioread.getCycleUseSpeedEverySecondMax()) + " /s   剩余:" + XFileTool.FileFormatSize(xioread.getCycleFreeSpeed()));
				//if(xioread.getEverySecondAverageSpeed()>0)
				System.out.println();
				//System.out.println(Arrays.toString(xioread.averageSpeedList.getArray()));
				//System.out.println(Arrays.toString(xioread.averageSpeedUpdateTimeList.getArray()));
				System.out.println("当前平均速度:" + XFileTool.fileFormatSize(xioread.getEverySecondAverageSpeed()) + "/S");
			}


			Thread.sleep(69999999);

			XStream.copy(new XRandomAccessFileInputStream("/sdcard/AppProjects/sources-27_r01.zip"), new XHexStream.EncOutputStream(new XRandomAccessFileOutputStream("/sdcard/hex").setLength(0)));
			XStream.copy(new XHexStream.DecInputStream(new XRandomAccessFileInputStream("/sdcard/hex")), new XRandomAccessFileOutputStream("/sdcard/hexDec").setLength(0));
//			
//			InputStream indep = new XHexStream.DecInputStream(XStream.wrapInputStream(XHexStream.encode("呵呵1".getBytes())));
//			System.out.println(Arrays.toString(XHexStream.decode(XHexStream.encode("呵呵1".getBytes()))));
//			
//			XStream.copy(indep,null);


			System.out.println(System.currentTimeMillis() - start);
			System.out.println("我们不一样");
			Thread.sleep(66666666);
			XInputStreamReaderRow Row = new XInputStreamReaderRow(new XRandomAccessFileInputStream(new File("/sdcard/reader.txt")));
			Row.setLineSplitChar("我乃神人".toCharArray());
			System.out.println(Arrays.toString(XObjects.tointArray(Row.readLine(false))));
			System.out.println(Row.getReadLineReadToByteLength());
			System.out.println(Arrays.toString(XObjects.tointArray(Row.readLine(false))));
			System.out.println(Row.getReadLineReadToByteLength());
			System.out.println(Arrays.toString(XObjects.tointArray(Row.readLine(false))));

			System.out.println("\t序耗时:" + (System.currentTimeMillis() - start));


			Thread.sleep(6666666);

			System.out.println("____");

			InputStream in = XStream.wrapInputStream(new byte[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16});
			XNsInputStreamFixedLength fixed = new XNsInputStreamFixedLength(in, 3);
			System.out.println(fixed.read());
			System.out.println(fixed.read());
			System.out.println(fixed.read());

			System.out.println(fixed.isFixedLengthAvailable());
			fixed.setFixedLength(false);
			System.out.println(fixed.isFixedLengthAvailable());

			fixed.setFixedLengthMaxSize(4);
			byte[] bytes = new byte[8];
			System.out.println("read=" + fixed.read(bytes));
			System.out.println(Arrays.toString(bytes));
			System.out.println(fixed.read());


			Thread.sleep(6666666);
			System.out.println(XClassUtils.isInstance(8, Integer.class));



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

			XCycleSpeedLimiter xioread = XIOLimiter.get("FileIO");
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
				System.out.println("当前平均速度:" + XFileTool.fileFormatSize(xioread.getEverySecondAverageSpeed()) + "/S");
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
				in = new XInputStreamFixedLength(in, copylength);
				in = XIOLimiter.wrap(in, Xiolimit);

				OutputStream out;
				out = new XRandomAccessFileOutputStream(randomOut, start);
				out = XIOLimiter.wrap(out, Xiolimit);

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

