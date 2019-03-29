package top.fols.box.lang;

public class XObject <T extends Object> {
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
}
