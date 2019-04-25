package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Playlist {

    private int id;
    private String title;
    private ArrayList<Song> songs;
    private String placeholder =
            "Add songs to this playlist by dragging items to the sidebar\n" +
            "or by clicking the Add to Playlist button";
    public static final String APIBASE = "http://ws.audioscrobbler.com/2.0/?";
    public static final String APIKEY = "57ee3318536b23ee81d6b27e36997cde";

    /**
     * Constructor for the Playlist class.
     * Creates a playlist object.
     * 
     * @param id
     * @param title
     * @param songs
     */
    public Playlist(int id, String title, ArrayList<Song> songs) {
        this.id = id;
        this.title = title;
        this.songs = songs;
    }

    protected Playlist(int id, String title, String placeholder) {
        this.id = id;
        this.title = title;
        this.songs = null;
        this.placeholder = placeholder;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getPlaceholder() {
        return this.placeholder;
    }

    public ObservableList<Song> getSongs() {
        return FXCollections.observableArrayList(this.songs);
    }
    
    public void addSong(Song song) {
    	if (!songs.contains(song)) {

    		songs.add(song);

    		try {
    			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    			Document doc = docBuilder.parse("lib/library.xml");

    			XPathFactory xPathfactory = XPathFactory.newInstance();
    			XPath xpath = xPathfactory.newXPath();

    			XPathExpression expr = xpath.compile("/library/playlists/playlist[@id=\"" + this.id + "\"]");
    			Node playlist = ((NodeList) expr.evaluate(doc, XPathConstants.NODESET)).item(0);

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
    	}
    }
    

    @Override
    public String toString() {
        return this.title;
    }
}