package GUIproject.startMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class startupMenu {

    String name;
    int choice;

    // Setting universal frame
    public startupMenu() {
    }

    // get User's name
    public void getUserName() {
        name = JOptionPane.showInputDialog("Enter your name");
        JOptionPane.showMessageDialog(null, "Hello " + name + ", Welcome to Groovie!");
    }

    // Main start-up Menu
    public void createMainGUI(JFrame frame) {
        choice = -1;
        // clearing frame
        frame.getContentPane().removeAll();

        // setting up panels
        JPanel buttonsPanel = new JPanel();
        JPanel imagePanel = new JPanel();
        JPanel catchPhrasePanel = new JPanel();


        // buttons
        JButton createNewPlaylist = new JButton("Create a New Playlist");
        JButton openPlaylist = new JButton("Open Playlists");
        JButton exitProgram = new JButton("Exit");

        // adding buttons to panel
        buttonsPanel.add(createNewPlaylist);
        buttonsPanel.add(openPlaylist);
        buttonsPanel.add(exitProgram);

        //adding catch phrase to panel 
        JLabel catchPhrase = new JLabel("\""+name+", find the right song for you. Swipe Right\"");
        catchPhrasePanel.add(catchPhrase);
        catchPhrasePanel.setBackground(Color.decode("#191414"));
        catchPhrase.setForeground(Color.decode("#ffffff"));
        catchPhrase.setFont(new Font("Gotham",Font.ITALIC, 24));
        frame.add(catchPhrasePanel, BorderLayout.CENTER);

        // adding color
        buttonsPanel.setBackground(Color.decode("#1db954"));
        imagePanel.setBackground(Color.decode("#191414"));
        frame.getContentPane().setBackground(Color.decode("#191414"));

        // positioning
        frame.add(buttonsPanel, BorderLayout.PAGE_END);

        // logo
        JLabel groovieLogo = new JLabel(new ImageIcon("GUIproject/Capture.PNG"));
        imagePanel.add(groovieLogo);
        
        frame.add(imagePanel, BorderLayout.PAGE_START);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        

        // action
        createNewPlaylist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                choice = 1;
                // Code
            }

        });

        openPlaylist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                choice = 2;
                // Code
            }

        });

        exitProgram.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                choice = 3;
            }

        });
    }
    public int getChoice(){
        return choice;
    }

    public String getName(){
        return name;
    }

}