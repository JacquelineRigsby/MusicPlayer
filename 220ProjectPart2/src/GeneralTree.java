import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GeneralTree <T> implements GTInterface <T> {
	
public Node<T> root;

//Constructors
public GeneralTree() {
	root = null;
}

public GeneralTree(T thisData) {
	root = new Node<T>(thisData);
	
}

public GeneralTree(T thisData, GeneralTree<T>[] children) {
	setTree(thisData, children);
}


//GTNode
public class Node<T> {
	private List<Node<T>> children = new ArrayList<Node<T>>();
	private Node<T> parent = null;
	private T data = null;

	    public Node(T data) {
	        this.data = data;
	    }

	    public Node(T data, Node<T> parent) {
	        this.data = data;
	        this.parent = parent;
	    }

	    public List<Node<T>> getChildren() {
	        return children;
	    }

	    public void setParent(Node<T> parent) {
	        parent.addChild(this);
	        this.parent = parent;
	    }

	    public void addChild(T data) {
	        Node<T> child = new Node<T>(data);
	        child.setParent(this);
	        this.children.add(child);
	    }

	    public void addChild(Node<T> child) {
	        child.setParent(this);
	        this.children.add(child);
	    }

	    public T getData() {
	        return this.data;
	    }

	    public void setData(T data) {
	        this.data = data;
	    }

	    public boolean isRoot() {
	        return (this.parent == null);
	    }

	    public boolean isLeaf() {
	        return this.children.size == 0;
	    }

	    public void removeParent() {
	        this.parent = null;
	    }
	}



@Override
public Iterator<T> getLevelOrderIterator() {
	// TODO Auto-generated method stub
	return null;
}



@Override
public void setTree(T rootData) {
	// TODO Auto-generated method stub
	
}



@Override
public void setTree(T rootData, GTInterface<T>[] children) {
	// TODO Auto-generated method stub
	
}



@Override
public T getValue() {
	// TODO Auto-generated method stub
	return null;
}



@Override
public boolean isEmpty() {
	// TODO Auto-generated method stub
	return false;
}



@Override
public boolean isLeaf() {
	// TODO Auto-generated method stub
	return false;
}



@Override
public int getHeight() {
	// TODO Auto-generated method stub
	return 0;
}



@Override
public void Traversal() {
	// TODO Auto-generated method stub
	
}





}
