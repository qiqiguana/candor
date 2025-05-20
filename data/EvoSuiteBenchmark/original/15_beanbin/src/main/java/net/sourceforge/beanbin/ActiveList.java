package net.sourceforge.beanbin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sourceforge.beanbin.data.BeanBinDAO;
import net.sourceforge.beanbin.query.Query;

public class ActiveList<E> extends ActiveListSearcher<E> implements List<E>, Serializable {
	private static final long serialVersionUID = -2452810520138406493L;

	private Query query;
	private BeanBinDAO dao;
	private List<Object> data;
	
	public ActiveList() {
		super();
	}
	
	public ActiveList(Class clazz) throws BeanBinException {
		super(clazz);
		this.query = new Query();
		this.dao = BeanBinDAOFactory.getDAO();
		this.data = null;
		setList(this);
	}
	
	protected BeanBinDAO getDAO() {
		return this.dao;
	}
	
	public Query getQuery() {
		return query;
	}
	
	protected void setQuery(Query query) {
		this.query = query;
		this.data = null;
	}
	
	protected void init() {
		if(data == null) {
			try {
				this.data = sendQuery();
			} catch (BeanBinException e) {
				throw new RuntimeException("ActiveList.sendQuery() : " + e.getMessage(), e);
			}		
		}
	}
	
	protected List<Object> sendQuery() throws BeanBinException {
		return dao.search(getClazz(), getQuery());
	}

	public boolean add(E o) {
		init();
		return data.add(o);
	}

	public void add(int index, E element) {
		init();
		data.add(index, element);
	}

	public boolean addAll(Collection<? extends E> c) {
		init();
		return data.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		init();
		return data.addAll(index, c);
	}

	public void clear() {
		init();
		data.clear();
	}

	public boolean contains(Object o) {
		init();
		return data.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		init();
		return data.containsAll(c);
	}

	@SuppressWarnings("unchecked")
	public E get(int index) {
		init();
		if(getQuery().getFetchSize() != -1 && index >= (getQuery().getPosition() + getQuery().getFetchSize())) {
			getQuery().setPosition(getQuery().getPosition() + getQuery().getFetchSize());
			try {
				data.addAll(sendQuery());
			} catch (BeanBinException e) {
				throw new RuntimeException("ActiveList.get(i) : " + e.getMessage(), e);
			}
		}
		return (E)data.get(index);
	}

	public int indexOf(Object o) {
		init();
		return data.indexOf(o);
	}

	public boolean isEmpty() {
		init();
		return data.isEmpty();
	}

	@SuppressWarnings("unchecked")
	public Iterator<E> iterator() {
		init();
//		return null;
		return new ActiveIterator<E>(this);
	}

	public int lastIndexOf(Object o) {
		init();
		return data.lastIndexOf(o);
	}

	@SuppressWarnings("unchecked")
	public ListIterator<E> listIterator() {
		init();
		return (ListIterator<E>)data.listIterator();
	}

	@SuppressWarnings("unchecked")
	public ListIterator<E> listIterator(int index) {
		init();
		return (ListIterator<E>)data.listIterator(index);
	}

	public boolean remove(Object o) {
		init();
		return data.remove(o);
	}

	@SuppressWarnings("unchecked")
	public E remove(int index) {
		init();
		return (E)data.remove(index);
	}

	public boolean removeAll(Collection<?> c) {
		init();
		return data.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		init();
		return data.retainAll(c);
	}

	@SuppressWarnings("unchecked")
	public E set(int index, E element) {
		init();
		return (E)data.set(index, element);
	}

	public int size() {
		try {
			return dao.getSize(getClazz(), getQuery());
		} catch (BeanBinException e) {
			throw new RuntimeException("Invalid Query: " + e.getMessage(), e);			
		}
	}

	@SuppressWarnings("unchecked")
	public List<E> subList(int fromIndex, int toIndex) {
		init();
		List<Object> subList = data.subList(fromIndex, toIndex);
		List<E> list = new ArrayList<E>();
		for(Object o : subList) {
			list.add((E)o);
		}
		return list;
	}

	public Object[] toArray() {
		init();
		return data.toArray();
	}

	public <T> T[] toArray(T[] a) {
		init();
		return data.toArray(a);
	}
	
	public String toString() {
		return data.toString();
	}
}
