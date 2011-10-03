//By Pat Needham

import javax.swing.*;
import java.awt.event.*;

public class MBox extends JButton
{	
	private int row;
	private int col;
	private boolean isMine;
	private int num;
	private MinesweeperBoard board;
	private boolean disabled;
	public ImageIcon[] icons;
	private boolean flagged;

	public MBox(int r, int c) {
		row = r;
		col = c;
		isMine = false;
		board = null;
		disabled = false;
		flagged = false;
		icons = new ImageIcon[12];
		icons[0] = new ImageIcon(this.getClass().getClassLoader().getResource("0.gif"));
		icons[1] = new ImageIcon(this.getClass().getClassLoader().getResource("1.gif"));
		icons[2] = new ImageIcon(this.getClass().getClassLoader().getResource("2.gif"));
		icons[3] = new ImageIcon(this.getClass().getClassLoader().getResource("3.gif"));
		icons[4] = new ImageIcon(this.getClass().getClassLoader().getResource("4.gif"));
		icons[5] = new ImageIcon(this.getClass().getClassLoader().getResource("5.gif"));
		icons[6] = new ImageIcon(this.getClass().getClassLoader().getResource("6.gif"));
		icons[7] = new ImageIcon(this.getClass().getClassLoader().getResource("7.gif"));
		icons[8] = new ImageIcon(this.getClass().getClassLoader().getResource("8.gif"));
		icons[9] = new ImageIcon(this.getClass().getClassLoader().getResource("Flag.gif"));
		icons[10] = new ImageIcon(this.getClass().getClassLoader().getResource("Mine.gif"));
		icons[11] = new ImageIcon(this.getClass().getClassLoader().getResource("Unopened.gif"));

		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				board.press();
			}

			public void mouseReleased(MouseEvent e) {
				board.release();

				if (!disabled) {
					if (e.getButton() == MouseEvent.BUTTON1 && !flagged) {
						board.addClick();
						click();
					}
					if (e.getButton() == MouseEvent.BUTTON3) {
						if (board.getClicks() != 0) {
							board.addClick();
						}

						changeFlagged();
					}
				}
			}
		});
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public boolean isMine() {
		return isMine;
	}

	public boolean disabled() {
		return disabled;
	}

	public int getNum() {
		return num;
	}

	public void setMine() {
		isMine = true;
	}

	public void setNum(int number) {
		num = number;
	}

	public void changeFlagged() {
		if (flagged) {
			flagged = false;
			setIcon(icons[11]);
		} else {
			flagged = true;
			setIcon(icons[9]);
		}
	}

	public void click() {
		setEnabled(false);

		if (!isMine()) {
			setDisabledIcon(icons[getNum()]);
		}

		if (toString().equals("0")) {
			for (MBox e : board.getAdjacentMBoxes(this)) {
				if (e.isEnabled() && !e.disabled) {
					e.click();
					e.disable();
					setDisabledIcon(icons[0]);
				}

			}
		}

		board.checkWin();

		if (toString().equals("*")) {
			for (MBox e : board.getAllMines()) {
				e.setEnabled(false);
			}

			board.disableAllMBoxes();
			board.lose();
		}
	}

	public void disable() {
		disabled = true;
	}

	public void putSelfInBoard(MinesweeperBoard b) {
		board = b;
	}

	public void clear() {
		isMine = false;
		num = 0;
		disabled = false;
		setEnabled(true);
		flagged = false;
		setDisabledIcon(icons[11]);
		setIcon(icons[11]);
	}

	public String toString() {
		if (isMine()) {
			return "*";
		} else {
			return "" + getNum();
		}
	}
}