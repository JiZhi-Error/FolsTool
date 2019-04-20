package top.fols.box.lang.reflect;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import top.fols.box.statics.XStaticBaseType;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.XArrays;
public class XReflect {
	public static Constructor[] getConstructors(Class Cls) {
		if (Cls == null)
			return null;
		return Cls.getConstructors();
	}
	public static Method[] getMethods(Class Cls) {
		if (Cls == null)
			return null;
		return Cls.getMethods();
	}
	public static Field[] getFields(Class Cls) {
		if (Cls == null)
			return null;
		return Cls.getFields();
	}

	public static Class getClass(Object obj) {
		return obj == null ?null: obj.getClass();
	}
	
	public static Method findMethod(Class ClassV, String functionName , Class...classs) { 
		if (classs == null)
			classs = XStaticFixedValue.nullClassArray;
		Method m =  XReflectQuick.defaultInstance.getMethod(ClassV, functionName, classs);
		return m;
	}
	//执行方法 方法名(functionName)  传入参数(object)的Class(classname) 传入参数(object)
	public static Object execStaticMethod(Method method , Object... object) throws InvocationTargetException, IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException {
		return execMethod(null, method, object);
	}
	//传入参数(object) 比 传入Class名称(classname) 多时就取 (0,classname.length)
	public static Object execMethod(Object Instance, Method method , Object... object) throws InvocationTargetException, IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException {
		if (object == null)
			object = XStaticFixedValue.nullObjectArray;
		return method.invoke(Instance, object);
	}
	
	//搜索构造方法 方法名(name) {可选}指定传入参数(object)的Class(classname)
	public static Constructor findConstructor(Object ClassV, Class...classs) {
		return findConstructor(getClass(ClassV), classs);
	}
	public static Constructor findConstructor(Class ClassV, Class...classs) {
		if (classs == null)
			classs = XStaticFixedValue.nullClassArray;
		Constructor  c = XReflectQuick.defaultInstance.getConstructor(ClassV, classs);
		return c;
	}
	//构建 new 传入参数(object) 传入参数(object)的Class(classname)
	public static Object newInstance(Constructor c , Object... object) throws InstantiationException, InvocationTargetException, SecurityException, IllegalAccessException, IllegalArgumentException, NoSuchMethodException {
		if (object == null || object.length == 0)
			return c.newInstance();
		return c.newInstance(object);
	}
	
	//获取变量类型
	public static Class getFieldType(Field idF) {
		return idF.getType();
	}

	//获取变量值 将会打破封装
	public static Object getFieldValue(Class ClassV, Object Instance, String name) throws IllegalAccessException, IllegalArgumentException {
		//获取id属性
		Field idF = XReflectQuick.defaultInstance.getField(ClassV, name);
		return idF.get(Instance);
	}
	public static Object getFieldValue(Object Instance, String name) throws IllegalAccessException, IllegalArgumentException {
		return getFieldValue(Instance.getClass(), Instance, name);
	}
	
	public static void setFieldValue(Object Instance, String name, Object Value) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
		setFieldValue(Instance, XReflectQuick.defaultInstance.getField(Instance.getClass(), name), Value);
	}
	
	public static void setFieldValue(Field field , Object newFieldValue) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
		setFieldValue(null, field, newFieldValue);
	}
	public static void setFieldValue(Object object, Field field , Object newFieldValue) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
		field.set(object, newFieldValue);
	}
	public static void setFieldValue(Class cls, String fieldName, Object newFieldValue) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
		Field field = XReflectQuick.defaultInstance.getField(cls, fieldName);
		setFieldValue(field, newFieldValue);
	}
	
	//获取方法返回类型
	public static Class getMethodReturnType(Method method) {
		return method.getReturnType();
	}
	
	public static String getName(Member object) {
		if (object == null)
			return null;
		return object.getName();
	}
	
	//获取变量的修饰符
	public static int getModifiers(Member idF) {
		//打破封装
		if (idF == null)
			return -1;
		return idF.getModifiers();
	}




	public static String getModifiersStr(int method) {
		return Modifier.toString(method);
	}
	public static String[] getNames(Class[] typearr) {
		String[] type = new String[typearr.length];
		for (int i =0;i < typearr.length;i++)
			type[i] = typearr[i].getName();
		return type;
	}
	
	
	
	
	
	
	
	
	
	//把简单的类地址转换为真实的类地址 Main[] >> [LMain;   String[] >> [Ljava.lang.String; byte[] >> [B
	public static String toAbsClassName(String Addres) {
		//没有这些地址
		if (XStaticBaseType.isBaseClassName(Addres))
			return Addres;
		//判断数组纬度
		int d = XArrays.getDimensionalFromClassCanonicalName(Addres);
		StringBuilder startStr = new StringBuilder();
		for (int i = 0;i < d;i++)
			startStr.append('[');
		int start;
		String F;
		if ((start = Addres.indexOf("[]")) > -1) {
			Addres = Addres.substring(0, start);
			if (Addres.equals("byte"))
				F = "B";
			else if (Addres.equals("char"))
				F = "C";
			else if (Addres.equals("double"))
				F = "D";
			else if (Addres.equals("float"))
				F = "F";
			else if (Addres.equals("int"))
				F = "I";
			else if (Addres.equals("long"))
				F = "J";
			else if (Addres.equals("short"))
				F = "S";
			else if (Addres.equals("boolean"))
				F = "Z";
			else if (Addres.equals("void"))//注意 void[].class是无效的！！
				F = "V";
			else
				F = String.format("L%s;", Addres);
			return startStr.append(F).toString();
		}
		return Addres;
	}
	public static String[] toAbsClassName(String...ClsName) {
		if (ClsName == null || ClsName.length == 0)
			return null;
		String c[] = new String[ClsName.length];
		for (int i = 0;i < ClsName.length;i++)
			c[i] = ClsName[i] == null ?null: toAbsClassName(ClsName[i]);
		return c;
	}
	public static Class<?> forName(String AddresS, boolean initialize, java.lang.ClassLoader loader) throws ClassNotFoundException {
		String Addres = toAbsClassName(AddresS);
		Class c = XStaticBaseType.forName(Addres);
		if (c != null)
			return c;
		return Class.forName(Addres, initialize, loader);
	}
    //根据类名获取Class  byte[]  >> byte[].class ([B)    java.lang.String[] >> [Ljava.lang.String (String[].class);
	public static Class<?> forName(String AddresS) throws ClassNotFoundException {
		String Addres = toAbsClassName(AddresS);
		Class c = XStaticBaseType.forName(Addres);
		if (c != null)
			return c;
		return Class.forName(Addres);
	}
	
}
