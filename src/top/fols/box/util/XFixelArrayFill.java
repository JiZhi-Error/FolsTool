package top.fols.box.util;
import java.util.Arrays;
import top.fols.box.statics.XStaticFixedValue;

public class XFixelArrayFill<K extends Object> {
	private Object[] array;
	private int length;
	public XFixelArrayFill(int len) {
		if (len < 1)
			throw new NumberFormatException("len need > 0");
		array = new Object[len];
		length = len;
	}
	//<- 将数据向左移动
	private void left0(Object val) {
		for (int i = 0;i < length - 1;i++)
			array[i] = array[i + 1];
		array[length - 1] = val;
	}

	public void left(K val) {
		left0(val);
	}
	public void left(K[] val) {
		if (val.length >= length) {
			for (int i = 0;i < length;i++)
				array[i] = val[val.length - length + i];
		} else {
			int ind = length - val.length;
			for (int i = 0;i < ind;i++)
				array[i] = array[length - ind + i];
			for (int i = 0;i < val.length;i++)
				array[ind + i] = val[i];
		}
	}
	
	//-> 将数据向右移动
	private void right0(Object val) {
		for (int i = length - 1;i >= 1;i--)
			array[i] = array[i - 1];
		array[0] = val;
	}
	public void right(K val) {
		right0(val);
	}
	public void right(K[] val) {
		if (val.length >= length) {
			for (int i = 0;i < length;i++)
				array[i] = val[i];
		} else {
			int ind = val.length;
			for (int i = 0;i < length - val.length;i++) {
				array[(length - 1) - i] = array[(length - 1) - ind - i];
			}	
			for (int i = 0;i < ind;i++)
				array[i] = val[i];
		}
	}

	public int length() {return length;}
	public void free() {
		array = XStaticFixedValue.nullObjectArray;
		length = 0;
	}
	public K get(int i) {return (K)array[i];}
	public void set(int i, K val) {array[i] = val;}
	public Object[] getArray() {return array;}

	@Override
	public String toString() {
		// TODO: Implement this method
		return Arrays.toString(array);
	}



	public static class longsFill {
		private long[] array;
		private int length;
		public longsFill(int len) {
			if (len < 1)
				throw new NumberFormatException("len need > 0");
			array = new long[len];
			length = len;
		}
		//<- 将数据向左移动
		private void left0(long val) {
			for (int i = 0;i < length - 1;i++)
				array[i] = array[i + 1];
			array[length - 1] = val;
		}

		public void left(long val) {
			left0(val);
		}
		public void left(long[] val) {
			if (val.length >= length) {
				for (int i = 0;i < length;i++)
					array[i] = val[val.length - length + i];
			} else {
				int ind = length - val.length;
				for (int i = 0;i < ind;i++)
					array[i] = array[length - ind + i];
				for (int i = 0;i < val.length;i++)
					array[ind + i] = val[i];
			}
		}

		//-> 将数据向右移动
		private void right0(long val) {
			for (int i = length - 1;i >= 1;i--)
				array[i] = array[i - 1];
			array[0] = val;
		}
		public void right(long val) {
			right0(val);
		}
		public void right(long[] val) {
			if (val.length >= length) {
				for (int i = 0;i < length;i++)
					array[i] = val[i];
			} else {
				int ind = val.length;
				for (int i = 0;i < length - val.length;i++) {
					array[(length - 1) - i] = array[(length - 1) - ind - i];
				}	
				for (int i = 0;i < ind;i++)
					array[i] = val[i];
			}
		}
		public int length() {return length;}
		public void free() {
			array = XStaticFixedValue.nulllongArray;
			length = 0;
		}
		public long get(int i) {return array[i];}
		public void set(int i, long val) {array[i] = val;}
		public long[] getArray() {return array;}

		@Override
		public String toString() {
			// TODO: Implement this method
			return Arrays.toString(array);
		}

	}
	public static class doublesFill {
		private double[] array;
		private int length;
		public doublesFill(int len) {
			if (len < 1)
				throw new NumberFormatException("len need > 0");
			array = new double[len];
			length = len;
		}
		//<- 将数据向左移动
		private void left0(double val) {
			for (int i = 0;i < length - 1;i++)
				array[i] = array[i + 1];
			array[length - 1] = val;
		}

		public void left(double val) {
			left0(val);
		}
		public void left(double[] val) {
			if (val.length >= length) {
				for (int i = 0;i < length;i++)
					array[i] = val[val.length - length + i];
			} else {
				int ind = length - val.length;
				for (int i = 0;i < ind;i++)
					array[i] = array[length - ind + i];
				for (int i = 0;i < val.length;i++)
					array[ind + i] = val[i];
			}
		}

