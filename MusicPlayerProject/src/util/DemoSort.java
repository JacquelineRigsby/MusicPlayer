package util;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;

public class DemoSort {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		
		LinkedList <String> sorter = new LinkedList <String>();
		
			
		sorter.add("Zebra");
		sorter.add("Abble");
		sorter.add("Xjgowa");
		sorter.add("Pokemon");
		sorter.add("Fuckass");
		sorter.add("HolyCow");
		sorter.add("Yong Money");
	
	Object[] array = sorter.toArray();
	
	System.out.println(Arrays.toString(array));
	System.out.println("");

		
	
	int arrayLength = array.length;
    for (int i = 0; i < arrayLength; i++) {
        System.out.println(array[i]);
    }
    
    System.out.println("###########");
    sortSongs(array);
    int Length = array.length;
    for (int i = 0; i < Length; i++) {
    	System.out.println(array[i]);
    }  
       // Arrays.sort(array);
}
	
	public static void sortSongs( Object[]  array)
    {
          int j;
          boolean flag = true;  // will determine when the sort is finished
          String temp;

          while ( flag )
          {
                flag = false;
                for ( j = 0;  j < array.length - 1;  j++ )
                {
                        if ( ((String) array [j]).compareToIgnoreCase((String) array[j+1])>0)
                        {                                             // ascending sort
                                    temp=(String) array[j];
                                    array [j] = array[j+1];     // swapping
                                    array [j+1] = temp; 
                                    flag = true;
                         }
                        
                        }
                }
          
          
          }






}

	
	


