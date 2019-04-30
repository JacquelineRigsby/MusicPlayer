package util;


public interface QueueInterface<T> {
	
	
public String Start();
public String Next();
public String Prev();
public void Loop();
public boolean toggleLoop();
public void addToBack(String songID);
public void addToFront(String songID);
public void pushToFront(String songID);
public void pushToBack(String songID);
public String getFront();
public boolean isEmpty();
public void clear();
public int getCount();
public String dequeue();
public String dequeue(String songID);
public String dequeueFromBack();
public Node addBehind(String songIDTarget, String songID);
public Queue shuffle(Queue newq);

}