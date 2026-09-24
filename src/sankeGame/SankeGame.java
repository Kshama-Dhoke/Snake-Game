package sankeGame;

import javax.swing.*;
public class SankeGame extends JFrame{
	
	SankeGame(){
		
		super("Snake Game");

        Board board = new Board();
        add(board);

        pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        board.requestFocusInWindow();
	}



}
