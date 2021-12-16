package createPlaylist;
import playlistWindow.songstuff.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class createPlaylist_window{
    ArrayList<Genre> genres;
    ArrayList<JCheckBox> checkboxes;
    ArrayList<String> selectedGenres;
    JPanel panel;
    JPanel panelGrid;
    JPanel pageEnd;
    //Grid layout for JFrame or JPanel
    GridBagLayout Grid = new GridBagLayout();
    GridBagConstraints c;
    Color titleColor = new Color(25,20,20);
    Color darkGreen = new Color(29,185,84);
    Color outlineColor = Color.WHITE;
    boolean waiting;

    public createPlaylist_window(JFrame frame){
        genres = new ArrayList<Genre>();
        checkboxes = new ArrayList<JCheckBox>();
        selectedGenres = new ArrayList<String>();
        waiting = false;
        frame.getContentPane().removeAll();
        JLabel title = new JLabel("SELECT PREFERRED GENRES", SwingConstants.CENTER);
        title.setFont(new Font("Gotham", Font.BOLD, 65));
        title.setForeground(Color.WHITE);
        c = new GridBagConstraints();
        panel = new JPanel();
        panelGrid = new JPanel();
        panelGrid.setLayout(Grid);
        pageEnd = new JPanel();
        Genre genre;
        frame.getContentPane().setBackground(titleColor);

        //Read and store genres into ArrayList from .txt file
        try{ 
            File read = new File("createPlaylist/genres.txt");
            Scanner inFile = new Scanner(read);
            while(inFile.hasNextLine())
            {
                genre = new Genre(inFile.nextLine());
                genres.add(genre);
            }
        } catch(FileNotFoundException e)
        {
            System.out.println("Error has occured");
        }

        //Create JCheckbox for every Genre
        for(int i = 0; i < genres.size(); i++){
            JCheckBox checkbox = new JCheckBox(genres.get(i).getGenre());
            checkbox.addItemListener(new checkboxListener());
            checkbox.setFont(new Font("Gotham", Font.PLAIN, 28));
            checkbox.setForeground(Color.WHITE);
            checkbox.setBackground(titleColor);
            checkboxes.add(checkbox);
            panel.add(checkbox);
        }
        panel.setBackground(titleColor);
        JButton submit = new JButton("Submit");
        submit.addActionListener(new buttonListener());
        
        c.gridx = 0; c.gridy = 5;
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panelGrid.add(panel);
        pageEnd.add(submit);

        panelGrid.setBackground(titleColor);
        pageEnd.setBackground(darkGreen);
        frame.add(title, BorderLayout.PAGE_START);
        frame.add(panelGrid, BorderLayout.CENTER);
        frame.add(pageEnd, BorderLayout.PAGE_END);
        frame.setVisible(true);
        waiting = true;
    }

    public class checkboxListener extends JFrame implements ItemListener{
    
        public checkboxListener(){
        }

        public void itemStateChanged(ItemEvent e){
            //If JCheckBox is selected, set Genre to selected
            if(e.getStateChange() == 1){
                
                //check through all checkboxes for any selected
                for(int i = 0; i < checkboxes.size(); i++){
                    if(checkboxes.get(i).isSelected()){
                        //If checkbox is selected, check all genres with same name as checkbox and set them to selected
                        for(int j = 0; j < genres.size(); j++){
                            if(checkboxes.get(i).getText().equals(genres.get(j).getGenre())){
                                genres.get(j).setSelected();
                            }
                        }
                    }
                }
            }
            //If JCheckBox is not selected, set Genre to deSelected
            else{
                //check through all checkboxes for any selected
                for(int i = 0; i < checkboxes.size(); i++){
                    if(!(checkboxes.get(i).isSelected())){
                        //If checkbox is not selected, check all genres with same name as checkbox and set to deSelected
                        for(int j = 0; j < genres.size(); j++){
                            if(checkboxes.get(i).getText().equals(genres.get(j).getGenre())){
                                genres.get(j).deSelect();
                            }
                        }
                    }
                }
            }
        }
    }

    //buttonListener for submit JButton
    public class buttonListener extends JFrame implements ActionListener{
        //constructor to create buttonListener Object to add to JButton
        public buttonListener(){
        }

        //method to submit selected Genres
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < genres.size(); i++){
                if(genres.get(i).isSelected()){
                    selectedGenres.add(genres.get(i).getGenre());
                    System.out.println(genres.get(i).getGenre() + " is selected");
                }
            }
            waiting = false;
        }
    }

    public boolean isWaiting(){
        return waiting;
    }

    public ArrayList<String> getSelectedGenres(){
        return selectedGenres;
    }
}