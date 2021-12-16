package playlistWindow.songstuff;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Playlist {
    private String title;
    private ArrayList<Song> songs = new ArrayList<Song>();
    
    public Playlist()
    {
      
    }
    
    public Playlist(String fileName) // for our genre playlists, do we need an empty constructor for playlists created by the user?
    {
       // ArrayList<Song> songs = new ArrayList<Song>();
        try {
            File file = new File(fileName);
            Scanner inFile = new Scanner(file);
            if(inFile.hasNextLine())
            {
                title = inFile.nextLine();
            }
            while(inFile.hasNextLine())
            {
                String songInfo = inFile.nextLine();
                Song song = new Song(songInfo);
                songs.add(song);   
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Error has occured: "+e);
        }
    }
    
    public Song getSong(int index)
    {
        return songs.get(index);
    }
    public void setTitle(String title) // make private?
    {
        this.title = title;
    }
    public void removeSong(int index) // make private?
    {
        songs.remove(index);
    }
    public String getTitle() // do we need this?
    {
        return title;
    }
    public int getPlaylistLength() // do we need this? if we want to play through the entire playlist?
    {
        return songs.size();
    }
    public void addSong(Song song)
    {
        songs.add(song);
    }
    public ArrayList<Song> getSongs(){
        return songs;
    }
}
