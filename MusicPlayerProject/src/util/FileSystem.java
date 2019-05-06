package util;

import java.io.*;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileSystem {
	
	private static String path;
	private static File directory;
	private static final String ID = "id";
	private static final String TITLE = "title";
    private static final String ARTIST = "artist";
    private static final String ALBUM = "album";
    private static final String LENGTH = "length";
    private static final String LOCATION = "location";
    
    
    private static ArrayList<Song> songs;
    private static ArrayList<Artist> artists;
    private static ArrayList<Album> albums;
	
    
    //This will get the default directory. checks if a path is already created, else gets that path. 
	public String getDefaultDirectory() throws Exception {
		String result = null;
		if(path != null) {
			result = path;
		}
		//Uses Java's built in file chooser to get the directory. 
		else {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        int returnVal = chooser.showOpenDialog(null);
	        if(returnVal == JFileChooser.CANCEL_OPTION) {
	        	JOptionPane.showMessageDialog(null, "You must set a default directory to continue.");
	        	System.exit(0);
	        }
	        directory = chooser.getSelectedFile();
	        result = chooser.getSelectedFile().getPath();
	        path = result;
		}

        return result;
        //System.out.println(chooser.getSelectedFile().getName());
	}
	//This traverses through the file to find the directory. 
	public static boolean isDefaultDirectory() throws Exception {
        File file = new File("lib/library.xml");
        boolean found = false;
        
        //Checks if file exists. 
        if(file.exists()) {
        	XMLInputFactory factory = XMLInputFactory.newInstance();
    		FileInputStream is = new FileInputStream(new File("lib/library.xml"));
    		XMLStreamReader reader = factory.createXMLStreamReader(is, "UTF-8");
    		
    		String element; 
    		
    		//This is very similar to the getMusic() method in the Main class. 
    		//This traverses through the library file and tries to find a node that has a location. 
    		String temppath = "";
    		 try {
    			while(reader.hasNext() && !found) {
    			     reader.next();
    			     if (reader.isWhiteSpace()) {
    			         continue;
    			     } else if (reader.isStartElement()) {
    			         element = reader.getName().getLocalPart();
    			         
    			         if(element.equals("path")) {
    			        	temppath = element;

    			         }
    			     } else if(reader.isCharacters() && temppath.equals("path")) {
    			    	 
    			    		 path=reader.getText();
    			    		 
    			    		 found = true;
    			     }
    			     
    			 }
    		} catch (XMLStreamException e) {
    			
    		}
        }
        
		return found;	
	}
	/*
	 * This creates the general tree used for the library. 
	 * It will get all the songs in the directory chosen, then create a level of just artists
	 * Then all the artists will have their own albums
	 * And then each album will have their own songs.  
	 */
	public int getLibrary(File path, Document doc, Element songs, int i) {
		
		//Using temp arraylists to make sure every item from the directory is chosen. 
		ArrayList<String> artistlist = new ArrayList<>();
		ArrayList<String> albumlist = new ArrayList<>();
		ArrayList<String> songlist = new ArrayList<>();
		File[] files = path.listFiles();
		Element id;
        Element title;
        Element song = null;
        Element artist = null;
        Element album = null;
        Element length;
        Element location;
		 for (File file : files) {
			 if (file.isFile()) {
				try {
				
				
				AudioFile audioFile = AudioFileIO.read(file);
				
                 Tag tag = audioFile.getTag();
                 AudioHeader header = audioFile.getAudioHeader();
                 
                 //Checks if the artist is already part of the tree. 
                 if(!artistlist.contains(tag.getFirst(FieldKey.ARTIST))) {
                	 artistlist.add(tag.getFirst(FieldKey.ARTIST));
                	 
                	 artist = doc.createElement("artist");
                	 artist.setTextContent(tag.getFirst(FieldKey.ARTIST));
                     
                     songs.appendChild(artist);
                     //Checks if the artist has an album. 
                     if(!albumlist.contains(tag.getFirst(FieldKey.ALBUM))) {
                    	 albumlist.add(tag.getFirst(FieldKey.ALBUM));
                    		 
                    	 album = doc.createElement("album");
                    	 album.setTextContent(tag.getFirst(FieldKey.ALBUM));
                    	 
                    	 artist.appendChild(album);
                    	 
                    	 //Checks if the album has a song.  
                    	 if(!songlist.contains(Paths.get(file.getAbsolutePath()).toString())) {
                    		 songlist.add(Paths.get(file.getAbsolutePath()).toString());
                    		 
                    		 song = doc.createElement("song");
                    		 album.appendChild(song);
                    		 
                    		 id = doc.createElement("id");
                             title = doc.createElement("title");
                             length = doc.createElement("length");
                             location = doc.createElement("location");
                             
                             id.setTextContent(Integer.toString(i++));
                             title.setTextContent(tag.getFirst(FieldKey.TITLE));
                             length.setTextContent(Integer.toString(header.getTrackLength()));
                             location.setTextContent(Paths.get(file.getAbsolutePath()).toString());
                             
                             song.appendChild(id);
                             song.appendChild(title);
                             song.appendChild(length);
                             song.appendChild(location);
                    	 }
                     }
                     
                 } 
                 //Checks if the artist has multiple albums. 
                 else if(!albumlist.contains(tag.getFirst(FieldKey.ALBUM)) && artistlist.contains(tag.getFirst(FieldKey.ARTIST))) {
                	 albumlist.add(tag.getFirst(FieldKey.ALBUM));
                	 
                		 
                	 album = doc.createElement("album");
                	 album.setTextContent(tag.getFirst(FieldKey.ALBUM));
                	 
                	 artist.appendChild(album);
                	 //Checks if the new album has multiple songs. 
                	 if(!songlist.contains(Paths.get(file.getAbsolutePath()).toString())) {
                		 songlist.add(Paths.get(file.getAbsolutePath()).toString());
                		 
                		 song = doc.createElement("song");
                		 album.appendChild(song);
                		 
                		 id = doc.createElement("id");
                         title = doc.createElement("title");
                         length = doc.createElement("length");
                         location = doc.createElement("location");
                         
                         id.setTextContent(Integer.toString(i++));
                         title.setTextContent(tag.getFirst(FieldKey.TITLE));
                         length.setTextContent(Integer.toString(header.getTrackLength()));
                         location.setTextContent(Paths.get(file.getAbsolutePath()).toString());
                         
                         song.appendChild(id);
                         song.appendChild(title);
                         song.appendChild(length);
                         song.appendChild(location);
                	 }
                	 //Checks if song is logged. 
                 } else if(!songlist.contains(Paths.get(file.getAbsolutePath()).toString()) && albumlist.contains(tag.getFirst(FieldKey.ALBUM))){
                	 songlist.add(Paths.get(file.getAbsolutePath()).toString());
            		 
            		 song = doc.createElement("song");
            		 album.appendChild(song);
            		 
            		 id = doc.createElement("id");
                     title = doc.createElement("title");
                     length = doc.createElement("length");
                     location = doc.createElement("location");
                     
                     id.setTextContent(Integer.toString(i++));
                     title.setTextContent(tag.getFirst(FieldKey.TITLE));
                     length.setTextContent(Integer.toString(header.getTrackLength()));
                     location.setTextContent(Paths.get(file.getAbsolutePath()).toString());
                     
                     song.appendChild(id);
                     song.appendChild(title);
                     song.appendChild(length);
                     song.appendChild(location);
                 }
                 
               
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            } 
			 //if the directory has a folder, it will recursively call the method again. 
			 else if(file.isDirectory()) {
	            	
   	
			 i = getLibrary(file, doc, songs, i);
		 }
	          
		
	}
		 
		 return i;
}
	//This creates the base XML file and adds root notes. 
	public void createLibrary(String path) throws Exception {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element library = doc.createElement("library");
        Element musicpath = doc.createElement("path");
        Element songs = doc.createElement("songs");
        Element playlists = doc.createElement("playlists");
        Element nowPlayingList = doc.createElement("nowPlayingList");
		
        doc.appendChild(library);
        library.appendChild(songs);
        library.appendChild(playlists);
        library.appendChild(nowPlayingList);
        
        musicpath.setTextContent(path.toString());
        library.appendChild(musicpath);
        
        int id = 0;
        File directory = new File(Paths.get(path).toUri());
        getLibrary(directory, doc, songs, id);
  
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        File xmlFile = new File("lib/library.xml");

        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
		
		
		
	}
	
	//If a new song is detected, it will add the data of it to the XML file and the general songs arraylist. 
	public void updateSongs() {
        try {
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
                } else if (reader.isEndElement() && reader.getName().getLocalPart().equals("song")) {
                songs.add(new Song(id, title, artist, album, length, location));
                id = -1;
                title = null;
                artist = null;
                album = null;
                length = null;
                location = null;
			} else if (reader.isEndElement() && reader.getName().getLocalPart().equals("songs")) {

                reader.close();
                break;
            }
        } // End while

        reader.close();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Gets song based on ID. 
	private Song getSong(int id) {
        if (songs == null) {
            getSongs();
        }
        return songs.get(id);
    }
	//gets a list of songs. 
    public ObservableList<Song> getSongs() {
        // If the observable list of songs has not been initialized.
        if (songs == null) {
            songs = new ArrayList<>();
            // Updates the songs array list.
            updateSongs();
        }
        return FXCollections.observableArrayList(songs);
    }
    
    //Gets songs based on Title 
	public Song getSong(String title) {
        if (songs == null) {
            getSongs();
        }
        return songs.stream().filter(song -> title.equals(song.getTitle())).findFirst().get();
    }
	
	//Gets location of selected song. 
	public Song getSongLocation(String location) {
		if (songs == null) {
			getSongs();
		}
		return songs.stream().filter(song -> location.equals(song.getLocation())).findFirst().get();
	}
	

	public File getDirectory () {
		return directory;
	}
	
	//gets a list of albums. 
	public ObservableList<Album> getAlbums() {
        // If the observable list of albums has not been initialized.
        if (albums == null) {
            if (songs == null) {
                getSongs();
            }
            // Updates the albums array list.
            updateAlbumsList();
        }
        return FXCollections.observableArrayList(albums);
    }
	
	//gets album based on title. 
    public Album getAlbum(String title) {
        if (albums == null) {
            getAlbums();
        }
        return albums.stream().filter(album -> title.equals(album.getTitle())).findFirst().get();
    }
    
    //This uses HashMaps to update the albums list. 
    private void updateAlbumsList() {
        albums = new ArrayList<>();
        
        //Creates the Hashmap based on all the songs created. 
        HashMap<String, List<Song>> albumMap = new HashMap<>(
                songs.stream()
                		//Filters to only albums
                        .filter(song -> song.getAlbum() != null)
                        //sorts those albums. 
                        .collect(Collectors.groupingBy(Song::getAlbum))
        );
        
        //For every entry in the hash map, it will get all the data from the album and add it to the albums array list. 
        //This uses the song as the key. 
        for (Map.Entry<String, List<Song>> entry : albumMap.entrySet()) {
            ArrayList<Song> songs = new ArrayList<>();

            songs.addAll(entry.getValue());
            
            //Repeats the process but for artists. 
            HashMap<String, List<Song>> artistMap = new HashMap<>(
                    songs.stream()
                            .filter(song -> song.getArtist() != null)
                            .collect(Collectors.groupingBy(Song::getArtist))
            );
            
            //Gets all the songs from the album. 
            for (Map.Entry<String, List<Song>> e : artistMap.entrySet()) {
                ArrayList<Song> albumSongs = new ArrayList<>();
                String artist = e.getValue().get(0).getArtist();

                albumSongs.addAll(e.getValue());

                albums.add(new Album(entry.getKey(), artist, albumSongs));
            }
        }

    }
    //Gets list of Artists.
    public ObservableList<Artist> getArtists() {
        if (artists == null) {
            if (albums == null) {
                getAlbums();
            }
            // Updates the artists array list.
            updateArtistsList();
        }
        return FXCollections.observableArrayList(artists);
    }
    //Gets artist by title. 
    public Artist getArtist(String title) {
        if (artists == null) {
            getArtists();
        }
        return artists.stream().filter(artist -> title.equals(artist.getTitle())).findFirst().get();
    }
    //Updates the artistlist using hashmaps.
    //The album is the key. 
    private void updateArtistsList() {
        artists = new ArrayList<>();

        HashMap<String, List<Album>> artistMap = new HashMap<>(
                albums.stream()
                        .filter(album -> album.getArtist() != null)
                        .collect(Collectors.groupingBy(Album::getArtist))
        );

        for (Map.Entry<String, List<Album>> entry : artistMap.entrySet()) {

            ArrayList<Album> albums = new ArrayList<>();

            albums.addAll(entry.getValue());

            artists.add(new Artist(entry.getKey(), albums));
        }
    }
    
    //Loads the nowplaying list, used to get what songs are currently playing. 
    public ArrayList<Song> loadPlayingList() {

        ArrayList<Song> nowPlayingList = new ArrayList<>();

        try {

            XMLInputFactory factory = XMLInputFactory.newInstance();
            FileInputStream is = new FileInputStream(new File("lib/library.xml"));
            XMLStreamReader reader = factory.createXMLStreamReader(is, "UTF-8");

            String element = "";
            boolean isNowPlayingList = false;

            while(reader.hasNext()) {
                reader.next();
                if (reader.isWhiteSpace()) {
                    continue;
                } else if (reader.isCharacters() && isNowPlayingList) {
                    String value = reader.getText();
                    if (element.equals(ID)) {
                        nowPlayingList.add(getSong(Integer.parseInt(value)));
                    }
                } else if (reader.isStartElement()) {
                    element = reader.getName().getLocalPart();
                    if (element.equals("nowPlayingList")) {
                        isNowPlayingList = true;
                    }
                } else if (reader.isEndElement() && reader.getName().getLocalPart().equals("nowPlayingList")) {
                    reader.close();
                    break;
                }
            }

            reader.close();

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return nowPlayingList;
    }
    
    //This was our attempt at creating a sorting algorithm, but it was very inefficient so we scrapped it. 
    /*
    public static void sortSongs( ObservableList<String> albumTitle)
    {
          int j;
          boolean flag = true;  // will determine when the sort is finished
          String temp = null;


          while ( flag )
          {
                flag = false;
                for ( j = 0;  j < albumTitle.size() - 1;  j++ )
                {
                        if ( (albumTitle.get(j)).compareToIgnoreCase(albumTitle.get(j+1))>0)
                        {                                             // ascending sort
                                    temp=albumTitle.get(j);
                                    albumTitle.get(j).equals(albumTitle.get(j+1));     // swapping
                                    albumTitle.get(j+1).equals(temp); 
                                    flag = true;
                         }
                        }
                }
          }
*/

}
	