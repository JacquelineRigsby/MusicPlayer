package util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

/**
 * Model class for an Artist
 *
 */
public final class Artist{

    private String title;
    private ArrayList<Album> albums;
    private Image artistImage;
    private SimpleObjectProperty<Image> artistImageProperty;
    public static final String APIBASE = "http://ws.audioscrobbler.com/2.0/?";
    public static final String APIKEY = "57ee3318536b23ee81d6b27e36997cde";

    /**
     * Constructor for the Artist class.
     * Creates an artist object and obtains the artist artwork.
     *
     * @param title Artist name
     * @param albums List of artist albums
     */
    public Artist(String title, ArrayList<Album> albums) {
        this.title = title;
        this.albums = albums;
       
    }

    /**
     * Gets the artist title.
     * @return artist title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets array list of artist albums
     * @return artist albums
     */
    public ArrayList<Album> getAlbums() {
        return new ArrayList<>(this.albums);
    }

    public ObjectProperty<Image> artistImageProperty() {
        return this.artistImageProperty;
    }

    /**
     * Gets images for artists
     * @return artist image
     */
   

}