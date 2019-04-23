import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;


//Identifiers for Songs-Artists-Albums
//Kind of Sort
//Adding an XML list of songs to an arrayList

public class Demo {

	public static void main(String[] args) {
		
		
Node<String> root = new Node<>("EDM");
Node<String> child1 = new Node<>("AVICII");
child1.addChild("Album1");
child1.addChild("Album2");

Node<String> child2 = new Node<>("DeadMau5");
child2.addChild("YoMama");

root.addChild(child1);
root.addChild(child2);
root.addChild("Daft Punk");

root.addChildren(Arrays.asList( new Node<>("GrouindIsLava"),new Node<>("GroundIsCandy"),new Node<>("GroundIsPancakes")
));

child1.addChildren( Arrays.asList(new Node <>("Telecommunication"),new Node<>("Stephani")));


/**
for(Node node : root.getChildren()) {
    System.out.println(node.getData());
		}
System.out.println("###############");
for(Node node : child1.getChildren()) {
    System.out.println(node.getData());
		}

	}
*/	


}
}