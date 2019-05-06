package util;



import java.util.ArrayList;


public final class Album{

    private String title;
    private String artist;
   
    private ArrayList<Song> songs;

   

    /**
     * Constructor for the Album class. 
     * Creates an album object.
     *

     * @param title
     * @param artist
     * @param songs
     */
    public Album(String title, String artist, ArrayList<Song> songs) {
        this.title = title;
        this.artist = artist;
        this.songs = songs;
        
    }

    /**
     * Gets album title
     *
     * @return album title
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * Gets album artist
     *
     * @return album artist
     */

    public String getArtist() {
        return this.artist;
    }
    
    /**
     * Gets album songs
     *
     * @return album songs
     */

    public ArrayList<Song> getSongs() {
        return new ArrayList<>(this.songs);
    }

   
    
}
