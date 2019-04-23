package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

public class ReadXMLTest {
	
	private File directory;
	private static final String ID = "id";
	private static final String TITLE = "title";
    private static final String ARTIST = "artist";
    private static final String ALBUM = "album";
    private static final String LENGTH = "length";
    private static final String LOCATION = "location";
    
    private static ArrayList<Song> songs;
    

	public static void main(String[] args) throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
        FileInputStream is = new FileInputStream(new File("lib/library.xml"));
        XMLStreamReader reader = factory.createXMLStreamReader(is, "UTF-8");
        
        String element = "";
		int id = -1;
		String title = null;
        String artist = null;
        String album = null;
        Duration length = null;
        String location = null;
        
		while(reader.hasNext()) {
            reader.next();

            if (reader.isWhiteSpace()) {
                continue;
            } else if (reader.isStartElement()) {
                element = reader.getName().getLocalPart();
                System.out.println(element);
            } else if (reader.isCharacters()) {
                String value = reader.getText();
               
                switch (element) {
                case ID:
                    id = Integer.parseInt(value);
                    break;
                case TITLE:
                    title = value;
                    break;
                case ARTIST:
                    artist = value;
                    break;
                case ALBUM:
                    album = value;
                    break;
                case LENGTH:
                    length = Duration.ofSeconds(Long.parseLong(value));
                    break;
                case LOCATION:
                    location = value;
                    break;
            } // End switch
            } else if (reader.isEndElement() && reader.getName().getLocalPart().equals("album")) {
            songs.add(new Song(id, title, artist, album, length, location));
           
		} else if (reader.isEndElement() && reader.getName().getLocalPart().equals("songs")) {

            reader.close();
            break;
        }
    } // End while

    reader.close();

		
	} 

}
