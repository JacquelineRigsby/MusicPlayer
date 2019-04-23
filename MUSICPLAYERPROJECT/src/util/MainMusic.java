package util;


import java.util.Scanner;

public class MainMusic {
	
	

	public static void main(String[] args) throws Exception {
		String filepath = "C:\\Users\\Prem Rana\\Desktop\\Music Stuff\\Exports\\Final\\New Adventure.wav";
		
		
		System.out.println("-------------------------------------------------------");
		System.out.println("Welcome to the Music Player!");
		Thread.sleep(2000);
		if(!FileSystem.isDefaultDirectory()) {
			System.out.println("There is no default directory set. Please choose a default directory");
			//FileSystem.setDefaultDirectory();
		}
		options();
		System.exit(0);
		MusicPlayer music = new MusicPlayer();
		//music.playMusic(filepath);
		

	}
	
	public static void options() throws Exception {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Please choose an option!");
		System.out.println("1) Play Last Song \n2) Albums & Playlists \n3)Artists \n4)Songs");
		int option = keyboard.nextInt();
		chooseOption(option);
	}
	
	public static void chooseOption(int option) throws Exception {
		MusicPlayer music = new MusicPlayer();
		switch(option) {
		case 1:
			//String filepath=FileSystem.getLastSong();
			//System.out.println(filepath); 
			//System.exit(0);
			//music.playMusic(filepath);
			break;
		case 2:
			//go to list of albums and such
			break;
		case 3:
			//go to list of artists 
			break;
		case 4:
			//go to list of songs
			break;
		}
		
	}
	
	
	

}
