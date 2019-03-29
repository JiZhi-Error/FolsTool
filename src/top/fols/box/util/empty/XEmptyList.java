package top.fols.box.util.empty;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import top.fols.box.statics.XStaticFixedValue;
import top.fols.box.util.XObjects;
import top.fols.box.util.ArrayListUtils;
public final class XEmptyList extends AbstractList implements List,RandomAccess, Cloneable, java.io.Serializable {
	private static final long serialVersionUID = 784920843L;

	public int size() {return 0;}
	public boolean isEmpty() {return true;}
	public boolean contains(Object p1) {return false;}
	public Iterator iterator() {return XStaticFixedValue.nullIterator;}
	public Object[] toArray() {return XStaticFixedValue.nullObjectArray;}
	public Object[] toArray(Object[] p1) {return p1;}
	public boolean add(Object p1) {throw XObjects.notPermittedAccess();}
	public boolean remove(Object p1) {throw XObjects.notPermittedAccess();}
	public boolean containsAll(Collection p1) {return p1.isEmpty();}
	public boolean addAll(Collection p1) {throw XObjects.notPermittedAccess();}
	public boolean addAll(int p1, Collection p2) {throw XObjects.notPermittedAccess();}
	public boolean removeAll(Collection p1) {throw XObjects.notPermittedAccess();}
	public boolean retainAll(Collection p1) {throw XObjects.notPermittedAccess();}
	public void clear() {}
	public Object get(int p1) {throw XObjects.notPermittedAccess();}
	public Object set(int p1, Object p2) {throw XObjects.notPermittedAccess();}
	public void add(int p1, Object p2) {throw XObjects.notPermittedAccess();}
	public Object remove(int p1) {throw XObjects.notPermittedAccess();}
	public int indexOf(Object p1) {return -1;}
	public int lastIndexOf(Object p1) {return -1;}
	public ListIterator listIterator() {return XStaticFixedValue.nullListIterator;}
	public ListIterator listIterator(int p1) {return XStaticFixedValue.nullListIterator;}
	public List subList(int p1, int p2) {return XStaticFixedValue.nullList;}
}
