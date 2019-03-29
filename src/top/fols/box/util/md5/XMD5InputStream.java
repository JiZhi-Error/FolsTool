package top.fols.box.util.md5;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

class XMD5InputStream extends FilterInputStream {
	/**
	 * MD5 context
	 */
	private XMD5	md5;
	/**
	 * Creates a MD5InputStream
	 * @param in	The input stream
	 */
	public XMD5InputStream(InputStream in) {
		super(in);
		md5 = new XMD5();
	}

	/**
	 * Read a byte of data. 
	 * @see java.io.FilterInputStream
	 */
	public int read() throws IOException {
		int c = in.read();
		if (c == -1)
			return -1;
		if ((c & ~0xff) != 0) {
			System.out.println("MD5InputStream.read() got character with (c & ~0xff) != 0)!");
		} else {
			md5.Update(c);
		}
		return c;
	}

	/**
	 * Reads into an array of bytes.
	 *
	 * @see java.io.FilterInputStream
	 */
	public int read(byte bytes[], int offset, int length) throws IOException {
		int	r;
		if ((r = in.read(bytes, offset, length)) == -1)
			return r;
		md5.Update(bytes, offset, r);
		return r;
	}

	/**
	 * Returns array of bytes representing hash of the stream as
	 * finalized for the current state. 
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
//			// determine the filename to use and the MD5 impelementation to use
//			String filename = arg[arg.length - 1];
//			// initialize common variables
//			byte[] buf = new byte[65536];
//			int num_read;
//			//   Use the default MD5 implementation that comes with Java
//			MD5InputStream in = new MD5InputStream(new BufferedInputStream(new FileInputStream(filename)));
//			while ((num_read = in.read(buf)) != -1) {
//			}
//			System.out.println(MD5.asHex(in.hash()) + "  " + filename);
//			in.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}



	/**
     * Calculates and returns the hash of the contents of the given file.
     **/
    public static byte[] getHash(File f) throws IOException {
        if (!f.exists()) throw new FileNotFoundException(f.toString());
        InputStream close_me = null;
        try {
            long buf_size = f.length();
            if (buf_size < 512) buf_size = 512;
            if (buf_size > 65536) buf_size = 65536;
            byte[] buf = new byte[(int) buf_size];
            XMD5InputStream in = new XMD5InputStream(new FileInputStream(f));
            close_me = in;
            while (in.read(buf) != -1);
            in.close();
            return in.hash();
        } catch (IOException e) {
            if (close_me != null) try { close_me.close(); } catch (Exception e2) {}
            throw e;
        }
    }
}

