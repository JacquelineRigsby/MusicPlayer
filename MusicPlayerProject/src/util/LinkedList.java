package util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LinkedList <T extends Comparable<T>> {
	
private Node <T>firstNode;
private  int numberOfEntries;
private Node <T> sorted;


public LinkedList()
{ 
	firstNode = null;
	numberOfEntries = 0; 
}

public boolean add(T newEntry) {
	 Node newNode = new Node(newEntry);
	 newNode.next = firstNode;
	 
	 firstNode = newNode;
	 numberOfEntries++;
	return true;
}

@SuppressWarnings("unchecked")
public T[] sortString(String[] inputString) 
{ 
    // convert input string to char array 
    T[] tempArray = (T[]) new Object[inputString.length];
      
    // sort tempArray 
    Arrays.sort(tempArray); 
      
    // return new sorted string 
    return (T[]) tempArray; 
} 
/**
public void add(T newEntry) {
	
	if(firstNode == null) 
	{
		firstNode = new Node<T>(newEntry);
	}else 
	{
		Node<T> temp = new Node<T>(newEntry);
		Node<T> current = firstNode;
		
		if(current != null) 
		{
			while(current.getNext()!=null) 
			{
				current = current.getNext();
			}
		}
		
	}
	numberOfEntries++;
}
 * @param strings 
*/
public T[] toArray(String[] strings) {
	
	@SuppressWarnings("unchecked")
	T[] result = (T[])new Object[numberOfEntries];
	
	int index = 0;
	Node <T> currentNode = firstNode;
	
	while((index < numberOfEntries)&&(currentNode !=null)) {
		
		result[index] = currentNode.data;
		index++;
		currentNode = currentNode.next;
		
	}
	return result;	
}



public String toString() {
	
	String output = "";
	
	if (firstNode != null) {
		Node <T> current = firstNode.getNext();
		while(current !=null ){
			
			output = current.getData().toString();
			current = current.getNext();
		}
		return output;
}
	return output;
}





public int numOfElements() {
	return numberOfEntries;
}

public boolean isEmpty() {
	return firstNode == null;
}

@SuppressWarnings("hiding")
private class Node<T>{
	    private T data;
	    private Node <T> next;


	    	    
	    
	   private Node(T data)
	    {
	        this(data,null);
	        
	    }
	    
	    
	    private Node(T dataPortion,Node nextNode) {
	    	data = dataPortion;
	    	next = nextNode;
	    	
	    }
	    
	    public T getData()
	    {
	        return data;
	    }
	    
	    @SuppressWarnings("unused")
		public void setData(T newdata) {
	    	data = newdata;
	    }
	    public Node<T>getNext()
	    {
	        return next;
	    }

	    @SuppressWarnings("unused")
		public void setNext(Node<T> newEntry)
	    {
	        next = newEntry;
	    }
	    
	    
	    	}
	    }








