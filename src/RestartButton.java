//By Pat Needham

import javax.swing.*;
import java.awt.event.*;

public class RestartButton extends JButton
{
	private MinesweeperBoard board;
	
	public RestartButton(MinesweeperBoard b)
	{
		board = b;
		
		addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)
			{
				board.restart();
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
	}
}