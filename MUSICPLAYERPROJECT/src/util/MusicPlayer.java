package util;
import java.io.File;
import java.io.FileInputStream;
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

	
	static Clip clip;
	static long length;
	
	static void playMusic1(String musicLocation) {
		
		try {
			File musicPath = new File(musicLocation);
			
			if(musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				System.out.println(clip.getMicrosecondLength());
				Thread.sleep(5000);
				clip.start();
				//System.out.println(clip.getMicrosecondLength());
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				
				JOptionPane.showMessageDialog(null, "Hit ok to pause");
				long clipTimePosition = clip.getMicrosecondPosition();
				clip.stop();
				
				JOptionPane.showMessageDialog(null, "Hit ok to go");
				clip.setMicrosecondPosition(clipTimePosition);
				clip.start();
				
				
				//JOptionPane.showMessageDialog(null, "Press Stop to stop playing");
			}
			else {
				System.out.println("Can't find file");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Clip oldloadMusic(String MusicLocation) throws Exception {
		File music = new File(MusicLocation);
		AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
		clip = AudioSystem.getClip();
		clip.open(audioInput);
		return clip;
	}

	public void playMusic(String musicLocation) throws Exception {
		Media media = loadMusic(musicLocation);
		mediaPlayer = new MediaPlayer(media);
		if(!isPlaying()) {
			mediaPlayer.play();
		}
		

	}
	
	public String getMusic() {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		FileInputStream is = new FileInputStream(new File("lib/library.xml"));
		XMLStreamReader reader = factory.createXMLStreamReader(is, "UTF-8");
		
		String element; 
		
		 try {
			while(reader.hasNext()) {
			     reader.next();
			     if (reader.isWhiteSpace()) {
			         continue;
			     } else if (reader.isStartElement()) {
			         element = reader.getName().getLocalPart();
			     }
			 }
		} catch (XMLStreamException e) {
			
		}
		return element;
	}
	
	public Media loadMusic(String MusicLocation) {
		Media media = new Media(Paths.get(MusicLocation).toUri().toString());
		return media;
	}
	
	public boolean isPlaying() {
		return mediaPlayer != null && MediaPlayer.Status.PLAYING.equals(mediaPlayer.getStatus());
	}
	
	public void pauseMusic() {
		mediaPlayer.pause();
	}
	
	
	

}
