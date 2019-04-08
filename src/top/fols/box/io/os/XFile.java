package top.fols.box.io.os;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import top.fols.box.io.XStream;
import top.fols.box.io.base.XInputStreamFixedLength;
import top.fols.box.io.base.XOutputStreamFixedLength;
import top.fols.box.statics.XStaticFixedValue;

public class XFile implements Closeable {

	@Override
	public void close() {
		// TODO: Implement this method
		try {
			this.r.close();
		} catch (Exception e) {
			this.r = null;
		}
	}

	private final String fileCanonicalPath;
	private File file;
	private RandomAccessFile r;
	public XFile(String file) {
		this(new File(file));
	}
	public XFile(File file) {
		this.file = file;
		try {
			this.fileCanonicalPath = file.getCanonicalPath();
		} catch (IOException e) {
			this.fileCanonicalPath = file.getAbsolutePath();
		}
	}
	private void init()throws IOException {
		if (r == null)
			r = new RandomAccessFile(fileCanonicalPath, XStaticFixedValue.FileValue.getRandomAccessFile_Mode_RW_String());
	}
	public File getFile() {
		return this.file;
	}





	public String getExtensionName() { 
		return XFileTool.getExtensionName(fileCanonicalPath);
	}
	public String getName() {
		return XFileTool.getName(fileCanonicalPath);
	}
	public String getNameNoExtension() {
		return XFileTool.getNameNoExtension(fileCanonicalPath);
	}
	
	public static String getExtensionName(String fileCanonicalPath) { 
		return XFileTool.getExtensionName(fileCanonicalPath);
	}
	public static String getName(String fileCanonicalPath) {
		return XFileTool.getName(fileCanonicalPath);
	}
	public static String getNameNoExtension(String fileCanonicalPath) {
		return XFileTool.getNameNoExtension(fileCanonicalPath);
	}








	public InputStream getRangeInputStream(long off, long len) throws IOException {
		XRandomAccessFileInputStream in = new XRandomAccessFileInputStream(fileCanonicalPath);
		in.seekIndex(off);
		return new XInputStreamFixedLength(in, len);
	}
	public OutputStream getRangeOutputStream(long off, long len) throws IOException {
		XRandomAccessFileOutputStream out = new XRandomAccessFileOutputStream(fileCanonicalPath);
		out.seekIndex(off);
		return new XOutputStreamFixedLength(out, len);
	}



	public String getPath() {
		return fileCanonicalPath;
	}


	public XFile append(String bytes) throws IOException {
		byte[] bytes2 = bytes.getBytes();
		return append(bytes2, 0, bytes2.length);
	}
	public XFile append(String bytes, String encoding) throws IOException {
		byte[] bytes2 = bytes.getBytes(encoding);
		return append(bytes2, 0, bytes2.length);
	}
	public XFile append(byte[] bytes) throws  IOException {
		return append(bytes, 0, bytes.length);
	}
	public XFile append(byte[] bytes, int off, int len) throws IOException {
		init();
		if (len < 0)
			return this;
		r.seek(r.length());
		r.write(bytes, off, len);
		return this;
	}
	public XFile append(InputStream in, long len) throws IOException {
		init();
		if (len < 0)
			return this;
		r.seek(r.length());
		XStream.copyFixedLength(in, new XRandomAccessFileOutputStream(r), len);
		return this;
	}
	public XFile append(int p1) throws FileNotFoundException, IOException {
		init();
		r.seek(r.length());
		r.write(p1);
		return this;
	}





	public XFile empty() throws IOException {
		init();
		r.setLength(0);
		return this;
	}
	public long length() {
		return file.length();
	}
	public String toString() {
		try {
			byte[] bs = getBytes();
			if (bs == null)
				return null;
			return new String(bs);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public String toString(String encoding) throws IOException {
		return new String(getBytes(), encoding);
	}
	public byte[] getBytes() throws IOException {
		if (!file.exists())
			return null;
		InputStream in = new XRandomAccessFileInputStream(fileCanonicalPath);
		byte[] b = XStream.inputstream.toByteArray(in);
		in.close();
		return b;
	}
	public byte[] getBytes(long off, long len) throws IOException {
		if (!file.exists())
			return null;
		InputStream in = getRangeInputStream(off, len);
		byte[] b = XStream.inputstream.toByteArray(in);
		in.close();
		return b;
	}
	public XFileEdit.ReadOption toFileEditReadOption() throws FileNotFoundException, IOException {
		return new XFileEdit.ReadOption(new File(fileCanonicalPath));
	}
	public XFileEdit.WriteOption toFileEditWriteOption() throws FileNotFoundException, IOException {
		return new XFileEdit.WriteOption(new File(fileCanonicalPath));
	}
	
	
	
	public InputStream toInputStream() throws IOException {
		return new XRandomAccessFileInputStream(fileCanonicalPath);
	}
	public OutputStream toOutputStream() throws IOException {
		return new XRandomAccessFileOutputStream(fileCanonicalPath);
	}
	
	
	
	
	
	
	public XFile copyTo(XFile f) throws IOException {
		return copyTo(f.fileCanonicalPath);
	}
	public XFile copyTo(File f) throws IOException {
		return copyTo(f.getPath());
	}
	public XFile copyTo(String f) throws IOException {
		return copyTo(f, true);
	}
	public XFile copyTo(String f, boolean check) throws IOException {
		File originFile = new File(fileCanonicalPath);
		File copyToFile = new File(f);
		if (check && copyToFile.exists())
			throw new IOException("file exist");
		
		XRandomAccessFileOutputStream out = new XRandomAccessFileOutputStream(f);
		out.setLength(0);
		out.seekIndex(0);

		XRandomAccessFileInputStream in = new XRandomAccessFileInputStream(originFile);
		XStream.copy(in, out);
		in.close();
		out.close();
		return this;
	}





}
