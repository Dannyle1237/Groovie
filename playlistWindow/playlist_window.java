package playlistWindow;
import playlistWindow.songstuff.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.sound.sampled.*;
import java.io.IOException;

public class playlist_window{
    //ArrayList<Genre> genres = new ArrayList<Genre>();
    ArrayList<JButton> buttons = new ArrayList<JButton>();
    JPanel panel;
    JPanel panelGrid;
    JPanel pageEnd;
    //Grid layout for JFrame or JPanel
    GridBagLayout Grid = new GridBagLayout();
    GridBagConstraints c;
    Color titleColor = new Color(25,20,20);
    Color darkGreen = new Color(29,185,84);
    Color outlineColor = Color.WHITE;
    Playlist playlist;

    public boolean opened;

    public playlist_window(Playlist playlist, JFrame frame){
        opened = true;
        frame.getContentPane().removeAll();
        this.playlist = playlist;
        JLabel title = new JLabel(playlist.getTitle(), SwingConstants.CENTER);
        title.setFont(new Font("Gotham", Font.BOLD, 65));
        title.setForeground(Color.WHITE);
        c = new GridBagConstraints();
        panel = new JPanel();
        panelGrid = new JPanel();
        panelGrid.setLayout(Grid);
        pageEnd = new JPanel();
        ImageIcon playIcon = new ImageIcon("playlistWindow/songIcons/PlayIcon.png");
        frame.getContentPane().setBackground(titleColor); // originally darkGreen

        //Create JCheckbox for every Song
        for(int i = 0; i < playlist.getPlaylistLength(); i++){
            JButton button = new JButton(playlist.getSong(i).getTitle(), playIcon);
            button.setForeground(Color.WHITE);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setPreferredSize(new Dimension(50, 1280));
            button.addActionListener(new buttonListener(i));
            button.setFont(new Font("Gotham", Font.PLAIN, 24)); // was 28
            buttons.add(button);
            panel.add(button);
        }
        panel.setBackground(titleColor);
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                opened = false;
            }
        });
        c.gridx = 0; c.gridy = 5;
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panelGrid.add(panel);
        pageEnd.add(submit);

        panelGrid.setBackground(titleColor); // originally darkGreen
        pageEnd.setBackground(darkGreen); // originally titleColor?
        frame.add(title, BorderLayout.PAGE_START);
        frame.add(panelGrid, BorderLayout.CENTER);
        //frame.add(titlesPanelGrid, BorderLayout.CENTER);
        frame.add(pageEnd, BorderLayout.PAGE_END);
        frame.setVisible(true);
    }

 

    //buttonListener for submit JButton
    public class buttonListener extends JFrame implements ActionListener{
        //constructor to create buttonListener Object to add to JButton
        int songIndex;
        public buttonListener(int songIndex){
            this.songIndex = songIndex;
        }

        //method to submit selected Genres
        public void actionPerformed(ActionEvent e){
            try {
                playlist.getSong(songIndex).playSnippet();
            }
            catch(UnsupportedAudioFileException|LineUnavailableException|IOException|InterruptedException a) {
                System.out.println("Cannot play song: "+a);
            }
            
        }
    }

}