		//-> 将数据向右移动
		private void right0(double val) {
			for (int i = length - 1;i >= 1;i--)
				array[i] = array[i - 1];
			array[0] = val;
		}
		public void right(double val) {
			right0(val);
		}
		public void right(double[] val) {
			if (val.length >= length) {
				for (int i = 0;i < length;i++)
					array[i] = val[i];
			} else {
				int ind = val.length;
				for (int i = 0;i < length - val.length;i++) {
					array[(length - 1) - i] = array[(length - 1) - ind - i];
				}	
				for (int i = 0;i < ind;i++)
					array[i] = val[i];
			}
		}
		public int length() {return length;}
		public void free() {
			array = XStaticFixedValue.nulldoubleArray;
			length = 0;
		}
		public double get(int i) {return array[i];}
		public void set(int i, double val) {array[i] = val;}
		public double[] getArray() {return array;}

		@Override
		public String toString() {
			// TODO: Implement this method
			return Arrays.toString(array);
		}
	}
	public static class bytesFill {
		private byte[] array;
		private int length;
		public bytesFill(int len) {
			if (len < 1)
				throw new NumberFormatException("len need > 0");
			array = new byte[len];
			length = len;
		}
		//<- 将数据向左移动
		private void left0(byte val) {
			for (int i = 0;i < length - 1;i++)
				array[i] = array[i + 1];
			array[length - 1] = val;
		}

		public void left(byte val) {
			left0(val);
		}
		public void left(byte[] val) {
			if (val.length >= length) {
				for (int i = 0;i < length;i++)
					array[i] = val[val.length - length + i];
			} else {
				int ind = length - val.length;
				for (int i = 0;i < ind;i++)
					array[i] = array[length - ind + i];
				for (int i = 0;i < val.length;i++)
					array[ind + i] = val[i];
			}
		}

		//-> 将数据向右移动
		private void right0(byte val) {
			for (int i = length - 1;i >= 1;i--)
				array[i] = array[i - 1];
			array[0] = val;
		}
		public void right(byte val) {
			right0(val);
		}
		public void right(byte[] val) {
			if (val.length >= length) {
				for (int i = 0;i < length;i++)
					array[i] = val[i];
			} else {
				int ind = val.length;
				for (int i = 0;i < length - val.length;i++) {
					array[(length - 1) - i] = array[(length - 1) - ind - i];
				}	
				for (int i = 0;i < ind;i++)
					array[i] = val[i];
			}
		}
		public int length() {return length;}
		public void free() {
			array = XStaticFixedValue.nullbyteArray;
			length = 0;
		}
		public byte get(int i) {return array[i];}
		public void set(int i, byte val) {array[i] = val;}
		public byte[] getArray() {return array;}

		@Override
		public String toString() {
			// TODO: Implement this method
			return Arrays.toString(array);
		}
	}
	public static class booleansFill {
		private boolean[] array;
		private int length;
		public booleansFill(int len) {
			if (len < 1)
				throw new NumberFormatException("len need > 0");
			array = new boolean[len];
			length = len;
		}
		//<- 将数据向左移动
		private void left0(boolean val) {
			for (int i = 0;i < length - 1;i++)
				array[i] = array[i + 1];
			array[length - 1] = val;
		}

		public void left(boolean val) {
			left0(val);
		}
		public void left(boolean[] val) {
			if (val.length >= length) {
				for (int i = 0;i < length;i++)
					array[i] = val[val.length - length + i];
			} else {
				int ind = length - val.length;
				for (int i = 0;i < ind;i++)
					array[i] = array[length - ind + i];
				for (int i = 0;i < val.length;i++)
					array[ind + i] = val[i];
			}
		}

		//-> 将数据向右移动
		private void right0(boolean val) {
			for (int i = length - 1;i >= 1;i--)
				array[i] = array[i - 1];
			array[0] = val;
		}
		public void right(boolean val) {
			right0(val);
		}
		public void right(boolean[] val) {
			if (val.length >= length) {
				for (int i = 0;i < length;i++)
					array[i] = val[i];
			} else {
				int ind = val.length;
				for (int i = 0;i < length - val.length;i++) {
					array[(length - 1) - i] = array[(length - 1) - ind - i];
				}	
				for (int i = 0;i < ind;i++)
					array[i] = val[i];
			}
		}
		public int length() {return length;}
		public void free() {
			array = XStaticFixedValue.nullbooleanArray;
			length = 0;
		}
		public boolean get(int i) {return array[i];}
		public void set(int i, boolean val) {array[i] = val;}
		public boolean[] getArray() {return array;}

		@Override
		public String toString() {
			// TODO: Implement this method
			return Arrays.toString(array);
		}
	}
}
