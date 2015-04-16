package com.goldennode.api.cluster;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.LoggerFactory;

public class ReplicatedMemorySet<E> extends ReplicatedMemoryObject implements Set<E> {
	private static final long serialVersionUID = 1L;
	static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ReplicatedMemorySet.class);
	protected Set<E> innerSet = Collections.synchronizedSet(new HashSet<E>());

	public ReplicatedMemorySet() {
		super();
	}

	public ReplicatedMemorySet(String publicName) {
		super(publicName);
	}

	@Override
	public boolean add(E e) {
		return (boolean) safeOperate(new Operation(getPublicName(), "add", e));
	}

	public boolean _add(E e) {
		return _base_add(e);
	}

	public boolean _base_add(E e) {
		return innerSet.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return (boolean) safeOperate(new Operation(getPublicName(), "remove", o));
	}

	public boolean _remove(Object o) {
		return _base_remove(o);
	}

	public boolean _base_remove(Object o) {
		return innerSet.remove(o);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return (boolean) safeOperate(new Operation(getPublicName(), "addAll", c));
	}

	public boolean _addAll(Collection<? extends E> c) {
		return _base_addAll(c);
	}

	public boolean _base_addAll(Collection<? extends E> c) {
		return innerSet.addAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return (boolean) safeOperate(new Operation(getPublicName(), "retainAll", c));
	}

	public boolean _retainAll(Collection<?> c) {
		return _base_retainAll(c);
	}

	public boolean _base_retainAll(Collection<?> c) {
		return innerSet.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return (boolean) safeOperate(new Operation(getPublicName(), "removeAll", c));
	}

	public boolean _removeAll(Collection<?> c) {
		return _base_removeAll(c);
	}

	public boolean _base_removeAll(Collection<?> c) {
		return innerSet.removeAll(c);
	}

	@Override
	public void clear() {
		safeOperate(new Operation(getPublicName(), "clear"));
	}

	public void _clear() {
		_base_clear();
	}

	public void _base_clear() {
		innerSet.clear();
	}

	@Override
	public int size() {
		return innerSet.size();
	}

	@Override
	public boolean isEmpty() {
		return innerSet.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return innerSet.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return innerSet.iterator();
	}

	@Override
	public Object[] toArray() {
		return innerSet.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return innerSet.toArray(a);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return innerSet.containsAll(c);
	}
}
