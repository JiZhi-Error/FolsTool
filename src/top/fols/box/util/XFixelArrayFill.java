package top.fols.box.util;
import java.util.Arrays;
import java.lang.reflect.Array;

public class XFixelArrayFill<K extends Object> {
	public boolean fillLeft = true;
	private Object[] array;
	private int length;
	public XFixelArrayFill(int len) {
		if (len < 1)
			throw new NumberFormatException("len need > 0");
		array = new Object[len];
		length = len;
	}
	//<-
	private void left(Object val) {
		for (int i = 0;i < length - 1;i++)
			array[i] = array[i + 1];
		array[length - 1] = val;
	}
	//->
	private void right(Object val) {
		for (int i = length - 1;i >= 1;i--)
			array[i] = array[i - 1];
		array[0] = val;
	}
	public void fill(K val) {
		if (fillLeft)
			left(val);
		else
			right(val);
	}
	public void fill(K...val) {
		if (fillLeft)
			for (K k:val)
				left(k);
		else
			for (K k:val)
				right(k);
	}
	public int length() {return length;}
	public void free() {
		array = null;
		length = 0;
	}
	public K get(int i) {return (K)array[i];}
	public void set(int i, K val) {array[i] = val;}
	public Object[] getArray() {return array;}




	public static class longsFill {
		public boolean fillLeft = true;
		private long[] array;
		private int length;
		public longsFill(int len) {
			if (len < 1)
				throw new NumberFormatException("len need > 0");
			array = new long[len];
			length = len;
		}
		//<-
		private void left(long val) {
			for (int i = 0;i < length - 1;i++)
				array[i] = array[i + 1];
			array[length - 1] = val;
		}
		//->
		private void right(long val) {
			for (int i = length - 1;i >= 1;i--)
				array[i] = array[i - 1];
			array[0] = val;
		}
		public void fill(long val) {
			if (fillLeft)
				left(val);
			else
				right(val);
		}
		public void fill(long...val) {
			if (fillLeft)
				for (long k:val)
					left(k);
			else
				for (long k:val)
					right(k);
		}
		public int length() {return length;}
		public void free() {
			array = null;
			length = 0;
		}
		public long get(int i) {return array[i];}
		public void set(int i, long val) {array[i] = val;}
		public long[] getArray() {return array;}
	}
	public static class doublesFill {
		public boolean fillLeft = true;
		private double[] array;
		private int length;
		public doublesFill(int len) {
			if (len < 1)
				throw new NumberFormatException("len need > 0");
			array = new double[len];
			length = len;
		}
		//<-
		private void left(double val) {
			for (int i = 0;i < length - 1;i++)
				array[i] = array[i + 1];
			array[length - 1] = val;
		}
		//->
		private void right(double val) {
			for (int i = length - 1;i >= 1;i--)
				array[i] = array[i - 1];
			array[0] = val;
		}
		public void fill(double val) {
			if (fillLeft)
				left(val);
			else
				right(val);
		}
		public void fill(double...val) {
			if (fillLeft)
				for (double k:val)
					left(k);
			else
				for (double k:val)
					right(k);
		}
		public int length() {return length;}
		public void free() {
			array = null;
			length = 0;
		}
		public double get(int i) {return array[i];}
		public void set(int i, double val) {array[i] = val;}
		public double[] getArray() {return array;}
	}
	public static class bytesFill {
		public boolean fillLeft = true;
		private byte[] array;
		private int length;
		public bytesFill(int len) {
			if (len < 1)
				throw new NumberFormatException("len need > 0");
			array = new byte[len];
			length = len;
		}
		//<-
		private void left(byte val) {
			for (int i = 0;i < length - 1;i++)
				array[i] = array[i + 1];
			array[length - 1] = val;
		}
		//->
		private void right(byte val) {
			for (int i = length - 1;i >= 1;i--)
				array[i] = array[i - 1];
			array[0] = val;
		}
		public void fill(byte val) {
			if (fillLeft)
				left(val);
			else
				right(val);
		}
		public void fill(byte...val) {
			if (fillLeft)
				for (byte k:val)
					left(k);
			else
				for (byte k:val)
					right(k);
		}
		public int length() {return length;}
		public void free() {
			array = null;
			length = 0;
		}
		public byte get(int i) {return array[i];}
		public void set(int i, byte val) {array[i] = val;}
		public byte[] getArray() {return array;}
	}
	public static class booleansFill {
		public boolean fillLeft = true;
		private boolean[] array;
		private int length;
		public booleansFill(int len) {
			if (len < 1)
				throw new NumberFormatException("len need > 0");
			array = new boolean[len];
			length = len;
		}
		//<-
		private void left(boolean val) {
			for (int i = 0;i < length - 1;i++)
				array[i] = array[i + 1];
			array[length - 1] = val;
		}
		//->
		private void right(boolean val) {
			for (int i = length - 1;i >= 1;i--)
				array[i] = array[i - 1];
			array[0] = val;
		}
		public void fill(boolean val) {
			if (fillLeft)
				left(val);
			else
				right(val);
		}
		public void fill(boolean...val) {
			if (fillLeft)
				for (boolean k:val)
					left(k);
			else
				for (boolean k:val)
					right(k);
		}
		public int length() {return length;}
		public void free() {
			array = null;
			length = 0;
		}
		public boolean get(int i) {return array[i];}
		public void set(int i, boolean val) {array[i] = val;}
		public boolean[] getArray() {return array;}
	}
}
