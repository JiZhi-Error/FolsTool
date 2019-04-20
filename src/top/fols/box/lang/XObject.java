package top.fols.box.lang;
import top.fols.box.io.interfaces.XReleaseBufferable;

/*
 * Save the object
 */
public class XObject <T extends Object> implements XReleaseBufferable {

	@Override
	public void releaseBuffer() {
		// TODO: Implement this method
		this.obj = null;
	}
	private T obj;

	public XObject() {
		super();
	}
	public XObject(T o) {
		this.obj = o;
	}
	public T get() {
		return this.obj;
	}
	public void set(T newo) {
		this.obj = newo;
	}




	public int objHashCode() {
		// TODO: Implement this method
		return this.obj.hashCode();
	}
	public boolean objEquals(Object o) {
		// TODO: Implement this method
		return this.obj == null ?(o == null): (this.obj.equals(o));
	}
	public String objToString() {
		// TODO: Implement this method
		return  this.obj == null ?"null": this.obj.toString();
	}
}
