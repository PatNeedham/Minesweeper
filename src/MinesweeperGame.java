//By Pat Needham

import javax.swing.*;
import java.util.*;

public class MinesweeperGame
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.println("Do you want to play beginner (b), intermediate (i), or EXPERT (e)?");
		String input = in.next();
		MinesweeperBoard board;
		
		if (input.equals("b"))
			board = new MinesweeperBoard(9, 9, 10);
		else if (input.equals("i"))
			board = new MinesweeperBoard(16, 16, 40);
		else if (input.equals("e"))
			board = new MinesweeperBoard(30, 16, 99);
		else
			board = new MinesweeperBoard(30, 30, 100);
		
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.show();
	}
}