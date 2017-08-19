import ecs100.*;

/** 
 * O's and X's board.
 */

public class OandXBoard{

    // The dimensions of the board
    private int Dimen;
    private static final double CellSize = 30;

    public static final double Left = 50;
    public static final double Top = 50;
    public double right;
    public double bot;
    private static final int FontSize = (int) (CellSize*0.8);

    /** The state of the board, as an array of Symbols:
    O, X, or null for empty.
     */
    private Symbol[][] cells;
    
    int win_r1, win_c1, win_r2, win_c2;
	private int toWin;
	
    public OandXBoard(String dims, String toWin) {
    	this.Dimen = Integer.parseInt(dims);
    	this.toWin = Integer.parseInt(toWin);
    	cells = new Symbol[Dimen][Dimen];
    	right = Left + Dimen * CellSize;
        bot = Top + Dimen * CellSize;
	}

	/**
     * Reset the board to be clear of all symbols
     */
    public void reset(){
        cells = new Symbol[Dimen][Dimen];
    }

    /**
     * Is the point (x,y) on the board
     */
    public boolean on(double x, double y){
        return (y>=Top && y < bot  && x>=Left && x < right);
    }

    /**
     * Return the row/col corresponding to the point x,y.
     */
    public int[] rowCol(double x, double y){
        int row = (int) ((y-Top)/CellSize);
        int col = (int) ((x-Left)/CellSize);
        return new int[]{row, col};
    }

    /**
     * Place the given symbol at the given row/col
     * Returns true if symbol is placed successfully.
     * Returns false if the position is invalid, or
     * the symbol is invalid, or there is already a symbol there
     */
    public boolean place(Symbol symbol, int row, int col){
        if (row<0 || row >=Dimen || col<0 || col >=Dimen) { return false; }
        if (!symbol.equals(Symbol.X) && !symbol.equals(Symbol.O)){ return false; }
        if (cells[row][col]!= null) { return false; }
        cells[row][col] = symbol;
        return true;
    }

    /**
     * Check whether the play at row/col resulted in a win for the player.
     */
    public boolean checkWin(int row, int col){
        if (row<0 || row >=Dimen || col<0 || col >=Dimen) { return false; }
        if (cells[row][col]==null) return false;

        if (count( cells[row][col], row, col, 1, 0 ) >= this.toWin) {
            return true;
        }
        if (count( cells[row][col], row, col, 0, 1 ) >= this.toWin) {
            return true;
        }
        if (count( cells[row][col], row, col, 1, -1 ) >= this.toWin) {
            return true;
        }
        if (count( cells[row][col], row, col, 1, 1 ) >= this.toWin) {
            return true;
        }

        return false;
    }
    
    private int count(Symbol symbol, int row, int col, int dirX, int dirY) {
             // Counts the number of the specified player's pieces starting at
             // square (row,col) and extending along the direction specified by
             // (dirX,dirY).  It is assumed that the player has a piece at
             // (row,col).  This method looks at the squares (row + dirX, col+dirY),
             // (row + 2*dirX, col + 2*dirY), ... until it hits a square that is
             // off the board or is not occupied by one of the players pieces.
             // It counts the squares that are occupied by the player's pieces.
             // Furthermore, it sets (win_r1,win_c1) to mark last position where
             // it saw one of the player's pieces.  Then, it looks in the
             // opposite direction, at squares (row - dirX, col-dirY),
             // (row - 2*dirX, col - 2*dirY), ... and does the same thing.
             // Except, this time it sets (win_r2,win_c2) to mark the last piece.
             // Note:  The values of dirX and dirY must be 0, 1, or -1.  At least
             // one of them must be non-zero.
             
          int ct = 1;  // Number of pieces in a row belonging to the player.
          
          int r, c;    // A row and column to be examined
          
          r = row + dirX;  // Look at square in specified direction.
          c = col + dirY;
          while ( r >= 0 && r < 19 && c >= 0 && c < 19 && cells[r][c] == symbol ) {
                  // Square is on the board and contains one of the players's pieces.
             ct++;
             r += dirX;  // Go on to next square in this direction.
             c += dirY;
          }
    
          win_r1 = r - dirX;  // The next-to-last square looked at.
          win_c1 = c - dirY;  //    (The LAST one looked at was off the board or
                              //    did not contain one of the player's pieces.
                              
          r = row - dirX;  // Look in the opposite direction.
          c = col - dirY;
          while ( r >= 0 && r < 19 && c >= 0 && c < 19 && cells[r][c] == symbol ) {
                  // Square is on the board and contains one of the players's pieces.
             ct++;
             r -= dirX;   // Go on to next square in this direction.
             c -= dirY;
          }
    
          win_r2 = r + dirX;
          win_c2 = c + dirY;
          
          // At this point, (win_r1,win_c1) and (win_r2,win_c2) mark the endpoints
          // of the line of pieces belonging to the player.
    
          return ct;
    
       }  // end count()

    /**
     * Check whether the game is over (no more cells to play in)
     */
    public boolean finished(){
        for (int row=0; row<Dimen; row++){
            for (int col=0; col<Dimen; col++){
                if (cells[row][col]==null){
                    return false;    // one cell has a space, so not over yet.
                }
            }
        }
        return true;
    }

    /**
     * Redraw the board. Assumes that the graphics pane has been cleared
     */
    public void draw(){
        UI.setFontSize(FontSize);
        double y = Top;
        for (int row=0; row<Dimen; row++){
            double x = Left;
            for (int col=0; col<Dimen; col++){
                UI.drawRect(x, y, CellSize, CellSize);
                if (cells[row][col]!= null){
                    UI.drawString(cells[row][col].toString(), x+CellSize*.25, y+CellSize*.8);
                }
                x += CellSize;
            }
            y += CellSize;
        }
    }

}
