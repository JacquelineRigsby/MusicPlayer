This is a simplistic music player. Included are some songs to test. 

FEATURES: 
-Plays and Pauses music. 
-Loops music
-Pulls directly from a music folder that you choose.
-Search for songs in the library
-Library is always updated as you change your songs folder

DATA STRUCTURES USED:
-ArrayList to store a list of all the songs/artists/and albums. Also using ObservableArrayList to be compatible with JavaFX
-General Tree Structure as the main library system. Items are stored in a tree, with the hierarchy being root, artists, albums, songs. 
-Hashmap to update the artists and albums. For albums, songs are used as keys, and for artists, albums are used as keys. 
-Queue structure. This is not fully implemented, as there were issues with it's compatibility with the GUI. We have provided a demo class to simulate it's function. 
There are some functionalities added for queue, such as adding the song to the list of items, which would be the queue. 

LIBRARYS REFERENCED:
-JavaFX for GUI.
-JAudioTagger to tag each audio file
-JavaXML to use XML for library file. 

CONTRIBUTIONS:
-Prem Rana: General GUI Design and Hashmap Structure Design
-Dominique Forbes: General Tree Structure Design and Sorting Algorithm Design
-Jacqueline Rigsby: Queue Structure Design and Shuffle Design.

CURRENT BUGS:
-Playlist is unimplemented due to time constraints
-Shuffle/Skip is unimplemented due to the queue not working with the GUI. 
-Queue is unimplemented due to data not being consistant across all instances of an FXML file.
-Time Slider does not update with the song due to the media player and time slider not communicating properally.
-Some songs get sorted incorrectly in albums. 
