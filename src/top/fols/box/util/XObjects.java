package top.fols.box.util;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import top.fols.box.lang.XClassUtils;
import top.fols.box.statics.XStaticBaseType;
import top.fols.box.statics.XStaticFixedValue;
public class XObjects {
	public static <S> List<S> keys(Map<S,?> map) {
		if (map.size() == 0)
			return XStaticFixedValue.nullList;
		List<S> list = new ArrayListUtils<>();
		Set<S> set = map.keySet();
		for (S key:set)
			list.add(key);
		return list;
	}
	
	
	public void clear(StringBuilder buf){
		if(isEmpty(buf))
			return;
		buf.delete(0,buf.length());
	}
	public void clear(StringBuffer buf){
		if(isEmpty(buf))
			return;
		buf.delete(0,buf.length());
	}
	
	
	
	
	public static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }
	public static <T> T requireNonNull(T obj, String errorMessage) {
        if (obj == null)
            throw new NullPointerException(errorMessage);
        return obj;
    }

	public static <T> T requireArray(T obj) {
        if (obj == null || requireNonNull(obj).getClass().isArray() == false)
            throw new RuntimeException((obj == null ?null: obj.getClass().getCanonicalName()) + " cannot cast to array");
        return obj;
    }
	public static <T> T requireArray(T obj, String errorMessage) {
        if (obj == null || requireNonNull(obj).getClass().isArray() == false)
            throw new RuntimeException(errorMessage);
        return obj;
    }

	public static <T> T requireInstanceOf(T obj, Class cls) {
        if (!XClassUtils.isInstance(obj, cls))
            throw new ClassCastException((obj == null ?null: obj.getClass().getCanonicalName()) + " cannot cast to " + cls.getCanonicalName());
        return obj;
    }
	public static <T> T requireInstanceOf(T obj, Class cls, String errorMessage) {

        if (!XClassUtils.isInstance(obj, cls))
            throw new ClassCastException(errorMessage);
        return obj;
    }






	public static RuntimeException notPermittedAccess() {
		return new RuntimeException("cannot operate. this object not permitted to access");
	}




	public static boolean isEquals(Object obj, Object obj2) {
		if (obj == null) {
			if (obj2 == null)
				return true;
			return false;

		} else {
			if (obj.equals(obj2))
				return true;
			return false;
		}
    }
	
	
	
	public static boolean isEmpty(StringBuffer obj) {
		return obj == null || obj.length() == 0;
    }
	public static boolean isEmpty(StringBuilder obj) {
		return obj == null || obj.length() == 0;
    }
	public static boolean isEmpty(Object obj) {
        return obj == null;
    }
	public static boolean isEmpty(Collection obj) {
		return obj == null || obj.size() == 0;
    }
	public static boolean isEmpty(String obj) {
        return obj == null || obj.length() == 0;
    }
	public static boolean isEmptyArray(Object[] obj) {
        return obj == null || obj.length == 0;
    }
	public static boolean isEmpty(long[] obj) {
        return obj == null || obj.length == 0;
    }
	public static boolean isEmpty(int[] obj) {
        return obj == null || obj.length == 0;
    }
	public static boolean isEmpty(short[] obj) {
        return obj == null || obj.length == 0;
    }
	public static boolean isEmpty(byte[] obj) {
        return obj == null || obj.length == 0;
    }
	public static boolean isEmpty(boolean[] obj) {
        return obj == null || obj.length == 0;
    }
	public static boolean isEmpty(double[] obj) {
        return obj == null || obj.length == 0;
    }
	public static boolean isEmpty(float[] obj) {
        return obj == null || obj.length == 0;
    }
	public static boolean isEmpty(char[] obj) {
        return obj == null || obj.length == 0;
    }


	/*
	 toString(char[]);
	 toString(byte[]);
	 toString(byte[],String coding);
	 toString(Object);
	 */
	public static String toString(Object... objArr) {
		if (isEmpty(objArr) || objArr[0] == null)
			return null;
		if (objArr[0] instanceof String)
			return (String) objArr[0];
		else if (objArr[0] instanceof char[])
			return new String((char[])objArr[0]);
		else if (objArr[0] instanceof byte[]) {
			String codeing = (objArr.length <= 1 || !(objArr[1] instanceof String)) ? null : (String)objArr[1];
			return codeing == null ? new String((byte[]) objArr[0]) : new String((byte[]) objArr[0], Charset.forName(codeing));
		} else
			return objArr[0].toString();
    }
	public static String toStringNoNull(Object... objArr) {
		String str = toString(objArr);
		return str == null ?"": str;
    }
	public static char tochar(Object objArr) {
        if (objArr == null)
			return 0;
		if (objArr instanceof Character)
			return ((Character)objArr).charValue();
		throw new ClassCastException(String.format("%s not can cast to array", objArr.getClass().getName()));
    }
	public static boolean toboolean(Object objArr) {
        if (objArr == null)
            return false;
        if (objArr instanceof Boolean)
			return ((Boolean)objArr).booleanValue();
		return Boolean.parseBoolean(objArr.toString().trim());
    }
    public static byte tobyte(Object objArr) {
        if (objArr == null)
			return 0;
		if (objArr instanceof Byte)
			return ((Byte)objArr).byteValue();
		return Byte.parseByte(objArr.toString().trim());
    }
	public static int toint(Object objArr) {
        if (objArr == null)
			return 0;
		if (objArr instanceof Integer)
			return ((Integer)objArr).intValue();
		return Integer.parseInt(objArr.toString().trim());
    }
	public static long tolong(Object objArr) {
        if (objArr == null)
            return 0;
		if (objArr instanceof Long)
			return ((Long)objArr).longValue();
		return Long.parseLong(objArr.toString().trim());
    }
	public static short toshort(Object objArr) {
        if (objArr == null)
			return 0;
		if (objArr instanceof Short)
			return ((Short)objArr).shortValue();
		return Short.parseShort(objArr.toString().trim());
    }
	/**
	 xx.xxx
	 **/
    public static double todouble(Object objArr) {
        if (objArr == null)
			return 0;
		else if (objArr instanceof Double)
			return ((Double)objArr).doubleValue();
		return Double.parseDouble(objArr.toString().trim());
    }
	/**
	 xx.xxx
	 **/
	public static float tofloat(Object objArr) {
        if (objArr == null)
			return 0;
		if (objArr instanceof Float)
			return ((Float)objArr).floatValue();
		return Float.parseFloat(objArr.toString().trim());
    }


	public static byte[] tobyteArray(Object originArray) {
		if (originArray instanceof Collection) {
			Object Arr = ((Collection)originArray).toArray();
			byte[] newArray = tobyteArray(Arr);
			Arr = null;
			return newArray;
		}	
		byte[] newArray = (byte[])XArrays.copyOf(originArray, XStaticBaseType.byte_class);
		originArray = null;
		return newArray;
	}
	public static long[] tolongArray(Object originArray) {
		if (originArray instanceof Collection) {
			Object Arr = ((Collection)originArray).toArray();
			long[] newArray = tolongArray(Arr);
			Arr = null;
			return newArray;
		}	
		long[] newArray = (long[])XArrays.copyOf(originArray, XStaticBaseType.long_class);
		originArray = null;
		return newArray;
	}
	public static double[] todoubleArray(Object originArray) {
		if (originArray instanceof Collection) {
			Object Arr = ((Collection)originArray).toArray();
			double[] newArray = todoubleArray(Arr);
			Arr = null;
			return newArray;
		}	
		double[] newArray = (double[])XArrays.copyOf(originArray, XStaticBaseType.double_class);
		originArray = null;
		return newArray;
	}
	public static char[] tocharArray(Object originArray) {
		if (originArray instanceof Collection) {
			Object Arr = ((Collection)originArray).toArray();
			char[] newArray = tocharArray(Arr);
			Arr = null;
			return newArray;
		}	
		char[] newArray = (char[])XArrays.copyOf(originArray, XStaticBaseType.char_class);
		originArray = null;
		return newArray;
	}
	public static int[] tointArray(Object originArray) {
		if (originArray instanceof Collection) {
			Object Arr = ((Collection)originArray).toArray();
			int[] newArray = tointArray(Arr);
			Arr = null;
			return newArray;
		}	
		int[] newArray = (int[])XArrays.copyOf(originArray, XStaticBaseType.int_class);
		originArray = null;
		return newArray;
	}
	public static boolean[] tobooleanArray(Object originArray) {
		if (originArray instanceof Collection) {
			Object Arr = ((Collection)originArray).toArray();
			boolean[] newArray = tobooleanArray(Arr);
			Arr = null;
			return newArray;
		}	
		boolean[] newArray = (boolean[])XArrays.copyOf(originArray, XStaticBaseType.boolean_class);
		originArray = null;
		return newArray;
	}
	public static float[] tofloatArray(Object originArray) {
		if (originArray instanceof Collection) {
			Object Arr = ((Collection)originArray).toArray();
			float[] newArray = tofloatArray(Arr);
			Arr = null;
			return newArray;
		}	
		float[] newArray = (float[])XArrays.copyOf(originArray, XStaticBaseType.float_class);
		originArray = null;
		return newArray;
	}
	public static short[] toshortArray(Object originArray) {
		if (originArray instanceof Collection) {
			Object Arr = ((Collection)originArray).toArray();
			short[] newArray = toshortArray(Arr);
			Arr = null;
			return newArray;
		}	
		short[] newArray = (short[])XArrays.copyOf(originArray, XStaticBaseType.short_class);
		originArray = null;
		return newArray;
	}
	public static String[] toStringArray(Object originArray) {
		if (originArray instanceof Collection) {
			Object Arr = ((Collection)originArray).toArray();
			String[] newArray = toStringArray(Arr);
			Arr = null;
			return newArray;
		}	
		String[] newArray = (String[])XArrays.copyOf(originArray, XStaticBaseType.String_class);
		originArray = null;
		return newArray;
	}
	public static Object[] toObjectArray(Object originArray) {
		if (originArray instanceof Collection) {
			Object Arr = ((Collection)originArray).toArray();
			Object[] newArray = toObjectArray(Arr);
			Arr = null;
			return newArray;
		}	
		Object[] newArray = (Object[])XArrays.copyOf(originArray, XStaticBaseType.Object_class);
		originArray = null;
		return newArray;
	}


}
