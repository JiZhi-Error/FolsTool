package top.fols.box.util;
import java.util.Map;
import java.util.Set;
public class XMapKeyCheck {
	public XMapKeyCheck(Object[] array) {
		putAll(array);
		array = null;
	}
	public XMapKeyCheck(Object array) {
		if (XObjects.requireArray(array) == null)
			return;
		putAll(array.getClass() == Object[].class ?(Object[])array: XObjects.toObjectArray(array));
	}
	public XMapKeyCheck(Map map) {
		Set set = map.keySet();
		for (Object i:set)
			Hash.put(i, nullvalue);
	}
	public XMapKeyCheck() {
		super();
	}


	private Map Hash = new HashMapUtils9<>();
	private Object nullvalue = new Object();
	public boolean contains(Object o) {
		return Hash.containsKey(o);
	}
	public void clear() {
		Hash.clear();
	}
	public int size() {
		return Hash.size();
	}
	public void put(Object Key) {
		Hash.put(Key, nullvalue);
	}
	public void putAll(Object... array) {
		if (array == null)
			return;
		for (Object Key:array)
			Hash.put(Key, nullvalue);
	}
	public void putAll(Map map) {
		if (map == null)
			return;
		Hash.putAll(map);
	}
	public Set keySet() {
		return Hash.keySet();
	}



	public boolean containsAll(Object... obj) {
		for (Object i:obj)
			if (!Hash.containsKey(i))
				return false;
		return true;
	}


	public void remove(Object key) {
		Hash.remove(key);
	}
	public void removeAll(Object... obj) {
		for (Object i:obj)
			Hash.remove(i);
	}

	@Override
	public String toString() {
		// TODO: Implement this method
		return Hash.toString();
	}
}
