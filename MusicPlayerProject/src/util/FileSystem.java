package util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileSystem {
	
	private File directory;
	private static final String ID = "id";
	private static final String TITLE = "title";
    private static final String ARTIST = "artist";
    private static final String ALBUM = "album";
    private static final String LENGTH = "length";
    private static final String LOCATION = "location";
    
    
    private static ArrayList<Song> songs;
    private static ArrayList<Artist> artists;
    private static ArrayList<Album> albums;
    private static ArrayList<Playlist> playlists;
	

	public String getDefaultDirectory() throws Exception {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.CANCEL_OPTION) {
        	JOptionPane.showMessageDialog(null, "You must set a default directory to continue.");
        	System.exit(0);
        }
        directory = chooser.getSelectedFile();
   
        return chooser.getSelectedFile().getPath();
        //System.out.println(chooser.getSelectedFile().getName());
	}
	public static boolean isDefaultDirectory() throws Exception {
        File file = new File("lib/library.xml");
		return file.exists();	
	}
	public int getLibrary(File path, Document doc, Element songs, int i) {

		File[] files = directory.listFiles();
		
		 for (File file : files) {
			 if (file.isFile()) {
				try {
				AudioFile audioFile = AudioFileIO.read(file);
				
                 Tag tag = audioFile.getTag();
                 AudioHeader header = audioFile.getAudioHeader();

                 Element song = doc.createElement("song");
                 songs.appendChild(song);
                 
                 Element id = doc.createElement("id");
                 Element title = doc.createElement("title");
                 Element artist = doc.createElement("artist");
                 Element album = doc.createElement("album");
                 Element length = doc.createElement("length");
                 Element location = doc.createElement("location");
                 
                 id.setTextContent(Integer.toString(i++));
                 title.setTextContent(tag.getFirst(FieldKey.TITLE));
                 artist.setTextContent(tag.getFirst(FieldKey.ARTIST));
                 album.setTextContent(tag.getFirst(FieldKey.ALBUM));
                 length.setTextContent(Integer.toString(header.getTrackLength()));
                 location.setTextContent(Paths.get(file.getAbsolutePath()).toString());
                 
                 song.appendChild(id);
                 song.appendChild(title);
                 song.appendChild(artist);
                 song.appendChild(album);
                 song.appendChild(length);
                 song.appendChild(location);
                 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            } else if(file.isDirectory()) {
	            	
			 i = getLibrary(file, doc, songs, i);
		 }
	          
		
	}
		 return i;
}
	public void createLibrary(String path) throws Exception {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element library = doc.createElement("library");
        Element musicpath = doc.createElement("path");
        Element songs = doc.createElement("songs");
        Element playlists = doc.createElement("playlists");
        Element nowPlayingList = doc.createElement("nowPlayingList");
        Element lastSong = doc.createElement("lastPlayed");
		
        doc.appendChild(library);
        library.appendChild(songs);
        library.appendChild(playlists);
        library.appendChild(nowPlayingList);
        library.appendChild(lastSong);
        
        Element musicLibraryFileNum = doc.createElement("fileNum");
        Element lastIdAssigned = doc.createElement("lastId");
        
        musicpath.setTextContent(path.toString());
        library.appendChild(musicpath);
        
        int id = 0;
        File directory = new File(Paths.get(path).toUri());
        int i = getLibrary(directory, doc, songs, id);
  
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        File xmlFile = new File("lib/library.xml");

        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
		
		
		
	}
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
	private Song getSong(int id) {
        if (songs == null) {
            getSongs();
        }
        return songs.get(id);
    }	
    public ObservableList<Song> getSongs() {
        // If the observable list of songs has not been initialized.
        if (songs == null) {
            songs = new ArrayList<>();
            // Updates the songs array list.
            updateSongs();
        }
        return FXCollections.observableArrayList(songs);
    }
	public Song getSong(String title) {
        if (songs == null) {
            getSongs();
        }
        return songs.stream().filter(song -> title.equals(song.getTitle())).findFirst().get();
    }
	public Song getSongLocation(String location) {
		if (songs == null) {
			getSongs();
		}
		return songs.stream().filter(song -> location.equals(song.getLocation())).findFirst().get();
	}
	public File getDirectory () {
		return directory;
	}
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
    public Album getAlbum(String title) {
        if (albums == null) {
            getAlbums();
        }
        return albums.stream().filter(album -> title.equals(album.getTitle())).findFirst().get();
    }
    private void updateAlbumsList() {
        albums = new ArrayList<>();

        HashMap<String, List<Song>> albumMap = new HashMap<>(
                songs.stream()
                        .filter(song -> song.getAlbum() != null)
                        .collect(Collectors.groupingBy(Song::getAlbum))
        );

        for (Map.Entry<String, List<Song>> entry : albumMap.entrySet()) {
            ArrayList<Song> songs = new ArrayList<>();

            songs.addAll(entry.getValue());

            HashMap<String, List<Song>> artistMap = new HashMap<>(
                    songs.stream()
                            .filter(song -> song.getArtist() != null)
                            .collect(Collectors.groupingBy(Song::getArtist))
            );

            for (Map.Entry<String, List<Song>> e : artistMap.entrySet()) {
                ArrayList<Song> albumSongs = new ArrayList<>();
                String artist = e.getValue().get(0).getArtist();

                albumSongs.addAll(e.getValue());

                albums.add(new Album(entry.getKey(), artist, albumSongs));
            }
        }
    }
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
    public Artist getArtist(String title) {
        if (artists == null) {
            getArtists();
        }
        return artists.stream().filter(artist -> title.equals(artist.getTitle())).findFirst().get();
    }
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
    public static void addPlaylist(String text) {

        Thread thread = new Thread(() -> {

            int i = playlists.size() - 2;
            playlists.add(new Playlist(i, text, new ArrayList<>()));

            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse("lib/library.xml");

                XPathFactory xPathfactory = XPathFactory.newInstance();
                XPath xpath = xPathfactory.newXPath();

                XPathExpression expr = xpath.compile("/library/playlists");
                Node playlists = ((NodeList) expr.evaluate(doc, XPathConstants.NODESET)).item(0);

                Element playlist = doc.createElement("playlist");
                playlist.setAttribute("id", Integer.toString(i));
                playlist.setAttribute(TITLE, text);
                playlists.appendChild(playlist);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);
                File xmlFile = new File("lib/library.xml");
                StreamResult result = new StreamResult(xmlFile);
                transformer.transform(source, result);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        thread.start();
    }
    public void removePlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }
    public ObservableList<Playlist> getPlaylists() {
        if (playlists == null) {

            playlists = new ArrayList<>();
            int id = 0;

            try {
                XMLInputFactory factory = XMLInputFactory.newInstance();
                factory.setProperty("javax.xml.stream.isCoalescing", true);
                FileInputStream is = new FileInputStream(new File("lib/library.xml"));
                XMLStreamReader reader = factory.createXMLStreamReader(is, "UTF-8");

                String element;
                boolean isPlaylist = false;
                String title = null;
                ArrayList<Song> songs = new ArrayList<>();

                while(reader.hasNext()) {
                    reader.next();
                    if (reader.isWhiteSpace()) {
                        continue;
                    } else if (reader.isStartElement()) {
                        element = reader.getName().getLocalPart();

                        // If the element is a play list, reads the element attributes to retrieve
                        // the play list id and title.
                        if (element.equals("playlist")) {
                            isPlaylist = true;

                            id = Integer.parseInt(reader.getAttributeValue(0));
                            title = reader.getAttributeValue(1);
                        }
                    } else if (reader.isCharacters() && isPlaylist) {
                        // Retrieves the reader value (song ID), gets the song and adds it to the songs list.
                        String value = reader.getText();
                        songs.add(getSong(Integer.parseInt(value)));
                    } else if (reader.isEndElement() && reader.getName().getLocalPart().equals("playlist")) {
                        // If the play list id, title, and songs have been retrieved, a new play list is created
                        // and the values reset.
                        playlists.add(new Playlist(id, title, songs));
                        id = -1;
                        title = null;
                        songs = new ArrayList<>();
                    } else if (reader.isEndElement() && reader.getName().getLocalPart().equals("playlists")) {
                        reader.close();
                        break;
                    }
                }
                reader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            playlists.sort((x, y) -> {
                if (x.getId() < y.getId()) {
                    return 1;
                } else if (x.getId() > y.getId()) {
                    return -1;
                } else {
                    return 0;
                }
            });

       
        } else {
            playlists.sort((x, y) -> {
                if (x.getId() < y.getId()) {
                    return 1;
                } else if (x.getId() > y.getId()) {
                    return -1;
                } else {
                    return 0;
                }
            });
        }
        return FXCollections.observableArrayList(playlists);
    }
    public Playlist getPlaylist(int id) {
        if (playlists == null) {
            getPlaylists();
        }
        // Gets the play list size.
        int playListSize = getPlaylists().size();
        // The +2 takes into account the two default play lists.
        // The -1 is used because size() starts at 1 but indexes start at 0.
        return playlists.get(playListSize - (id + 2) - 1);
    }
    public Playlist getPlaylist(String title) {
        if (playlists == null) {
            getPlaylists();
        }
        return playlists.stream().filter(playlist -> title.equals(playlist.getTitle())).findFirst().get();
    }
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
}
	