package top.fols.box.lang;
import top.fols.box.annotation.XAnnotations;
import java.io.OutputStream;
import java.io.IOException;
public class XBits {


	public static final int boolean_byte_length = 1;
	public static final int char_byte_length = 2;
	public static final int short_byte_length = 2;
	public static final int int_byte_length = 4;
	public static final int float_byte_length = 4;
	public static final int long_byte_length = 8;
	public static final int double_byte_length = 8;



    /*
     * Methods for unpacking primitive values from byte arrays starting at
     * given offsets.
     */
	@XAnnotations("need length 1")
	public static boolean getBoolean(byte[] b, int off) {
        return b[off] != 0;
    }
	@XAnnotations("need length 2")
	public static char getChar(byte[] b, int off) {
        return (char) ((b[off + 1] & 0xFF) +
			(b[off] << 8));
    }
	@XAnnotations("need length 2")
	public static short getShort(byte[] b, int off) {
        return (short) ((b[off + 1] & 0xFF) +
			(b[off] << 8));
    }
	@XAnnotations("need length 4")
	public static int getInt(byte[] b, int off) {
        return ((b[off + 3] & 0xFF)) +
			((b[off + 2] & 0xFF) <<  8) +
			((b[off + 1] & 0xFF) << 16) +
			((b[off]) << 24);
    }
	@XAnnotations("need length 4")
	public static float getFloat(byte[] b, int off) {
        return Float.intBitsToFloat(getInt(b, off));
    }
	@XAnnotations("need length 8")
	public static long getLong(byte[] b, int off) {
        return ((b[off + 7] & 0xFFL)) +
			((b[off + 6] & 0xFFL) <<  8) +
			((b[off + 5] & 0xFFL) << 16) +
			((b[off + 4] & 0xFFL) << 24) +
			((b[off + 3] & 0xFFL) << 32) +
			((b[off + 2] & 0xFFL) << 40) +
			((b[off + 1] & 0xFFL) << 48) +
			(((long) b[off])      << 56);
    }
	@XAnnotations("need length 8")
	public static double getDouble(byte[] b, int off) {
        return Double.longBitsToDouble(getLong(b, off));
    }

    /*
     * Methods for packing primitive values into byte arrays starting at given
     * offsets.
     */
	@XAnnotations("need length 1")
	public static void putBoolean(byte[] b, int off, boolean val) {
        b[off] = (byte) (val ? 1 : 0);
    }
	@XAnnotations("need length 2")
	public static void putChar(byte[] b, int off, char val) {
        b[off + 1] = (byte) (val);
        b[off] = (byte) (val >>> 8);
    }
	@XAnnotations("need length 2")
	public static void putShort(byte[] b, int off, short val) {
        b[off + 1] = (byte) (val);
        b[off] = (byte) (val >>> 8);
    }
	@XAnnotations("need length 4")
	public static void putInt(byte[] b, int off, int val) {
        b[off + 3] = (byte) (val);
        b[off + 2] = (byte) (val >>>  8);
        b[off + 1] = (byte) (val >>> 16);
        b[off] = (byte) (val >>> 24);
    }
	@XAnnotations("need length 4")
	public static void putFloat(byte[] b, int off, float val) {
        putInt(b, off,  Float.floatToIntBits(val));
    }
	@XAnnotations("need length 8")
	public static void putLong(byte[] b, int off, long val) {
        b[off + 7] = (byte) (val);
        b[off + 6] = (byte) (val >>>  8);
        b[off + 5] = (byte) (val >>> 16);
        b[off + 4] = (byte) (val >>> 24);
        b[off + 3] = (byte) (val >>> 32);
        b[off + 2] = (byte) (val >>> 40);
        b[off + 1] = (byte) (val >>> 48);
        b[off] = (byte) (val >>> 56);
    }
	@XAnnotations("need length 8")
	public static void putDouble(byte[] b, int off, double val) {
        putLong(b, off, Double.doubleToLongBits(val));
    }


	public static class OutputStreamBits {
		private OutputStream os;
		public void setOutputStream(OutputStream os) {
			this.os = os;
		}

		@XAnnotations("need length 1")
		public void writerBoolean(boolean val) throws IOException {
			os.write((byte) (val ? 1 : 0));
		}

		private byte[] b21 = new byte[2];
		@XAnnotations("need length 2")
		public void writerChar(char val) throws IOException {
			b21[1] = (byte) (val);
			b21[0] = (byte) (val >>> 8);
			os.write(b21);
		}

		private byte[] b22 = new byte[2];
		@XAnnotations("need length 2")
		public void writerShort(short val) throws IOException {
			b22[1] = (byte) (val);
			b22[0] = (byte) (val >>> 8);
			os.write(b22);
		}
		private byte[] b41 = new byte[4];
		@XAnnotations("need length 4")
		public void writerInt(int val) throws IOException {
			b41[3] = (byte) (val);
			b41[2] = (byte) (val >>>  8);
			b41[1] = (byte) (val >>> 16);
			b41[0] = (byte) (val >>> 24);
			os.write(b41);
		}


		@XAnnotations("need length 4")
		public void writerFloat(float val) throws IOException {
			writerInt(Float.floatToIntBits(val));
		}
		private byte[] b81 = new byte[8];
		@XAnnotations("need length 8")
		public void writerLong(long val) throws IOException {
			b81[7] = (byte) (val);
			b81[6] = (byte) (val >>>  8);
			b81[5] = (byte) (val >>> 16);
			b81[4] = (byte) (val >>> 24);
			b81[3] = (byte) (val >>> 32);
			b81[2] = (byte) (val >>> 40);
			b81[1] = (byte) (val >>> 48);
			b81[0] = (byte) (val >>> 56);
			os.write(b81);
		}
		@XAnnotations("need length 8")
		public void writerDouble(double val) throws IOException {
			writerLong(Double.doubleToLongBits(val));
		}
	}


}

