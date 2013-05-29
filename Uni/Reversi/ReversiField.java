import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public abstract class ReversiField extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private boolean _bot;
	private int _size, _activePlayer, _player1Own, _player2Own;
	ReversiButton[][] _field;
	GridLayout layout;

	public ReversiField() {
		super();
		
		layout = new GridLayout();
		layout.setHgap(3);
		layout.setVgap(3);
		
		this.setBackground(new Color(0,0,0,0));
		this.setLayout(layout);
		this.setSize(500, 500);
		this.setVisible(true);
	}
	
	public void init(int Size, boolean Bot){
		_bot = Bot;
		_size = Size;
		_activePlayer = 1;
		_player1Own = 2;
		_player2Own = 2;
		
		this.removeAll();
		layout.setColumns(_size);
		layout.setRows(_size);

		_field = new ReversiButton[_size][_size];
		for (int y = 0; y < _size; y++)
			for (int x = 0; x < _size; x++) {
				ReversiButton btn = new ReversiButton(x, y, this);
				if (x == _size / 2 && y == _size / 2)
					btn.setPlayer(1);
				if (x == _size / 2 - 1 && y == _size / 2)
					btn.setPlayer(2);
				if (x == _size / 2 - 1 && y == _size / 2 - 1)
					btn.setPlayer(1);
				if (x == _size / 2 && y == _size / 2 - 1)
					btn.setPlayer(2);
				_field[x][y] = btn;
				this.add(btn);
			}
		updatePossibleMoves();
	}

	private int checkRow(int x, int y, int xspeed, int yspeed) {
		int result = -1;
		do {
			x += xspeed;
			y += yspeed;
			if (x >= _size || x < 0 || y >= _size || y < 0
					|| _field[x][y].getPlayer() == 0)
				return 0;
			result++;
		} while (_field[x][y].getPlayer() != _activePlayer);
		return result;
	}
	
	private int checkPosition(int x, int y){
		return checkRow(x, y, 1, 0) +
			   checkRow(x, y,-1, 0) +
			   checkRow(x, y, 0, 1) +
			   checkRow(x, y, 0,-1) +
			   checkRow(x, y, 1, 1) +
			   checkRow(x, y, 1,-1) +
			   checkRow(x, y,-1,-1) +
			   checkRow(x, y,-1, 1);
	}

	private boolean CanSet(int x, int y) {
		return _field[x][y].getPlayer() == 0 &&
				checkPosition(x, y) > 0;
	}

	private int updatePossibleMoves() {
		int result = 0;
		for (int y = 0; y < _size; y++)
			for (int x = 0; x < _size; x++) {
				boolean can = CanSet(x, y);
				if (can)
					result++;
				_field[x][y].setCanSet(can);
			}
		return result;
	}

	private void ownRow(ReversiButton field, int xspeed, int yspeed) {
		int x = field.getRow(),
			y = field.getColumn();
		int temp = checkRow(x, y, xspeed, yspeed);
		if (temp > 0) {
			for (int i = 0; i <= temp; i++)
				_field[x + i * xspeed][y + i * yspeed].setPlayer(_activePlayer);
			if (_activePlayer == 1){
				_player1Own += temp;
				_player2Own -= temp;
			}
			else{
				_player1Own -= temp;
				_player2Own += temp;
				}
		}
	}
	
	private void ownPosition(ReversiButton field){
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
	}
	
	private boolean reversiBot(){
		ArrayList<ReversiButton> lstGoodMoves = new ArrayList<ReversiButton>();
		int maxOwn = 0,
			end = _size - 1;
		maxOwn = reversiBotCheck(0  , 0  , maxOwn, lstGoodMoves);
		maxOwn = reversiBotCheck(0  , end, maxOwn, lstGoodMoves);
		maxOwn = reversiBotCheck(end, end, maxOwn, lstGoodMoves);
		maxOwn = reversiBotCheck(end, 0  , maxOwn, lstGoodMoves);
		if(maxOwn > 0){
			ownPosition(lstGoodMoves.get((int)(Math.random() * lstGoodMoves.size())));
			return true;
		}
		for(int i = 1; i < end; i++){
			maxOwn = reversiBotCheck(i  , 0  , maxOwn, lstGoodMoves);
			maxOwn = reversiBotCheck(i  , end, maxOwn, lstGoodMoves);
			maxOwn = reversiBotCheck(0  , i  , maxOwn, lstGoodMoves);
			maxOwn = reversiBotCheck(end, i  , maxOwn, lstGoodMoves);
		}
		if(maxOwn > 0){
			ownPosition(lstGoodMoves.get((int)(Math.random() * lstGoodMoves.size())));
			return true;
		}
		for(int y = 1; y < end; y++)
			for(int x = 1; x < end; x++)
				maxOwn = reversiBotCheck(x, y, maxOwn, lstGoodMoves);
		if(maxOwn > 0){
			ownPosition(lstGoodMoves.get((int)(Math.random() * lstGoodMoves.size())));
			return true;
		}
		return false;
	}
	
	private int reversiBotCheck(int x, int y, int maxOwn, ArrayList<ReversiButton> lstGoodMoves){
		if(_field[x][y].getPlayer() != 0)
			return maxOwn;
		int temp = checkPosition(x,y);
		if(temp > maxOwn){
			lstGoodMoves.clear();
			lstGoodMoves.add(_field[x][y]);
			return temp;
		}
		if(temp == maxOwn)
			lstGoodMoves.add(_field[x][y]);
		return maxOwn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object sender = e.getSource();
		if (sender instanceof ReversiButton) {
			ReversiButton field = (ReversiButton) sender;
			if (field.getCanSet()) {
				ownPosition(field);
				
				_activePlayer = _activePlayer == 1 ? 2 : 1;
				if(_bot){
					if(_activePlayer == 2)
						if(reversiBot()){
							_activePlayer = 1;
							while(updatePossibleMoves() == 0){
								_activePlayer = 2;
								if(!reversiBot()){
									_activePlayer = 0;
									break;
								}
								_activePlayer = 1;
							};
						}
						else{
							_activePlayer = 1;
							if(updatePossibleMoves() == 0)
								_activePlayer = 0;
						}
					else
						if(updatePossibleMoves() == 0){
							_activePlayer = 2;
							if(reversiBot()){
								_activePlayer = 1;
								while(updatePossibleMoves() == 0){
									_activePlayer = 2;
									if(!reversiBot()){
										_activePlayer = 0;
										break;
									}
									_activePlayer = 1;
								};
							}else
								_activePlayer = 0;
						}
				}else{
					if (updatePossibleMoves() == 0) {
						_activePlayer = _activePlayer == 1 ? 2 : 1;
						if (updatePossibleMoves() == 0)
							_activePlayer = 0;
					}
				}
				endMove(_activePlayer, _player1Own, _player2Own);
			}
		}
	}
	
	abstract void endMove(int player, int player1Own, int player2Own);
}
