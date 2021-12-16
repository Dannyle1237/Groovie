package createPlaylist;

public class Genre {
    private String genre;
    private boolean selected = false;
    
    public Genre(String genre){
        this.genre = genre;
    }

    public void setSelected(){
        selected = true;
    }

    public void deSelect(){
        selected = false;
    }

    public boolean isSelected(){
        return selected;
    }

    public String getGenre(){
        return genre;
    }
}
