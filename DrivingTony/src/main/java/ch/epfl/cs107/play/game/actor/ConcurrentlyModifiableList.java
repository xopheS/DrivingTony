package ch.epfl.cs107.play.game.actor;

import java.util.ArrayList;
import java.util.Iterator;

//Cette clase generique permet de modifier une liste pendant une iteration sur elle-meme
public class ConcurrentlyModifiableList<T> implements Iterable<T> {
	ArrayList<T> list = new ArrayList<T>();
	ArrayList<T> toRemove = new ArrayList<T>();
	ArrayList<T> toAdd = new ArrayList<T>();
	
	public void update(float deltaTime) {
		for(T t : list) {
			((Actor) t).update(deltaTime);
		}
		
		for(T t : toAdd) {
			list.add(t);
		}
		toAdd.clear();
		
		for(T t : toRemove) {
			list.remove(t);
		}
		toRemove.clear();
	}
	
	public void add(T t) {
		toAdd.add(t);
	}
	
	public void remove(T t) {
		toRemove.add(t);
	}
	
	public void clear() {
		list.clear();
	}
	
	public int length() {
		return list.size();
	}
	
	public T get(int index) {
		return list.get(index);
	}
	
	public ArrayList<T> getList() {
		return list;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
