package playlistWindow.songstuff;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import java.lang.String;
import javax.swing.ImageIcon;

public class Song {
    private String title;
    private String author;
    private String fileName;
    private String hyperlink;
    // ArrayList genre?
    
    public Song(String info)
	{
	    String[] infoArray = info.split("; ");
		title = infoArray[0];
		author = infoArray[1];
		fileName = "playlistWindow/musicFiles/" + infoArray[2];
        hyperlink = infoArray[3];
	}
	
	public void playSnippet() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException // make private?
	{
	    System.out.println("Playing "+title);
		File file = new File(fileName);
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		clip.start();
		Thread.sleep(16000);
		clip.close();
	}
	
	public String getTitle() // make private?
	{
	    return title;
	}

	public String getAuthor() {
		return author;
	}

	public ImageIcon getImage(){
		ImageIcon image;
		File dir = new File("playlistWindow/songIcons");
        String text = new String();
        for(File file : dir.listFiles()){
            text = file.getName().substring(0, file.getName().length() - 4);
			if(text.equals(getTitle())){
				image = new ImageIcon(file.getPath());
				System.out.println(file.getName() + " Image found");
				return image;
			}
        }
		image = new ImageIcon("tinderGUI/song.png");
		return image;
	}

	public String getFileName() {
		return fileName;
	}
}
