package top.fols.box.util.empty;
import java.util.Iterator;
import top.fols.box.util.XObjects;
public final class XEmptyIterator implements Iterator {
	public boolean hasNext() {return false;}
	public Object next() {throw XObjects.notPermittedAccess();}
	public void remove() {throw XObjects.notPermittedAccess();}
}
