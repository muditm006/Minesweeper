package org.cis1200.minesweeper;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class instantiates a GameBoard object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */

public class GameBoard extends JPanel {

    private final Minesweeper ms; // model for the game
    private final JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_HEIGHT = 400;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        ms = new Minesweeper(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();
                if (p.getX() <= 500 && p.getY() <= 400) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        ms.playTurn(p.x / 50, p.y / 50);
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        ms.placeFlag(p.x / 50, p.y / 50);
                    }
                    updateStatus(); // updates the status JLabel
                    repaint(); // repaints the game board
                }
            }
            // updates the model given the coordinates of the mouseclick
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        ms.reset();
        status.setText("Number of Flags: " + ms.getNumFlags());
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void save() {
        int savedStatus = ms.save();
        if (savedStatus == 1) {
            status.setText("Status Saved! Number of Flags: " + ms.getNumFlags());
            repaint();
            requestFocusInWindow();
        } else if (savedStatus == 0) {
            status.setText("Cannot Save When Game Is Over!");
            repaint();
            requestFocusInWindow();
        } else {
            JOptionPane.showMessageDialog(
                    null, "File couldn't be found",
                    "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void load() {
        boolean loadSuccessful = ms.load();
        if (loadSuccessful) {
            status.setText("Game Loaded! Number of Flags: " + ms.getNumFlags());
            repaint();
            requestFocusInWindow();
        } else {
            JOptionPane.showMessageDialog(
                    null, "File couldn't be found",
                    "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (ms.checkWinner()) {
            status.setText("You Win!");
        } else if (ms.getGameOver()) {
            status.setText("You Lost!");
        } else {
            status.setText("Number of Flags: " + ms.getNumFlags());
        }
    }

    // Draws the game board.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        // Draws board grid
        g.drawLine(50, 0, 50, 400);
        g.drawLine(100, 0, 100, 400);
        g.drawLine(150, 0, 150, 400);
        g.drawLine(200, 0, 200, 400);
        g.drawLine(250, 0, 250, 400);
        g.drawLine(300, 0, 300, 400);
        g.drawLine(350, 0, 350, 400);
        g.drawLine(400, 0, 400, 400);
        g.drawLine(450, 0, 450, 400);
        g.drawLine(500, 0, 500, 400);
        g.drawLine(0, 50, 500, 50);
        g.drawLine(0, 100, 500, 100);
        g.drawLine(0, 150, 500, 150);
        g.drawLine(0, 200, 500, 200);
        g.drawLine(0, 250, 500, 250);
        g.drawLine(0, 300, 500, 300);
        g.drawLine(0, 350, 500, 350);
        g.drawLine(0, 400, 500, 400);

        // Draws Tiles
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 8; j++) {
                if (ms.getCellLost(i, j)) {
                    g.setColor(Color.BLACK);
                    g.fillRect(50 * i + 1, 50 * j + 1, 49, 49);
                } else if (!ms.getCellRevealed(i, j)) {
                    if (ms.getCellFlagged(i, j)) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.GRAY);
                    }
                    g.fillRect(50 * i + 1, 50 * j + 1, 49, 49);
                } else {
                    int mines = ms.getCellAdjacentMines(i, j);
                    if (mines != 0) {
                        BufferedImage image = null;
                        try {
                            image = ImageIO.read(new File("files/minesweeper" + mines + ".png"));
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(
                                    null, "Image File couldn't be found",
                                    "Error", JOptionPane.ERROR_MESSAGE
                            );
                        }
                        if (image != null) {
                            g.drawImage(image, 50 * i + 1, 50 * j + 1, 49, 49, null);
                        }
                    } else {
                        g.setColor(Color.decode("#B9B9B9"));
                        g.fillRect(50 * i + 1, 50 * j + 1, 49, 49);
                    }
                }
            }
        }

    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
