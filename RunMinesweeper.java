package org.cis1200.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RunMinesweeper implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Minesweeper");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e -> showInstructions());
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        final JButton save = new JButton("Save");
        save.addActionListener(e -> board.save());
        final JButton load = new JButton("Load");
        load.addActionListener(e -> board.load());
        control_panel.add(instructions);
        control_panel.add(reset);
        control_panel.add(save);
        control_panel.add(load);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }

    private void showInstructions() {
        JFrame instructionFrame = new JFrame("Minesweeper Instructions");
        instructionFrame.setSize(300, 300);
        JTextArea instructionsText = new JTextArea();
        try (BufferedReader reader = new BufferedReader(new FileReader("files/instructions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                instructionsText.append(line + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null, "Instructions File couldn't be found",
                    "Error", JOptionPane.ERROR_MESSAGE
            );
        }
        instructionsText.setEditable(false);
        instructionFrame.add(instructionsText);
        Dimension contentSize = instructionsText.getPreferredSize();
        int width = Math.min(contentSize.width + 50, 800);
        int height = Math.min(contentSize.height + 50, 600);
        instructionFrame.setSize(width, height);
        instructionFrame.setVisible(true);

    }

}