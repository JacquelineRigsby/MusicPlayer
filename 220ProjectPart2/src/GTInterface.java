
public interface GTInterface<T> {

public void setTree(T rootData);
public void setTree(T rootData,GTInterface<T>[] children);
T getValue();
boolean isEmpty();
boolean isLeaf();
int getHeight();
void Traversal();
	
}
