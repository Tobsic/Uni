import java.util.ArrayList;
import java.util.Iterator;


public class BruteForce implements Iterable<String>, Iterator<String>{
	private boolean _hasNext;
	private int _minLength, _maxLength;
	long _seed;
	private ArrayList<Character> _usedChars;

	/**
	 * Create a new Brutforce object that provides a iterator for all possible string combianation of given chars.
	 */
	public BruteForce(){
		this(0, 10);
	}
	/**
	 * Create a new Brutforce object that provides a iterator for all possible string combianation of given chars.
	 * @param MinLength Minimum length of the returned key
	 * @param MaxLength Maximum length of the returned key
	 */
	public BruteForce(int MinLength, int MaxLength){
		this(MinLength, MaxLength, "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray());
	}
	/**
	 * Create a new Brutforce object that provides a iterator for all possible string combianation of given chars.
	 * @param MinLength Minimum length of the returned key
	 * @param MaxLength Maximum length of the returned key
	 * @param Chars Used Chars to generate the Key
	 */
	public BruteForce(int MinLength, int MaxLength, char[] Chars){
		_usedChars = new ArrayList<Character>();
		setMaxLength(MaxLength);
		setMinLength(MinLength);
		setChars(Chars);
	}
	
	/**
	 * Set the minimum length of the key
	 * @param MinLength
	 */
	public void setMinLength(int MinLength){
		resetSeed();
		_minLength = MinLength;
		if(_minLength > _maxLength)
			_maxLength = _minLength;
	}
	
	/**
	 * Set the maximum length of the key
	 * @param MaxLength
	 */
	public void setMaxLength(int MaxLength){
		resetSeed();
		_maxLength = MaxLength;
		if(_maxLength < _minLength)
			_minLength = _maxLength;
	}
	
	/**
	 * Set the char set that's used to generate the keys
	 * @param Chars
	 */
	public void setChars(char[] Chars){
		resetSeed();
		_usedChars.clear();
		for(char c : Chars)
			if(!_usedChars.contains(c))
				_usedChars.add(c);
	}

	/**
	 * Return the Key that can be generate with a specific seed
	 * @param Seed
	 * @return
	 */
	public String getKey(long Seed){
		String result = "";
		int len = _minLength;
		long subseed = Seed;
		while(subseed >= 0)
			subseed -= Math.pow(_usedChars.size(), len++);
		subseed += Math.pow(_usedChars.size(), --len);
		for(int i = 0; i < len; i++)
			result = _usedChars.get((int)((subseed / Math.pow(_usedChars.size(), i)) % _usedChars.size())) + result;
		if(_seed == Seed && len == _maxLength)
			_hasNext = (subseed != Math.pow(_usedChars.size(), _maxLength) - 1);
		return result;
	}
	/**
	 * Get the next Key. If it is over the last, it takes the first
	 * @return
	 */
	public String getNext(){
		if(!_hasNext)
			resetSeed();
		return getKey(++_seed);
	}
	/**
	 * Reset the seed to get the first value with .getNext()
	 */
	public void resetSeed(){
		_seed = -1;
		_hasNext = true;
	}
	
	//Iterable
	
	@Override
	public Iterator<String> iterator() {
		resetSeed();
		return this;
	}
	
	//Iterator
		
	@Override
	public boolean hasNext() {
		return _hasNext;
	}
	@Override
	public String next() {
		return getNext();
	}
	@Override
	public void remove() { }
}
