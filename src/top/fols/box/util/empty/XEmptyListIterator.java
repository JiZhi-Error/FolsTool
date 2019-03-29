package top.fols.box.util.empty;
import java.util.ListIterator;
import top.fols.box.util.XObjects;

public final class XEmptyListIterator implements ListIterator {
	public boolean hasNext() {return false;}
	public Object next() {throw XObjects.notPermittedAccess();}
	public boolean hasPrevious() {return false;}
	public Object previous() {throw XObjects.notPermittedAccess();}
	public int nextIndex() {return -1;}
	public int previousIndex() {return -1;}
	public void remove() {throw XObjects.notPermittedAccess();}
	public void set(Object p1) {throw XObjects.notPermittedAccess();}
	public void add(Object p1) {throw XObjects.notPermittedAccess();}
}
