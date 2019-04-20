package top.fols.box.util.messagedigest.javamd5;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class XMD5OutputStream extends FilterOutputStream {
    /**
     * MD5 context
     */
    private XMD5	md5;
    /**
     * Creates MD5OutputStream
     * @param out	The output stream
     */
    public XMD5OutputStream(OutputStream out) {
		super(out);
		md5 = new XMD5();
    }
    /**
     * Writes a byte. 
     *
     * @see java.io.FilterOutputStream
     */
    public void write(int b) throws IOException {
		out.write(b);
		md5.update((byte) b);
    }
    /**
     * Writes a sub array of bytes.
     *
     * @see java.io.FilterOutputStream
     */
    public void write(byte b[], int off, int len) throws IOException {
		out.write(b, off, len);
		md5.update(b, off, len);
    }
    /**
     * Returns array of bytes representing hash of the stream as finalized
     * for the current state.
     * @see MD5#Final
     */
    public byte[] hash() {
		return md5.Final();
    }
	public XMD5 getMD5() {
		return md5;
	}

	/**
	 * This method is here for testing purposes only - do not rely
	 * on it being here.
	 **/
//	public static void main(String[] arg) {
//		try {
//			ByteArrayOutputStream o = new ByteArrayOutputStream();
//			MD5OutputStream out = new MD5OutputStream(o);
//			InputStream in = new BufferedInputStream(new FileInputStream("Main.java"));
//			byte[] buf = new byte[65536];
//			int num_read;
//			long total_read = 0;
//			while ((num_read = in.read(buf)) != -1) {
//				total_read += num_read;
//				out.write(buf, 0, num_read);
//			}
//			System.out.println(MD5.asHex(out.hash()) + "  " + "Main.java");
//			in.close();
//			out.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//
//	}


}

