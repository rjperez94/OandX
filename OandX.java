import ecs100.*;


/** 
 * O's and X's game.
 * Displays a O's and X's board, and then allows the players
 * to take turns putting O's or X's on the board.
 * Doesn't allow a player to put their mark on a square that is already filled
 * After each turn, it checks the board to see if the player has won at that place.
 * Has a "Restart" button that resets the game to the beginning
 * Requires input from button and mouse.
*/

public class OandX implements UIButtonListener, UIMouseListener{

    /** the board */
    private OandXBoard board;

    /** Whose turn is it, using O and X for the two values */
    private Symbol turn = Symbol.X;

    /** Whether the current game has finished
     */
    private boolean finished;


    /** Construct a new OandX object 
     * @param toWin 
     * @param dims */
    public OandX(String dims, String toWin){
    	board = new OandXBoard(dims,toWin);
        UI.initialise();
        UI.setImmediateRepaint(false);
        UI.addButton("Restart", this);
        UI.addButton("Quit", this);
        UI.setMouseListener(this);
        this.restartGame();
    }

    public void buttonPerformed(String button){
        if (button.equals("Restart")){
            restartGame();
        }
        else if (button.equals("Quit")){
            UI.quit();
        }
    }

    public void mousePerformed(String action, double x, double y){
        //Trace.println("Mouse "+action +" at "+ x +"/"+y);
        String outcome = null;
        if (action.equals("released")){
            // play turn if game not yet finished, and it is a valid place
            if (finished) { return; }
            if (!board.on(x, y)){ return; }
            int[] rowCol = board.rowCol(x,y);
            int row = rowCol[0];
            int col = rowCol[1];
            if (!board.place(turn, row, col)){ return; }
            if (board.checkWin(row, col)){
                finished = true;
                outcome = turn.toString();
            }
            else if (board.finished()){
                finished = true;
                outcome = "draw";
            }
            else {
                turn = turn.other();
            }
            redraw(outcome);
        }
    }
        
    /** Restart: set board to empty, reset the turn, redraw the board */
    public void restartGame(){
        board.reset();
        turn = Symbol.X;
        finished = false;
        redraw(null);
    }


    /** Redraw the board and the current O's and X's*/
    public void redraw(String outcome){
        UI.clearGraphics(false);
        board.draw();
        if (!finished) { //Not finished: say whose turn is next
            UI.setFontSize(18);
            UI.drawString(this.turn+"'s turn", OandXBoard.Left, 20, false);
        }
        else { // report the outcome 
            UI.setFontSize(48);
            if (outcome.equals("draw")){
                UI.drawString("It was a draw", OandXBoard.Left, board.bot + 60);
            }
            else {
            	UI.drawString("Win for "+outcome, OandXBoard.Left, board.bot + 60);
            }
        }
        UI.repaintGraphics();
    }

    public static void main(String[] args){
    	if(args.length == 0 || Integer.parseInt(args[0]) < Integer.parseInt(args[1])) {
    		throw new IllegalArgumentException("First argument must be board dimension. Seccond argument must be how many cells to win. First argument must be >= second argument");
    	}
    	new OandX(args[0], args[1]);
    }        

}
