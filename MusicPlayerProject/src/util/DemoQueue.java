package util;


public class DemoQueue {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Queue<?> newq=new Queue<>(); 
		 FileSystem file = new FileSystem();
		 //Queue d = new Queue();
		 //newq = d;

		 for(Song song: file.getSongs()) {
			 newq.addToBack(song.getTitle());
		 }
		 
	        //newq.pushToFront(one);
	        //newq.pushToBack(one);	        
	       
	        //newq.addToBack(six);
	        //newq.addBehind(one, six);
	        System.out.println(newq.getCount());
	        //newq.clear();
	        //newq.shuffle(newq);
	        System.out.println("Song in front is "+ newq.getFront());
	        System.out.println(" ");
	        //newq.toggleLoop();
	        //newq.enableLoop();
	        
	        System.out.println("Starting song is "+ newq.Start());
	        System.out.println("Next song is "+ newq.Next());
	        System.out.println("Next song is "+ newq.Next());
	        System.out.println("Next song is "+ newq.Next());
	        System.out.println("Next song is "+ newq.Next());
	        System.out.println("Next song is "+ newq.Next());
	        System.out.println("Next song is "+ newq.Next());
	        System.out.println("Next song is "+ newq.Next());
	        System.out.println("Next song is "+ newq.Next());
	        System.out.println("Next song is "+ newq.Next());
	        System.out.println("Next song is "+ newq.Next());
	        System.out.println("Next song is "+ newq.Next());
	        System.out.println("Next song is "+ newq.Next());
	        System.out.println("Previous song is "+ newq.Prev());
	        System.out.println("Previous song is "+ newq.Prev());
	        System.out.println("Previous song is "+ newq.Prev());
	        System.out.println(" "); 
	        System.out.println("Dequeued song is "+ newq.dequeue()); 
	        System.out.println("Dequeued song is "+ newq.dequeue()); 
	        System.out.println("Dequeued song is "+ newq.dequeue()); 
	        System.out.println("Dequeued song is "+ newq.dequeue());
	        System.out.println("Dequeued song is "+ newq.dequeue()); 
	        System.out.println("Dequeued song is "+ newq.dequeue()); 
	        System.out.println("Dequeued song is "+ newq.dequeue()); 
	        
	        //System.out.println("Dequeued song is "+ newq.dequeue(one));
	        
	        
	        
	        System.out.println(newq.getCount());

	}

}
