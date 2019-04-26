package util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



public class MusicPlayer <T> {
	
	private MediaPlayer mediaPlayer;
	private Media song;
	private FileSystem file = new FileSystem();
	boolean playing = false;

	
	static Clip clip;
	static long length;
	
	public void playMusic(String musicLocation){
		Media media = loadMusic(musicLocation);
		if(!isPlaying()) {
			song = media;
			mediaPlayer = new MediaPlayer(song);
			mediaPlayer.play();
			playing = true;

		}
		if(mediaPlayer.getCurrentTime()==media.getDuration()) {
			System.out.println("test");
			playing = false;
		}
		

	}
	
	public String getMusic(String song) throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		FileInputStream is = new FileInputStream(new File("lib/library.xml"));
		XMLStreamReader reader = factory.createXMLStreamReader(is, "UTF-8");
		
		String element; 
		boolean found = false;
		String path = "";
		 try {
			while(reader.hasNext() && !found) {
			     reader.next();
			     if (reader.isWhiteSpace()) {
			         continue;
			     } else if (reader.isStartElement()) {
			         element = reader.getName().getLocalPart();
			         if(element.equals("location")) {
			        	path = element;

			         }
			         if(element.equals("title")) {
			        	 path = element;
			         }
			      
			     } else if(reader.isCharacters() && path.equals("location")) {
			    	 if(reader.getText().toLowerCase().contains(song.toLowerCase())) {
			    		 path=reader.getText();
			    		 found = true;
			    	 }
			    	 
			    	 
			    	 
			     }else if(reader.isEndElement() && reader.getName().getLocalPart().equals("songs")) {
			    	 reader.close();
			    	 break;
			     }
			 }
		} catch (XMLStreamException e) {
			
		}
		return path;
	}
	
	public Media loadMusic(String MusicLocation) {
		Media media = new Media(Paths.get(MusicLocation).toUri().toString());
		return media;
	}
	
	public boolean isPlaying() {
		return mediaPlayer != null && playing;
	}
	
	public void pauseMusic() {
		mediaPlayer.pause();
		playing = false;
	}
	
	
	

}
