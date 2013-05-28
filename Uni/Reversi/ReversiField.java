import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public abstract class ReversiField extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private int _size, _activePlayer, _player1Own, _player2Own;
	ReversiButton[][] _field;

	public ReversiField(int size) {
		super();
		this.setBackground(new Color(0,0,0,0));
		GridLayout layout = new GridLayout(size, size, 3, 3);
		this.setLayout(layout);

		_size = size;
		_activePlayer = 1;
		_player1Own = 2;
		_player2Own = 2;

		_field = new ReversiButton[size][size];
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++) {
				ReversiButton btn = new ReversiButton(x, y, this);
				if (x == size / 2 && y == size / 2)
					btn.setPlayer(1);
				if (x == size / 2 - 1 && y == size / 2)
					btn.setPlayer(2);
				if (x == size / 2 - 1 && y == size / 2 - 1)
					btn.setPlayer(1);
				if (x == size / 2 && y == size / 2 - 1)
					btn.setPlayer(2);
				_field[x][y] = btn;
				this.add(btn);
			}
		updatePossibleMoves();
		this.setSize(500, 500);
		this.setVisible(true);
	}

	boolean CanSet(int player, int x, int y) {
		if (player != 1 && player != 2 || _field[x][y].getPlayer() != 0)
			return false;
		return checkRow(player, x, y, 1, 0) > 0
				|| checkRow(player, x, y, -1, 0) > 0
				|| checkRow(player, x, y, 0, 1) > 0
				|| checkRow(player, x, y, 0, -1) > 0
				|| checkRow(player, x, y, 1, 1) > 0
				|| checkRow(player, x, y, 1, -1) > 0
				|| checkRow(player, x, y, -1, -1) > 0
				|| checkRow(player, x, y, -1, 1) > 0;
	}

	int checkRow(int x, int y, int xspeed, int yspeed) {
		return checkRow(_field[x][y].getPlayer(), x, y, xspeed, yspeed);
	}

	int checkRow(int player, int x, int y, int xspeed, int yspeed) {
		int result = -1;
		do {
			x += xspeed;
			y += yspeed;
			if (x >= _size || x < 0 || y >= _size || y < 0
					|| _field[x][y].getPlayer() == 0)
				return -1;
			result++;
		} while (_field[x][y].getPlayer() != player);
		return result;
	}

	void ownRow(ReversiButton field, int xspeed, int yspeed) {
		int player = field.getPlayer(), x = field.getRow(), y = field
				.getColumn();
		int temp = checkRow(x, y, xspeed, yspeed);
		if (temp > 0) {
			for (int i = 0; i <= temp; i++)
				_field[x + i * xspeed][y + i * yspeed].setPlayer(player);
			if (player == 1){
				_player1Own += temp;
				_player2Own -= temp;
			}
			else{
				_player1Own -= temp;
				_player2Own += temp;
				}
		}
	}

	public int updatePossibleMoves() {
		int result = 0;
		for (int y = 0; y < _size; y++)
			for (int x = 0; x < _size; x++) {
				boolean can = CanSet(_activePlayer, x, y);
				if (can)
					result++;
				_field[x][y].setCanSet(can);
			}
		return result;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object sender = e.getSource();
		if (sender instanceof ReversiButton) {
			ReversiButton field = (ReversiButton) sender;
			if (field.getCanSet()) {
				field.setPlayer(_activePlayer);
				if (_activePlayer == 1)
					_player1Own++;
				else
					_player2Own++;
				ownRow(field, 1, 0);
				ownRow(field, -1, 0);
				ownRow(field, 0, 1);
				ownRow(field, 0, -1);
				ownRow(field, 1, 1);
				ownRow(field, 1, -1);
				ownRow(field, -1, -1);
				ownRow(field, -1, 1);

				_activePlayer = _activePlayer == 1 ? 2 : 1;
				if (updatePossibleMoves() == 0) {
					_activePlayer = _activePlayer == 1 ? 2 : 1;
					if (updatePossibleMoves() == 0)
						_activePlayer = 0;
				}
				endMove(_activePlayer, _player1Own, _player2Own);
			}
		}
	}
	
	abstract void endMove(int player, int player1Own, int player2Own);
}
