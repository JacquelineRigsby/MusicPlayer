import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;




public class Node<T> {

	
	
	
	
	
	@Override
	public String toString() {
		return "Genre:##" + data + ", Artist" + children + ", Songs" + parent + "";
	}

	    private T data = null;
	    //Array List to hold multiple artist/songs/albums for a specific genre
	    private List<Node> children = new ArrayList<>();
	    
	    private Node parent = null;
	    private int numberOfNodes;

	    
	    
//Constructor, this is the root for genre.	    
	    public Node(T data) {
	        this.data = data;
}

/**
 * 	    
 * @param child this will accept 
 */
	    public void addChild(Node child) {
	        child.setParent(this);
	        this.children.add(child);
	    }
/**
 * 
 * @param data holds the data for newChild, which will 
 */
	    public void addChild(T data) {
	        Node<T> newChild = new Node<>(data);
	        this.addChild(newChild);
	    }

	    public void addChildren(List<Node> children) {
	        for(Node t : children) {
	            t.setParent(this);
	        }
	        this.children.addAll(children);
	    }
/**
 * 
 * @return children this will return the children of a specific node
 */
	    public List<Node> getChildren() {
	        return children;
	    }

	    public T getData() {
	        return data;
	    }

	    
	    public void setData(T data) {
	        this.data = data;
	    }

/**
 * 	    
 * @param parent holds the identity of the parent node.
 */
	    private void setParent(Node parent) {
	        this.parent = parent;
	    }

/**
 * 	    
 * @return returns the parent node
 */
	    public Node getParent() {
	        return parent;
	    }

public Iterator<String> iterator() {
	// TODO Auto-generated method stub
	return null;
}
	}
	

