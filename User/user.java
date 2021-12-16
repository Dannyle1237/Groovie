package User;
import playlistWindow.songstuff.*;
import java.util.ArrayList;

public class user {
    String name;
    int IDnum;
    ArrayList<Playlist> savedPlaylists = new ArrayList<Playlist>();
    public user(String name, int IDnum){
        this.name = name;
        this.IDnum = IDnum;
    }
    public String getName(){
        return name;
    }
    public int getID(){
        return IDnum;
    }
    public void savePlaylist(Playlist playlist){
        savedPlaylists.add(playlist);
    }
    public ArrayList<Playlist> getPlaylists(){
        return savedPlaylists;
    }
}
