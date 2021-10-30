/**
 * Assignment 6 -- Prisoner's Dilemma -- 2ip90
 * main class
 * 
 * @author Mark Shekhtman 1710133
 * @author Erik Dekker 1665049
 * assignment group 155
 * 
 * 
 * assignment copyright Kees Huizing
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

class PrisonersDilemma {

    private int timerDelay = 1000; // Timer that causes the delay in ms
    private void buildGUI() {
        SwingUtilities.invokeLater(() -> {
            // Creating the frame of the game using JFrame
            JFrame frame = new JFrame("PrisonersDilemma");
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 700);
            frame.setResizable(false);
            frame.setLayout(new BorderLayout());
            // Creating the playing field
            PlayingField playingField = new PlayingField();
            playingField.fillPatchGrid(); // Fills the whole grid with patches
            // Setting gridlayout with rows and columns from playingfield
            playingField.setLayout(new GridLayout(playingField.getGridLength(), playingField.getGridHeight()));
            // Setting the grid layout
            playingField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            playingField.generatePatchGrid(); // Strategy of the patches is randomised

            // Initialsing every individual neighbour of each patch
            playingField.Neighbours();
            for (int x = 0; x < playingField.getGridLength(); x++) {
                for (int y = 0; y < playingField.getGridHeight(); y++) {
                    Patch patch;
                    patch = playingField.getPatch(x, y);
                    patch.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            patch.toggleStrategy();
                        }
                    });
                    playingField.add(playingField.getPatch(x, y));
                }
            }
            frame.add(playingField, BorderLayout.CENTER);
            // Timer activates step()
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    playingField.step();
                }
            };
            Timer timer = new Timer(timerDelay, taskPerformer);
            // Creating a part of the GUI for the buttons and sliders
            JPanel bottomPanel = new JPanel();
            frame.add(bottomPanel, BorderLayout.SOUTH);
            bottomPanel.setLayout(new GridLayout(4, 2));
            bottomPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            // creating a label for the reward slider
            JLabel rewardLabel = new JLabel("      Reward α:    " + playingField.getAlpha());
            bottomPanel.add(rewardLabel);
            // Slider that changes the reward multiplier
            JSlider rewardSlider = new JSlider(0, 30, 10);
            rewardSlider.setMajorTickSpacing(1);
            rewardSlider.setPaintTicks(true);
            rewardSlider.setPaintLabels(true);
            // Creating a table that stores labels on the slider
            Hashtable labelTable = new Hashtable();
            labelTable.put(0, new JLabel("0.0"));
            labelTable.put(10, new JLabel("1.0"));
            labelTable.put(20, new JLabel("2.0"));
            labelTable.put(30, new JLabel("3.0"));
            rewardSlider.setLabelTable(labelTable);
            // Addin the Listener to the slider
            rewardSlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    playingField.setAlpha(source.getValue() / 10.0); 
                    rewardLabel.setText("      Reward α:    " + playingField.getAlpha());
                }
            });
            // Label for the speed slider
            JLabel speedLabel = new JLabel("Speed in milliseconds:     " + timerDelay);
            bottomPanel.add(speedLabel);
            bottomPanel.add(rewardSlider);
            // Creating slider that changes the speed of the game
            JSlider speedSlider = new JSlider(0, 3000, 1000);
            speedSlider.setMajorTickSpacing(500);
            speedSlider.setPaintTicks(true);
            speedSlider.setPaintLabels(true);
            speedSlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    timerDelay = source.getValue();
                    timer.setDelay(timerDelay);
                    speedLabel.setText("Speed in milliseconds:     " + timerDelay);
                }
            });
            bottomPanel.add(speedSlider);
            // Creating "Go" button
            JButton goButton = new JButton("Go");
            goButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // When the timer is running it stops
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                    // Changing the label on the 'Go' button from "Pause"-"Go", 
                    // depending on the state of the timer
                    if (goButton.getText().equals("Go")) {
                        goButton.setText("Pause");
                    } else {
                        goButton.setText("Go");
                    }
                }
            });
            bottomPanel.add(goButton);
            // Creating "Reset" button
            JButton resetButton = new JButton("Reset");
            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    playingField.generatePatchGrid();
                }
            });
            bottomPanel.add(resetButton);
            // Creating the button for the extra rule
            JButton ruleButton = new JButton("     Extra Rule :   Off  ");
            bottomPanel.add(ruleButton);
            ruleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (playingField.getExtraRule()) {
                        playingField.setExtraRule(false);
                        ruleButton.setText("     Extra Rule :   Off  ");
                    } else {
                        playingField.setExtraRule(true);
                        ruleButton.setText("     Extra Rule :   On  ");
                    }
                }
            });
        });
    }
    public static void main(String[] a) {
        (new PrisonersDilemma()).buildGUI();
    }
}