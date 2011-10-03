//By Pat Needham

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.DecimalFormat;

public class MinesweeperBoard extends JFrame implements ActionListener
{
	public MBox[][] boxes;
	public int numRows;
	public int numCols;
	private int mines;
	private int minesLeft;
	private final int SIZE = 16;
	public ArrayList<MBox> mineBoxes;
	public Container cont;
	private javax.swing.Timer clock;
	private int clicks;
	private JTextField timer;
	private double seconds;
	private DecimalFormat fmt;
	private ImageIcon[] smileys;
	private RestartButton button;
	private boolean active;
	
	public MinesweeperBoard(int rows, int cols, int minesInGame)
	{
		numRows = rows;
		numCols = cols;
		boxes = new MBox[numRows][numCols];
		mines = minesInGame;
		active = false;
		clock = new javax.swing.Timer(100, this);
		timer = new JTextField("0");
		timer.setHorizontalAlignment(JTextField.CENTER);
		timer.setEditable(false);
		fmt = new DecimalFormat("0.#");
		cont = getContentPane();
		cont.setLayout(null);
		setSize(numRows * SIZE + 20, numCols * SIZE + 105);
		timer.setBounds(getWidth() - 70, 0, 50, 25);
		cont.add(timer);
			
		for (int i = 0; i < numRows; i++)
		{
			for (int j = 0; j < numCols; j++)
			{
				boxes[i][j] = new MBox(i, j);
				boxes[i][j].setBounds(i * SIZE + 5, j * SIZE + 65, SIZE, SIZE);
				boxes[i][j].putSelfInBoard(this);
				cont.add(boxes[i][j]);
			}
		}
		
		smileys = new ImageIcon[4];
		smileys[0] = new ImageIcon(this.getClass().getClassLoader().getResource("SmileyFace.GIF"));
		smileys[1] = new ImageIcon(this.getClass().getClassLoader().getResource("SmileyDown.GIF"));
		smileys[2] = new ImageIcon(this.getClass().getClassLoader().getResource("SmileyDead.GIF"));
		smileys[3] = new ImageIcon(this.getClass().getClassLoader().getResource("SmileyWon.GIF"));
		
		button = new RestartButton(this);
		button.setBounds(getWidth() / 2 - 13, 10, 26, 26);
		cont.add(button);
		
		addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e)
			{
				if (active)
				{
					button.setIcon(smileys[1]);
				}
			}
			public void mouseReleased(MouseEvent e)
			{
				if (active)
				{
					button.setIcon(smileys[0]);
				}
			}
		});
		
		restart();
	}
	
	public void press()
	{
		if (active)
		{
			button.setIcon(smileys[1]);
		}
	}
	
	public void release()
	{
		if (active)
		{
			button.setIcon(smileys[0]);
		}
	}
	
	public void addClick()
	{
		if (clicks == 0)
		{
			clock.start();
			active = true;
		}
		
		clicks++;
	}
	
	public int getClicks()
	{
		return clicks;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		seconds += 0.1;
		timer.setText("" + fmt.format(seconds));
	}
	
	private void setNumbers()
	{
		for (int i = 0; i < numRows; i++)
		{
			for (int j = 0; j < numCols; j++)
			{
				if (!boxes[i][j].isMine())
				{
					setNumber(boxes[i][j]);
				}
			}
		}
	}
	
	private void setNumber(MBox b)
	{
		int num = 0;
		
		for (int i = b.getRow() - 1; i <= b.getRow() + 1; i++)
		{
			for (int j = b.getCol() - 1; j <= b.getCol() + 1; j++)
			{
				if (isValid(i, j))
				{
					if (boxes[i][j].isMine())
					{
						num++;
					}
				}
				
			}
		}
		
		b.setNum(num);
	}
	
	public boolean isValid(int row, int col)
	{
		if ((row >= 0 && row < numRows) && (col >= 0 && col < numCols))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void disableAllMBoxes()
	{
		for (int i = 0; i < numRows; i++)
		{
			for (int j = 0; j < numCols; j++)
			{
				boxes[i][j].disable();
				boxes[i][j].setEnabled(false);
			}
		}
	}
	
	public void putAllMinesUp()
	{
		for (MBox e : mineBoxes)
		{
			e.setDisabledIcon(e.icons[10]);
		}
	}
	
	public void putAllFlagsUp()
	{
		for (MBox e : mineBoxes)
		{
			e.setDisabledIcon(e.icons[9]);
		}
	}
	
	public ArrayList<MBox> getAllMines()
	{
		return mineBoxes;
	}
	
	public ArrayList<MBox> getAdjacentMBoxes(MBox box)
	{
		ArrayList<MBox> boxList = new ArrayList<MBox>();
		
		for (int i = box.getRow() - 1; i <= box.getRow() + 1; i++)
		{
			for (int j = box.getCol() - 1; j <= box.getCol() + 1; j++)
			{
				if (isValid(i, j) && !(i == box.getRow() && j == box.getCol()))
				{
					boxList.add(boxes[i][j]);
				}
			}
		}
		
		return boxList;
	}
	
	public boolean checkWin()
	{
		for (int i = 0; i < numRows; i++)
		{
			for (int j = 0; j < numCols; j++)
			{
				if (!boxes[i][j].isMine() && !boxes[i][j].disabled())
				{
					return false;
				}
			}
		}
		
		win();
		return true;
	}
	
	public void win()
	{
		clock.stop();
		disableAllMBoxes();
		System.out.println("Won in " + timer.getText() + " seconds having clicked " + clicks + " times!");
		putAllFlagsUp();
		button.setIcon(smileys[3]);
		active = false;
	}
	
	public void lose()
	{
		clock.stop();
		disableAllMBoxes();
		System.out.println("Lost in " + timer.getText() + " seconds having clicked " + clicks + " times!");
		putAllMinesUp();
		button.setIcon(smileys[2]);
		active = false;
	}
	
	public void restart()
	{
		clicks = 0;
		seconds = 0.0;
		clock.stop();
		timer.setText("0");
		mineBoxes = new ArrayList<MBox>();
		button.setIcon(smileys[0]);
		
		for (int i = 0; i < numRows; i++)
		{
			for (int j = 0; j < numCols; j++)
			{
				boxes[i][j].clear();
			}
		}
		
		minesLeft = mines;
		
		while (minesLeft > 0)
		{
			int r = (int) (Math.random() * numRows);
			int c = (int) (Math.random() * numCols);
			
			if (!boxes[r][c].isMine())
			{
				boxes[r][c].setMine();
				mineBoxes.add(boxes[r][c]);
				minesLeft--;
			}
		}
		
		setNumbers();
	}
}