package tinderGUI.tinder;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import playlistWindow.songstuff.*;

import javax.sound.sampled.*;
import java.io.File; // added 12/8
import java.io.IOException; // added 12/8

public class tinderGUI {
    ArrayList<Song> songs = new ArrayList<Song>();
    Playlist selectedSongs = new Playlist();
    int i;
    int j;
    Song song;
    Clip clip; // added 12/8
    boolean asking; 

    public tinderGUI() {
    }

    public void createTinderGUI(Playlist selected, JFrame frame){
        /*
        selectedSongs = new playlist songs
        selected = playlist full of randomized songs
        */

        // remove everything in frame
        frame.getContentPane().removeAll();
        Icon heartImage = new ImageIcon("tinderGUI/heart.png");
        Icon dislikeImage = new ImageIcon("tinderGUI/x_button.png");
        Icon songImage = new ImageIcon("tinderGUI/song.png");

         // setting up panels
        //JPanel heartPanel = new JPanel();
        //JPanel dislikePanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel songPanel = new JPanel();
        JPanel donePanel = new JPanel();

        // buttons
        JButton heartButton = new JButton(heartImage);
        heartButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Heart Pressed");
                selectedSongs.addSong(song);
                clip.close();
                j++;
            }
        });

        JButton dislikeButton = new JButton(dislikeImage);
        dislikeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Dislike pressed");
                clip.close();
                j++;
            }
        });

        JButton songButton = new JButton(songImage);
        songButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //try{
                    clip.setMicrosecondPosition(0); // was song.playSnippet();
                    clip.start();
               /* }
                catch(UnsupportedAudioFileException a){
                }
                catch(LineUnavailableException b){
                }
                catch(IOException c){
                }
                catch(InterruptedException d){
                }*/
            }
        });

        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                j = selected.getPlaylistLength();
                asking = false;
            }
        });

        heartButton.setPreferredSize(new Dimension(200, 200));
        dislikeButton.setPreferredSize(new Dimension(200, 200));
        songButton.setPreferredSize(new Dimension(400, 400));

        heartButton.setOpaque(false);
        heartButton.setContentAreaFilled(false);
        heartButton.setBorderPainted(false);

        dislikeButton.setOpaque(false);
        dislikeButton.setContentAreaFilled(false);
        dislikeButton.setBorderPainted(false);

        // adding buttons to panel
        songPanel.add(songButton);
        //dislikePanel.add(dislikeButton);
        //heartPanel.add(heartButton);
        centerPanel.add(dislikeButton);
        centerPanel.add(songPanel);
        centerPanel.add(heartButton);
        donePanel.add(doneButton);

        // setting background colors
        centerPanel.setBackground(Color.decode("#191414"));
        //dislikePanel.setBackground(Color.decode("#191414"));
        //heartPanel.setBackground(Color.decode("#191414"));
        songPanel.setBackground(Color.decode("#191414"));
        frame.getContentPane().setBackground(Color.decode("#191414"));
        donePanel.setBackground(Color.decode("#1db954"));

        // frame
        //frame.add(heartPanel, BorderLayout.LINE_END);
        //frame.add(dislikePanel, BorderLayout.LINE_START);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(donePanel, BorderLayout.PAGE_END);

        i = 0;
        j = 0;
        song = selected.getSong(i);
        songImage = song.getImage();
        songButton.setIcon(songImage);
        songButton.setPreferredSize(new Dimension(400, 400));
        songPanel.removeAll();
        songPanel.add(songButton);
        
        // 12/8
        
        try {
            File file = new File(song.getFileName());
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        }
        catch(Exception e) {
        }
        
        // 12/8
        
        JLabel titleLabel = new JLabel(song.getTitle()+" by "+song.getAuthor());
        JPanel titlePanel = new JPanel();
        titleLabel.setFont(new Font("Gotham", Font.PLAIN, 32));
        titlePanel.setBackground(Color.decode("#1db954"));
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.PAGE_START);

        // setting frame to visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        while(j < selected.getPlaylistLength() ){
            if(i != j){
                i = j;
                song = selected.getSong(i);
                songImage = song.getImage();
                songButton.setIcon(songImage);
                songButton.setPreferredSize(new Dimension(400, 400));
                songPanel.removeAll();
                songPanel.revalidate();
                songPanel.repaint();
                songPanel.add(songButton);
                // 12/8
                
                try {
                    File file = new File(song.getFileName());
		            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		            clip = AudioSystem.getClip();
		            clip.open(audioStream);
                }
                catch(Exception e) {
                }
                
                // 12/8
                titleLabel.setText(song.getTitle()+" by "+song.getAuthor());
                titlePanel.removeAll();
                titlePanel.add(titleLabel);

                frame.setVisible(true);
                //System.out.println(i);
            }
            try{
                Thread.sleep(175);
            }catch(Exception e){

            }
        }
        asking = true;
        frame.getContentPane().remove(centerPanel);
        titleLabel.setText("What would you like to name the playlist?");
        titleLabel.setFont(new Font("Gotham", Font.PLAIN, 42));
        titlePanel.removeAll();
        titlePanel.add(titleLabel);

        GridBagConstraints c = new GridBagConstraints();
        JTextField text = new JTextField(20);
        text.setPreferredSize(new Dimension(200,50));
        text.setFont(new Font("Gotham", Font.PLAIN, 20));
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        c.gridy = -4;
        centerPanel.add(text,c);
        centerPanel.setBackground(Color.decode("#191414"));

        //frame.add(titlePanel, BorderLayout.PAGE_START);
        frame.add(centerPanel, BorderLayout.CENTER);
        while(asking){
            frame.setVisible(true);
            selectedSongs.setTitle(text.getText());
            try{
                Thread.sleep(200);
            }catch (Exception e){
            }
        }
    }

    public Playlist getNewPlaylist(){
        return selectedSongs;
    }
}
