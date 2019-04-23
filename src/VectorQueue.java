import java.util.Vector;

import Queue.Node;

public class VectorQueue<T> implements QueueInterface<T>{


    private Vector<T> queue;
    public VectorQueue() { 
    	queue = new Vector<T>();
    }
    public VectorQueue(int initialCapacity) {
    	queue = new Vector<T>(initialCapacity);
    }
	@Override
	public void addToBack(T songName) {
		queue.add(songName);
	}

	@Override
	public void addToFront(String songName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T getFront() {
		T front = null;
		if (!isEmpty()) {
			front = queue.get(0);
		}
		return front;
	}
	
	public T dequeue() {
		T front = null;
		if (!isEmpty()) {
			front = queue.remove(0);
		}
		return front;
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public void clear() {
		queue.clear();
	}

}
