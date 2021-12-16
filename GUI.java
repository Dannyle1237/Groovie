import GUIproject.startMenu.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

import createPlaylist.createPlaylist_window;
import playlistWindow.songstuff.*;
import playlistWindow.*;
import tinderGUI.tinder.*;
import User.user;


public class GUI{

    JFrame frame;
    int choice, index;
    ArrayList<Playlist> playlists = new ArrayList<Playlist>(); //All of our hardcoded Playlists (each genre)
    ArrayList<Playlist> selectedPlaylists; //Arraylist of only the selected genres
    Playlist all; //Playlist with all songs from each selected genre and shuffled
    Playlist finished; //Finished product
    startupMenu mainGUI = new startupMenu();
    user user;
    public GUI(){
        frame = new JFrame("Groovie");
        frame.setSize(1280,720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        File dir = new File("playlistWindow/Playlists");
        for(File file : dir.listFiles()){
            Playlist playlist = new Playlist(file.getPath());
            playlists.add(playlist);
        }
    }

    public void opened(){
        int rNum = (int)Math.floor(Math.random()*(999999999-100000000+1) + 100000000);
        mainGUI.getUserName();
        while(mainGUI.getName().equals(null)){
            try{
                Thread.sleep(200);
            }catch (Exception e){

            }
        }
        try{
            File file = new File("users.txt");
            Scanner checkUsersFile = new Scanner(file);
            while(checkUsersFile.hasNextLine()){
                String name = checkUsersFile.nextLine().split(";")[0];
                if(name.equals(mainGUI.getName())){
                    user = new user(mainGUI.getName(), Integer.parseInt( checkUsersFile.nextLine().split(";")[1]) );
                }
            }
        }catch(FileNotFoundException e){
            System.out.println(e + " file cannot be found"); 
        }


        user = new user(mainGUI.getName(), rNum);
    }

    public void createMainMenu(){
        //Create main menu (create, open, exit) and wait for a button to be pressed
        choice = -1;
        mainGUI.createMainGUI(frame);
        while(choice == -1){
            choice = mainGUI.getChoice();
            try{
                Thread.sleep(200);
            } catch(InterruptedException e){
            }
        }
        switch(choice){
            case 1:
                createPlaylistWindow();
                break;
            case 2:
                openPlaylistWindow();
                break;
            case 3:
                System.exit(0);
            default: 
                System.out.println("Well I messed up"); 
        }
    }

    public void createPlaylistWindow(){

        
        selectedPlaylists = new ArrayList<Playlist>(); //Arraylist of only the selected genres
        all = new Playlist(); //Playlist with all songs from each selected genre and shuffled
        finished = new Playlist(); //Finished product
        createPlaylist_window create = new createPlaylist_window(frame);
        

        //while loop waits until User presses submit button
        while(create.isWaiting()){
            try{
                Thread.sleep(200);
            } catch(Exception e){
                System.out.println("Something went wrong: GUI.java line 69");
            }
        }

        //Iterate through all String genre that is selected from JCheck boxes
        for(int i = 0; i < create.getSelectedGenres().size(); i++){
            for(int j = 0; j < playlists.size(); j++){
                if(playlists.get(j).getTitle().equals( create.getSelectedGenres().get(i)) ){  //if a playlist has the same title as a genre selected, add playlist to arraylist
                    selectedPlaylists.add(playlists.get(j));
                }
            }
        }
        for(int i = 0; i < selectedPlaylists.size(); i++){
            for(int j = 0; j < selectedPlaylists.get(i).getPlaylistLength(); j++){
                Song song = selectedPlaylists.get(i).getSong(j);
                all.addSong(song);
            }
        }
        Collections.shuffle(all.getSongs());
        tinderGUI t1 = new tinderGUI();
        t1.createTinderGUI(all, frame);
        finished = t1.getNewPlaylist();
        user.savePlaylist(finished);
        frame.getContentPane().removeAll();
        openPlaylistWindow(finished);
    }

    public void openPlaylistWindow(Playlist finishedPlaylist){ 
        playlist_window window = new playlist_window(finishedPlaylist, frame);
        while(window.opened){
            try{
                Thread.sleep(200);
            }catch(Exception e){
            }
        }
        createMainMenu();
    }

    public void openPlaylistWindow(){
        index = -1;
        frame.getContentPane().removeAll();
        JPanel panel = new JPanel();
        
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        if(user.getPlaylists().size() == 0){
            createMainMenu();
        }
        else{
            JLabel titleLabel = new JLabel("Stored Playlists", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Gotham", Font.BOLD, 65));
            titleLabel.setForeground(Color.decode("#191414"));
            JPanel titlePanel = new JPanel();
            titlePanel.add(titleLabel);
            titlePanel.setBackground(Color.decode("#1db954"));
            ImageIcon selectIcon = new ImageIcon("openPlaylistWindow/playlistIcon.png");

            JPanel bottomPanel = new JPanel();
            bottomPanel.setBackground(Color.decode("#1db954"));
            frame.add(bottomPanel, BorderLayout.PAGE_END);

            GridBagLayout Grid = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            JPanel panelGrid = new JPanel();
            panelGrid.setLayout(Grid);
            c.gridx = 0; c.gridy = 5;

            for(int i = 0; i < user.getPlaylists().size(); i++){
                JButton button = new JButton(user.getPlaylists().get(i).getTitle(), selectIcon);
                button.setFont(new Font("Gotham", Font.PLAIN, 24));
                button.setBackground(Color.decode("#1db954"));
                button.setForeground(Color.decode("#191414"));
                buttons.add(button);
                button.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        index = buttons.indexOf(e.getSource());
                    }
                });
                panel.add(button);  
            }
            panel.setBackground(Color.decode("#191414"));
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panelGrid.setBackground(Color.decode("#191414"));
            panelGrid.add(panel);
            frame.add(panelGrid, BorderLayout.CENTER);
            frame.add(titlePanel, BorderLayout.PAGE_START);
            frame.setVisible(true);
            while(index == -1){
                try{
                    Thread.sleep(200);
                }catch (Exception e){
                }
            }
            openPlaylistWindow(user.getPlaylists().get(index));
        }
        
    }

    
}