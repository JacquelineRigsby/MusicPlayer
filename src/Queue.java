import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Queue<T> implements QueueInterface<T>
{
		
	    Node front, back, current; 
	    int count = 0;
	    boolean enableLoop;
	    
	    //Keeping track of the amount of songs
	    public Queue() { 
	        this.front = this.back = null; 
	    } 
	    
	    //Playing the queue
	    public String Start() {
	    	current = front;
	    	return current.songID;
	    }
	       //Going to the next song in the queue
	    public String Next() {
	    	if(current.next != null) 
	    		{
	    		current = current.next;
	    		return current.songID; 
	    		}
	    	else if (current.next == null && enableLoop == true) 
	    		{
	    		this.Loop();
	    		return Start();
	    		}
	    	else{
	    	return null;
	    		}
	    }
	    	//Going to the previous song in the queue
	    public String Prev() {
	    	if(current.prev != null) {
	    		current = current.prev;
	    		return current.songID;
	    	}
	    	return null;
	    }
	    //Enabling and disabling looping
	    public boolean toggleLoop() {
	    	if (enableLoop == true) {
	    		enableLoop = false;
	    		return enableLoop;
	    	}
	    	else
	    	enableLoop = true;
	    	return enableLoop;
	    }
	    
	    public void Loop() {
	    		current = front;
	    		current.prev = back;
	    }
	    
	    //Adding a song to the back of the queue
	    public void addToBack(String songID) 
	    { 
	        Node temp = new Node(songID); 
	       
	        if (back == null) 
	        { 
	           front = back = temp; 
	           count++;
	           return; 
	        } 
	        else
	        		temp.setPrev(back);
	       	        back.next = temp; 
	       	        back = temp; 
	       	        count++;
	       	        
	    } 
	    
	    //Adding a song to the beginning of the queue
	    public void addToFront(String songID) {
	    	Node temp = new Node(songID);
	    	Node temp2 = new Node(null);
	    	
	    	if(front == null)
	    	{
	    		front = back = temp;
	    		count++;
	    		return;
	    	}
	    	else
	    			front.setPrev(temp);
	    			temp2 = front;
	    			temp2.setPrev(temp);
	    			front = temp;
	    			front.setsongID(temp.songID);
	    			front.setNext(temp2);
	    			count++;
	    	
	    }
	    
	    //For getting the amount of songs in the queue
	    public int getCount() {
	    	return count;
	    }
	    
	    //For showing the first song to be played in the queue
	    public String getFront() {
	    	String frontSong = null;
	    	
	    	if (!isEmpty()) {
	    		frontSong = front.getsongID();
	    		return frontSong;
	    	}
			return frontSong;
	    }
	       
	    //For removing the first song from the queue
	    public String dequeue() 
	    { 
	        String temp;
			if (front == null) {
	        	back = null;
	           return null; 
	        }
	        else
	        temp = front.songID; 
	        front = front.next; 
	        	count--;
	        return temp; 
	    } 
	    
	    public String dequeueFromBack() {
	    	String temp;
	    	if(front == null) {
	    		back = null;
	    		return null;
	    	}
	    	else
	    	temp = back.songID;
	    	back = back.prev;
	    		count--;
	    	return temp;
	    }
	    
	    //Dequeueing a specific song
	    public String dequeue(String songID) 
	    { 
	    	Node tempNode = new Node(null);
	    	String temp = songID; 
	        if (front == null) {
	           return null; 
	           }
	        front = front.next; 
	        if (front == null) {
	            back = null; 
	        	return null; }
	        tempNode = front;
	        if (tempNode.songID == temp)
	        {
	        	temp = tempNode.songID;
	        	dequeue();
	        }
	        else if (tempNode.songID != temp) {
	        	tempNode = tempNode.next;
	        }
	        count--;
	        return temp; 
	    } 
	    
	    //Uses dequeue and addToFront to add a specific song to the front
	    public void pushToFront(String songID) {
	    	Node temp = new Node(null);
	    	temp.songID = dequeue(songID);
	    	addToFront(temp.songID);
	    }
	    
	    //Uses dequeue and addToBack to add a specific song to the back
	    public void pushToBack(String songID) {
	    	Node temp = new Node(null);
	    	temp.songID = dequeue(songID);
	    	addToBack(temp.songID);
	    }
	    
	    //Checking if the queue is empty
	    public boolean isEmpty() {
	    	return (count == 0);
	    }
	    
	    //Clearing the song queue
	    public void clear() {
	    	front = null;
	    	back = null;
	    	count = 0;
	    }
	    
	    //Shuffling the queue
	    public Queue<String> shuffle(Queue newq) {
	    		Node cycler = front;
	    		Queue<String> tempq = new Queue<String>();
	    		int count2 = count;
						tempq.addToFront(cycler.songID);
	    				newq.dequeue(cycler.songID);
	    				
	    						while(cycler.next != null) {
	    							cycler = cycler.next;
	    							
	    						Random rand1 = new Random();
    							int m = rand1.nextInt(count2);

    									if(m<count2/2) {
    										tempq.addToBack(cycler.songID);
    									}
    									if(m>=count2/2) {
    										tempq.addToFront(cycler.songID);
    									}

    									newq.dequeue(cycler.songID);
    									count++;
    									
	    						}
	    						newq = tempq;
	    						front = tempq.front;
	    						back = tempq.back;
	    						return newq;
	    							
	    						
	    					}

	    					
	    
	  	    				
	    
	    //Adding a song behind another song in the queue
	    public Node addBehind(String songIDTarget, String songID) {
	    	Node insert = new Node(null);
	    	Node cycler = new Node(null);
	    	ArrayList<String> temp = new ArrayList<String>();
	    	java.util.Iterator<String> iter = temp.iterator();
	    	cycler = front;
	    		while (cycler.songID != songIDTarget) {
	    			cycler = cycler.next;
	    		}
	    		if (cycler.songID == songIDTarget && cycler.next != null) {
	    			cycler = cycler.next;
	    			cycler.songID = songIDTarget;
	    			while (cycler.next != null) {
	    				String temp2 = dequeueFromBack();
	    				temp.add(temp2);
	    			}
	    			while (iter.hasNext()) {
	    				String temp3 = iter.next();
	    				addToBack(temp3);
	    				iter.remove();
	    			}
	    			return cycler;
	    			
	    		}
	    		return null;
	    		
	    			/*
	    			 insert.songID = songID;
	    			 
	    			cycler = insert;
	    			cycler = cycler.next;
	    			//cycler.next.songID = cycler.songID;
	    			//cycler = cycler.next;
	    			//cycler = cycler.next;

	    			while(cycler.next != null) {
	    					temp = cycler;
	    					cycler.songID = cycler.next.songID;
	    					cycler = cycler.next;
	    					//insert = cycler.next.next;
	     				}
	    			
	    				if (cycler.next == null)
	    					addToBack(cycler.songID);
	    					//temp.songID = cycler.songID;
	    					//cycler.prev.songID = temp.songID;
	        		
	    					return insert;
	    		}
	    		else 
	    			return null; */
	    		
	    		
	    }
	    }

	    
	    
	    //Node class to store song names and next and previous songs
	     class Node {
		    String songID;
		    Node next; 
		    Node prev;
		      
		    public Node(String songID) { 
		        this.songID = songID; 
		        this.next = null; 
		        this.prev = null;
		    }

			public String getsongID() {
				return songID;
			}

			public void setsongID(String songID) {
				this.songID = songID;
			}

			public Node getNext() {
				return next;
			}

			public void setNext(Node next) {
				this.next = next;
			}

			public Node getPrev() {
				return prev;
			}

			public void setPrev(Node prev) {
				this.prev = prev;
			} 
		    
	    

	     
		  
	} 