package ikraftsoftware.com;
import javafx.geometry.HorizontalDirection;

import java.awt.*;
import javax.swing.*;

public class UI {
    JFrame window;
    JPanel titleNamePanel,startButtonPanel,mainTextPanel,choiceButtonPanel,playerPanel1,playerPanel2;
    JLabel titleNameLabel,hpLabel,hpNumberLabel,coinLabel,coinNumberLabel,weaponLabel,weaponNameLabel,gregLabel,cordLabel,cordxyLabel;
    JTextArea mainTextArea;
    JButton startButton,choice1,choice2,choice3,choice4,choice5;
    Font titleFont = new Font("Time New Roman",Font.PLAIN,70);
    Font normalFont = new Font("Time New Roman",Font.PLAIN,26);
    Font smallFont = new Font("Time New Roman",Font.BOLD,20);
    Font smallerFont = new Font("Time New Roman",Font.BOLD,13);

    public UI() {
    }

    public void createUI(Game.ChoiceHandler cHandler) {
        //Window
        window = new JFrame();
        window.setSize(900, 800);
        window.setDefaultCloseOperation(3);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
//        window.setLayout(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//        c.fill = GridBagConstraints.VERTICAL;
//



        // Title Screen

        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(100,100,600,500);
        titleNamePanel.setBackground(Color.black);
        titleNameLabel = new JLabel("ADVENTURE");
        titleNameLabel.setForeground(Color.white);
        titleNameLabel.setFont(titleFont);
        gregLabel = new JLabel("iKraftSoftware.com");
        gregLabel.setFont(normalFont);
        gregLabel.setForeground(Color.yellow);
        titleNamePanel.add(titleNameLabel);
        titleNamePanel.add(gregLabel);


        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(300,600,200,100);
        startButtonPanel.setBackground(Color.black);
        startButton = new JButton("START");
        startButton.setBackground(Color.green);
        startButton.setForeground(Color.white);
        startButton.setFont(normalFont);
        startButton.setFocusPainted(false);
        startButton.addActionListener(cHandler);
        startButton.setActionCommand("start");
        startButtonPanel.add(startButton);
        window.add(titleNamePanel);
        window.add(startButtonPanel);
       //

        // Game Screen
        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100,100,600,250);
        mainTextPanel.setBackground(Color.black);

        mainTextArea = new JTextArea("This is the main text area");
        mainTextArea.setBounds(100,100,600,200);
        mainTextArea.setBackground(Color.black);
        mainTextArea.setForeground(Color.magenta);
        mainTextArea.setFont(smallFont);
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);
        mainTextArea.setEditable(false);
        mainTextPanel.add(mainTextArea);
        window.add(mainTextPanel);


        choiceButtonPanel = new JPanel();
        choiceButtonPanel.setBounds(250,350,300,150);
        choiceButtonPanel.setBackground(Color.black);
        choiceButtonPanel.setLayout(new GridLayout(5,1));
        window.add(choiceButtonPanel);


        choice1 = new JButton("choice1");
        choice1.setBackground(Color.black);
        choice1.setForeground(Color.white);
        choice1.setFont(normalFont);
        choice1.setFocusPainted(false);
        choice1.addActionListener(cHandler);
        choice1.setActionCommand("c1");
        choiceButtonPanel.add(choice1);

        choice2 = new JButton("choice2");
        choice2.setBackground(Color.black);
        choice2.setForeground(Color.white);
        choice2.setFont(normalFont);
        choice2.setFocusPainted(false);
        choice2.addActionListener(cHandler);
        choice2.setActionCommand("c2");
        choiceButtonPanel.add(choice2);

        choice3 = new JButton("choice3");
        choice3.setBackground(Color.black);
        choice3.setForeground(Color.white);
        choice3.setFont(normalFont);
        choice3.setFocusPainted(false);
        choice3.addActionListener(cHandler);
        choice3.setActionCommand("c3");
        choiceButtonPanel.add(choice3);

        choice4 = new JButton("choice4");
        choice4.setBackground(Color.black);
        choice4.setForeground(Color.white);
        choice4.setFont(normalFont);
        choice4.setFocusPainted(false);
        choice4.addActionListener(cHandler);
        choice4.setActionCommand("c4");
        choiceButtonPanel.add(choice4);

        choice5 = new JButton("choice5");
        choice5.setBackground(Color.black);
        choice5.setForeground(Color.white);
        choice5.setFont(normalFont);
        choice5.setFocusPainted(false);
        choice5.addActionListener(cHandler);
        choice5.setActionCommand("c5");
        choiceButtonPanel.add(choice5);


        playerPanel1 = new JPanel();
        playerPanel1.setBounds(100,15,600,25);
        playerPanel1.setBackground(Color.black);
        playerPanel1.setForeground(Color.white);
        playerPanel1.setLayout(new GridLayout(1,4));

        playerPanel2 = new JPanel();
        playerPanel2.setBounds(100,40,600,25);
        playerPanel2.setBackground(Color.black);
        playerPanel2.setForeground(Color.white);
        playerPanel2.setLayout(new GridLayout(1,4));

       //Add Status Labels
        hpLabel = new JLabel("Health:");
        hpLabel.setFont(smallFont);
        hpLabel.setForeground(Color.green);
        hpNumberLabel = new JLabel();
        hpNumberLabel.setForeground(Color.white);
        hpNumberLabel.setFont(smallFont);
        playerPanel1.add(hpLabel);
        playerPanel1.add(hpNumberLabel);

        coinLabel = new JLabel("Coins:");
        coinLabel.setFont(smallFont);
        coinLabel.setForeground(Color.green);
        coinNumberLabel = new JLabel();
        coinNumberLabel.setForeground(Color.white);
        coinNumberLabel.setFont(smallFont);
        playerPanel1.add(coinLabel);
        playerPanel1.add(coinNumberLabel);
        window.add(playerPanel1);

        weaponLabel = new JLabel("Weapon:");
        weaponLabel.setFont(smallerFont);
        weaponLabel.setForeground(Color.green);
        playerPanel2.add(weaponLabel);
        weaponNameLabel = new JLabel();
        weaponNameLabel.setForeground(Color.white);
        weaponNameLabel.setFont(smallerFont);
        playerPanel2.add(weaponNameLabel);

        cordLabel = new JLabel("Coordinates");
        cordLabel.setFont(smallerFont);
        cordLabel.setForeground(Color.green);
        playerPanel2.add(cordLabel);
        cordxyLabel = new JLabel();
        cordxyLabel.setForeground(Color.white);
        cordxyLabel.setFont(smallerFont);
        playerPanel2.add(cordxyLabel);
        window.add(playerPanel2);

        window.setVisible(true);


    }
}
