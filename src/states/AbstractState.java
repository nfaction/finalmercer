package states;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import engine.World;
import entities.Entities;

public abstract class AbstractState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7552510714241154656L;
	public static ArrayList<Float> x = new ArrayList<Float>();
	public static ArrayList<Float> y = new ArrayList<Float>();

	public AbstractState() {
	}
	
	public boolean add(Entities e){
		return getList().add(e);
	}
	
	public void add(int index, Entities element) {
		 getList().add(index, element);
	}
	public void clear() {
		getList().clear();
	}
	public boolean contains(Object o) {
		return getList().contains(o);
	}
	public Entities get(int index) {
		return getList().get(index);
	}
	public int indexOf(Object o) {
		return getList().indexOf(o);
	}
	public boolean isEmpty() {
		return getList().isEmpty();
	}
	public Iterator<Entities> iterator() {
		return getList().iterator();
	}
	public Entities remove(int i){
		return getList().remove(i);
	}
	public boolean remove(Object o) {
		return getList().remove(o);
	}
	public int size() {
		return getList().size();
	}
	
	public abstract ArrayList<Entities> getList();
	
	public abstract void set(AbstractState list) ;
	
	public abstract void reset(World world);
	
}
