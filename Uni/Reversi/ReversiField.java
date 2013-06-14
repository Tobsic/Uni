import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * 
 * @author Tobias Brosge (539713)
 * @author Veit Heller (539501)
 * 
 */
public abstract class ReversiField extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private boolean _bot;
	private int _size, _activePlayer, _player1Own, _player2Own;
	ReversiButton[][] _field;
	GridLayout layout;

	/**
	 * Initialize a new panel and set the grid layout
	 */
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
	
	/**
	 * Initialize a new play field with ReversiButton's
	 * @param Size Count of rows and columns
	 * @param Bot Enable the bot if it is true
	 */
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

	/**
	 * Get the numbers of ownable positions by the active player in one direction
	 * @param Row The row where to check if the active player would set
	 * @param Column The column where to check if the active player would set
	 * @param RowsPerStep Number to increase/decrease the row (1, 0 or -1 makes sense)
	 * @param ColumnsPerStep Number to increase/decrease the column (1, 0 or -1 makes sense)
	 * @return
	 */
	private int checkRow(int Row, int Column, int RowsPerStep, int ColumnsPerStep) {
		int result = -1;
		do {
			Row += RowsPerStep;
			Column += ColumnsPerStep;
			if (Row >= _size || Row < 0 || Column >= _size || Column < 0
					|| _field[Row][Column].getPlayer() == 0)
				return 0;
			result++;
		} while (_field[Row][Column].getPlayer() != _activePlayer);
		return result;
	}
	
	/**
	 * Get the numbers of ownable positions by the active player in all directions
	 * @param Row The row where to check if the active player would set
	 * @param Column The column where to check if the active player would set
	 * @return
	 */
	private int checkPosition(int Row, int Column){
		return checkRow(Row, Column, 1, 0) +
			   checkRow(Row, Column,-1, 0) +
			   checkRow(Row, Column, 0, 1) +
			   checkRow(Row, Column, 0,-1) +
			   checkRow(Row, Column, 1, 1) +
			   checkRow(Row, Column, 1,-1) +
			   checkRow(Row, Column,-1,-1) +
			   checkRow(Row, Column,-1, 1);
	}

	/**
	 * Check if the active player can set on a specific position
	 * @param Row Row where to check if the active player can set
	 * @param Column Column where to check if the active player can set
	 * @return
	 */
	private boolean CanSet(int Row, int Column) {
		return _field[Row][Column].getPlayer() == 0 &&
				checkPosition(Row, Column) > 0;
	}

	/**
	 * Activate the setable fields
	 * @return The numbers of possible moves
	 */
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

	/**
	 * Active player adopt all positions in one direction
	 * @param field Start position
	 * @param RowsPerStep Numbers to increase/decrease the row (1, 0 or -1 makes sense)
	 * @param ColumnsPerStep Numbers to increase/decrease the cloumn (1, 0 or -1 makes sense)
	 */
	private void ownRow(ReversiButton field, int RowsPerStep, int ColumnsPerStep) {
		int x = field.getRow(),
			y = field.getColumn();
		int temp = checkRow(x, y, RowsPerStep, ColumnsPerStep);
		if (temp > 0) {
			for (int i = 0; i <= temp; i++)
				_field[x + i * RowsPerStep][y + i * ColumnsPerStep].setPlayer(_activePlayer);
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
	
	/**
	 * Active player own all possible positions around the given position
	 * @param field The position, where the active player had set
	 */
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
	
	/**
	 * Do the best move (not best at all, but a good)
	 * @return True if the bot had set, false if there is no ownable position
	 */
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
	
	/**
	 * Add a position to a list of positions, when the move positions that will own equals the most ownable positions till now.
	 * Clear the list and add this position, if the this move could own more positions than the last highest amount.
	 * Do nothing if this move own less than the last highest ownable positions.
	 * @param Row The row where to check if the bot set
	 * @param Column The column where to check if the bot set
	 * @param maxOwn The highest amount of ownable positions till now
	 * @param lstGoodMoves List of positions that could own to get the last highest ownable positions
	 * @return Amount of the positions that ownd by one of the positions out of the given list
	 */
	private int reversiBotCheck(int Row, int Column, int maxOwn, ArrayList<ReversiButton> lstGoodMoves){
		if(_field[Row][Column].getPlayer() != 0)
			return maxOwn;
		int temp = checkPosition(Row,Column);
		if(temp > maxOwn){
			lstGoodMoves.clear();
			lstGoodMoves.add(_field[Row][Column]);
			return temp;
		}
		if(temp == maxOwn)
			lstGoodMoves.add(_field[Row][Column]);
		return maxOwn;
	}

	/**
	 * Own a position if the player click on it
	 * if there is a second player, set him to the active player and wait for an action
	 * If there is a bot, let him make a move
	 * Perform endMove to update the states and end the game when nobody can set
	 */
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
	
	/**
	 * Performed if a move ends. Should be overridden to show states
	 * @param player The active player, 0 if the game is over
	 * @param player1Own numbers of positions that player 1 own
	 * @param player2Own numbers of positions that player 2 own
	 */
	abstract void endMove(int player, int player1Own, int player2Own);
}
