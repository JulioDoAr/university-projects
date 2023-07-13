package com.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;

/**
 * Resizable-array and ordered implementation of the {@code List} interface.
 * Implements all optional list operations, and permits all elements, including
 * {@code null}. In addition to implementing the {@code List} interface, this
 * class provides methods to manipulate the size of the array that is used
 * internally to store the list. (This class is roughly equivalent to
 * {@code Vector}, except that it is unsynchronized.)
 *
 * <p>
 * The {@code size}, {@code isEmpty}, {@code get}, {@code set},
 * {@code iterator}, and {@code listIterator} operations run in constant time.
 * The {@code add} operation runs in <i>amortized constant time</i>, that is,
 * adding n elements requires O(n) time. All of the other operations run in
 * linear time (roughly speaking). The constant factor is low compared to that
 * for the {@code LinkedList} implementation.
 *
 * <p>
 * Each {@code ArrayList} instance has a <i>capacity</i>. The capacity is the
 * size of the array used to store the elements in the list. It is always at
 * least as large as the list size. As elements are added to an ArrayList, its
 * capacity grows automatically. The details of the growth policy are not
 * specified beyond the fact that adding an element has constant amortized time
 * cost.
 *
 * <p>
 * An application can increase the capacity of an {@code ArrayList} instance
 * before adding a large number of elements using the {@code ensureCapacity}
 * operation. This may reduce the amount of incremental reallocation.
 *
 * <p>
 * <strong>Note that this implementation is not synchronized.</strong> If
 * multiple threads access an {@code ArrayList} instance concurrently, and at
 * least one of the threads modifies the list structurally, it <i>must</i> be
 * synchronized externally. (A structural modification is any operation that
 * adds or deletes one or more elements, or explicitly resizes the backing
 * array; merely setting the value of an element is not a structural
 * modification.) This is typically accomplished by synchronizing on some object
 * that naturally encapsulates the list.
 *
 * If no such object exists, the list should be "wrapped" using the
 * {@link Collections#synchronizedList Collections.synchronizedList} method.
 * This is best done at creation time, to prevent accidental unsynchronized
 * access to the list:
 * 
 * <pre>
 *   List list = Collections.synchronizedList(new ArrayList(...));
 * </pre>
 *
 * <p id="fail-fast">
 * The iterators returned by this class's {@link #iterator() iterator} and
 * {@link #listIterator(int) listIterator} methods are <em>fail-fast</em>: if
 * the list is structurally modified at any time after the iterator is created,
 * in any way except through the iterator's own {@link ListIterator#remove()
 * remove} or {@link ListIterator#add(Object) add} methods, the iterator will
 * throw a {@link ConcurrentModificationException}. Thus, in the face of
 * concurrent modification, the iterator fails quickly and cleanly, rather than
 * risking arbitrary, non-deterministic behavior at an undetermined time in the
 * future.
 *
 * <p>
 * Note that the fail-fast behavior of an iterator cannot be guaranteed as it
 * is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification. Fail-fast iterators throw
 * {@code ConcurrentModificationException} on a best-effort basis. Therefore, it
 * would be wrong to write a program that depended on this exception for its
 * correctness: <i>the fail-fast behavior of iterators should be used only to
 * detect bugs.</i>
 *
 * @param <E> the type of elements in this list
 *
 */
public class OrderedList<E> extends ArrayList<E> {

	/**
	 * Default Serial Version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Comparator
	 */
	private Comparator<E> comparator;

	/**
	 * Constructs an empty list with an initial capacity of ten.
	 */
	public OrderedList() {
		super();
		comparator = null;
	}

	/**
	 * Constructs an empty list with an initial capacity of ten and a comparator to
	 * order the data.
	 */
	public OrderedList(Comparator<E> comparator) {
		super();
		this.comparator = comparator;
	}

	@Override
	public boolean add(E e) {
		boolean added = super.add(e);
		sort();
		return added;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean added = super.addAll(c);
		sort();
		return added;
	}

	/**
	 * Set the comparator to reorder the list
	 * 
	 * @param comparator The comparator
	 */
	public void setComparator(Comparator<E> comparator) {
		this.comparator = comparator;
		sort();
	}

	/**
	 * Force the list to be sorted
	 */
	public void sort() {
		if (comparator != null)
			Collections.sort(this, comparator);
	}
}
