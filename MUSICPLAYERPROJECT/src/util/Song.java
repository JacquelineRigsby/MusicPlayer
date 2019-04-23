package util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public final class Song {
	private int id;
    private SimpleStringProperty title;
    private SimpleStringProperty artist;
    private SimpleStringProperty album;
    private SimpleStringProperty length;
    private long lengthInSeconds;
    private String location;
    private SimpleBooleanProperty playing;
    private SimpleBooleanProperty selected;

    /**
     * Constructor for the song class.
     *
     * @param title
     * @param artist
     * @param album
     * @param length
     * @param location
     */
    public Song(int id, String title, String artist, String album, Duration length, String location) {

        if (title == null) {
            Path path = Paths.get(location);
            String fileName = path.getFileName().toString();
            title = fileName.substring(0, fileName.lastIndexOf('.'));
        }

        if (album == null) {
            album = "Unknown Album";
        }

        if (artist == null) {
            artist = "Unknown Artist";
        }

        this.id = id;
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty(artist);
        this.album = new SimpleStringProperty(album);
        this.lengthInSeconds = length.getSeconds();
        long seconds = length.getSeconds() % 60;
        this.length = new SimpleStringProperty(length.toMinutes() + ":" + (seconds < 10 ? "0" + seconds : seconds));
        this.location = location;
        this.playing = new SimpleBooleanProperty(false);
        this.selected = new SimpleBooleanProperty(false);
    }
    
    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title.get();
    }

    public StringProperty titleProperty() {
        return this.title;
    }

    public String getArtist() {
        return this.artist.get();
    }

    public StringProperty artistProperty() {
        return this.artist;
    }

    public String getAlbum() {
        return this.album.get();
    }

    public StringProperty albumProperty() {
        return this.album;
    }

    public String getLength() {
        return this.length.get();
    }

    public StringProperty lengthProperty() {
        return this.length;
    }

    public long getLengthInSeconds() {
        return this.lengthInSeconds;
    }

    public String getLocation() {
        return this.location;
    }

    public BooleanProperty playingProperty() {
        return this.playing;
    }

    public boolean getPlaying() {
        return this.playing.get();
    }

    public void setPlaying(boolean playing) {
        this.playing.set(playing);
    }

    public BooleanProperty selectedProperty() {
        return this.selected;
    }

    public boolean getSelected() {
        return this.selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}