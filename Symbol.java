import ecs100.*;

/**
 * Symbol is the class of possible values that can be put on a O's and X's
 * board: ie, and O or an X. The two values can be referred to by Symbol.O and
 * Symbol.X They are objects, and you can call the other() method on them.
 * (which will return the othe symbol) This is cleaner than using the strings
 * "O" and "X", since it prevents any values other than O and X from being used
 * by mistake.
 */

public enum Symbol {
	O, X;
	public Symbol other() {
		if (this == O) {
			return X;
		} else {
			return O;
		}
	}
}
