import javafx.scene.paint.Color;
import java.util.Random;

/**
 * This should be implemented to include your game control.
 * @author pipWolfe
 */
/** all messages were moved inside the check for ability
 * to perform an action. Seemed like a cleaner output
 * @author scott
 */
public class TetrisGame {
    private final Tetris tetrisApp;
    private final int StartX = 6;
    private final int StartY = 0;
    private int score = 0;
    private boolean gameOver = false;
    private TetrisSquare[] currentPiece = new TetrisSquare[4];
    public TetrisSquare[][] filledboard = new TetrisSquare[16][31];
    
    
    /**
     * Initialize the game. Remove the example code and replace with code
     * that creates a random piece.
     * @param tetrisApp A reference to the application (use to set messages).
     * @param board A reference to the board on which Squares are drawn
     */
    public TetrisGame(Tetris tetrisApp, TetrisBoard board) {
    	currentPiece[0] = new TetrisSquare(board);
        currentPiece[1] = new TetrisSquare(board);
        currentPiece[2] = new TetrisSquare(board);
        currentPiece[3] = new TetrisSquare(board);
    	for(int i = 0; i<15; i++) {
        	for(int j = 0; j<30; j++) {
        		filledboard[i][j] = null;
        	}
        }
    	this.tetrisApp = tetrisApp;
        // You can use this to show the score, etc.
        tetrisApp.setMessage("Game has started!");
        newPiece();
    }

    /**
     * Animate the game, by moving the current tetris piece down.
     */
    public void update() {
    	gameOver();
    	//if the game hasnt ended, it procceeds as usual
    	if(gameOver == false) {
	    	addToFilledBoard();
	    	filledRow();
	    	for(TetrisSquare o : currentPiece) {
	    		o.moveToTetrisLocation(o.getX(), o.getY()+1);
	    	}
	    	tetrisApp.setMessage("Score: " + getScore());
    	}
    	//otherwise prints a ending message
    	else {
    		tetrisApp.setMessage("Game Over, Final Score: " + getScore());
    	}
    }
    
    /**
     * Move the current tetris piece left.
     */
    public void left() {
    	//check for being at the edge or an occupied position
    	boolean atEdge = false;
    	for(TetrisSquare o : currentPiece) {
    		if(o.getX() == 0 || filledboard[o.getX()-1][o.getY()] != null) {
    			atEdge = true;
    		}
    	}
    	if(atEdge == false) {	
	    	for(TetrisSquare o : currentPiece) {
	    		o.moveToTetrisLocation(o.getX()-1, o.getY());
	    	}
	    	//tetrisApp.setMessage("left key was pressed!");
	        System.out.println("left key was pressed!");
    	}
    }

    /**
     * Move the current tetris piece right.
     */
    public void right() {
    	//check for being at the edge or an occupied position
    	boolean atEdge = false;
    	for(TetrisSquare o : currentPiece) {
    		if(o.getX() == 14 || filledboard[o.getX()+1][o.getY()] != null) {
    			atEdge = true;
    		}
    	}
    	if(atEdge == false) {
	    	for(TetrisSquare o : currentPiece) {
	    		o.moveToTetrisLocation(o.getX()+1, o.getY());
	    	}
	        //tetrisApp.setMessage("right key was pressed!");
	        System.out.println("right key was pressed!");
    	}
    }

    /**
     * Drop the current tetris piece.
     * Doesnt really drop it, just speeds up the falling by calling update again
     */
    public void drop() {
    	update();
    }

    /**
     * Rotate the current piece counter-clockwise.
     */
    public void rotateLeft() {
    	//calculate the new rotated coordinates, then decides if they are allowed
    	boolean canRotate = true;
    	for(TetrisSquare o : currentPiece) {
    		int relX = currentPiece[0].getX()-o.getX();
    		int relY = currentPiece[0].getY()-o.getY();
    		int newRelX = relY;
    		int newRelY = -relX;
    		o.newX = currentPiece[0].getX()+newRelX;
    		o.newY = currentPiece[0].getY()+newRelY;
    		for(TetrisSquare O : currentPiece) {
    			if(O.newX < 0 || O.newX > 14) {
    				canRotate = false;
    			}
    		}
    	}
    	if(canRotate) {
    		for(TetrisSquare o : currentPiece) {
    			o.moveToTetrisLocation(o.newX, o.newY);
    		}
    		//tetrisApp.setMessage("rotate left key was pressed!");
	        System.out.println("rotate left key was pressed!");
    	}
    }
    
    /**
     * Rotate the current piece clockwise.
     */
    public void rotateRight() {
    	//calculate the new rotated coordinates, then decides if they are allowed
    	boolean canRotate = true;
    	for(TetrisSquare o : currentPiece) {
    		int relX = currentPiece[0].getX()-o.getX();
    		int relY = currentPiece[0].getY()-o.getY();
    		int newRelX = -relY;
    		int newRelY = relX;
    		o.newX = currentPiece[0].getX()+newRelX;
    		o.newY = currentPiece[0].getY()+newRelY;
    		for(TetrisSquare O : currentPiece) {
    			if(O.newX < 0 || O.newX > 14) {
    				canRotate = false;
    			}
    		}
    	}
    	if(canRotate) {
    		for(TetrisSquare o : currentPiece) {
    			o.moveToTetrisLocation(o.newX, o.newY);
    		}
    		//tetrisApp.setMessage("rotate right key was pressed!");
	        System.out.println("rotate right key was pressed!");
    	}
    }
    
    /**
     * Creates a new random piece
     * all pieces are defined such that currentPiece[0] is the center square
     */
    private void newPiece() {
    	Random rand = new Random();
        int pick = rand.nextInt(7)+1;
        /**  */
    	switch(pick){
        	//square
        	case 1:
        		currentPiece[0].moveToTetrisLocation(StartX, StartY);
        		currentPiece[1].moveToTetrisLocation(StartX, StartY+1);
        		currentPiece[2].moveToTetrisLocation(StartX+1, StartY);
        		currentPiece[3].moveToTetrisLocation(StartX+1, StartY+1);
        		for(TetrisSquare o : currentPiece) {
        			//each shape has a specific color and border
        			o.setColor(Color.RED);
        			o.setStroke(Color.CRIMSON);
        		}
        		break;
        	//s-right
        	case 2:
        		currentPiece[0].moveToTetrisLocation(StartX, StartY);
        		currentPiece[1].moveToTetrisLocation(StartX+1, StartY);
        		currentPiece[2].moveToTetrisLocation(StartX, StartY+1);
        		currentPiece[3].moveToTetrisLocation(StartX-1, StartY+1);
        		for(TetrisSquare o : currentPiece) {
        			o.setColor(Color.ROYALBLUE);
        			o.setStroke(Color.BLUE);
        		}
        		break;
        	//s-left
        	case 3:
        		currentPiece[0].moveToTetrisLocation(StartX, StartY);
        		currentPiece[1].moveToTetrisLocation(StartX-1, StartY);
        		currentPiece[2].moveToTetrisLocation(StartX, StartY+1);
        		currentPiece[3].moveToTetrisLocation(StartX+1, StartY+1);
        		for(TetrisSquare o : currentPiece) {
        			o.setColor(Color.BLUE);
        			o.setStroke(Color.NAVY);
        		}
        		break;
        	//t
        	case 4:
        		currentPiece[1].moveToTetrisLocation(StartX, StartY);
        		currentPiece[0].moveToTetrisLocation(StartX, StartY+1);
        		currentPiece[2].moveToTetrisLocation(StartX-1, StartY+1);
        		currentPiece[3].moveToTetrisLocation(StartX+1, StartY+1);
        		for(TetrisSquare o : currentPiece) {
        			o.setColor(Color.FORESTGREEN);
        			o.setStroke(Color.DARKGREEN);
        		}
        		break;
        	//L-right
        	case 5:
        		currentPiece[1].moveToTetrisLocation(StartX, StartY);
        		currentPiece[0].moveToTetrisLocation(StartX, StartY+1);
        		currentPiece[2].moveToTetrisLocation(StartX, StartY+2);
        		currentPiece[3].moveToTetrisLocation(StartX+1, StartY+2);
        		for(TetrisSquare o : currentPiece) {
        			o.setColor(Color.INDIANRED);
        			o.setStroke(Color.DARKRED);
        		}
        		break;
        	//L-left
        	case 6:
        		currentPiece[1].moveToTetrisLocation(StartX, StartY);
        		currentPiece[0].moveToTetrisLocation(StartX, StartY+1);
        		currentPiece[2].moveToTetrisLocation(StartX, StartY+2);
        		currentPiece[3].moveToTetrisLocation(StartX-1, StartY+2);
        		for(TetrisSquare o : currentPiece) {
        			o.setColor(Color.PALEGREEN);
        			o.setStroke(Color.GREEN);
        		}
        		break;
        	case 7:
        		currentPiece[1].moveToTetrisLocation(StartX, StartY);
        		currentPiece[0].moveToTetrisLocation(StartX, StartY+1);
        		currentPiece[2].moveToTetrisLocation(StartX, StartY+2);
        		currentPiece[3].moveToTetrisLocation(StartX, StartY+3);
        		for(TetrisSquare o : currentPiece) {
        			o.setColor(Color.AQUA);
        			o.setStroke(Color.CORNFLOWERBLUE);
        		}
        		break;
        }
    	
    }
    
    /**
     * Adds pieces that have reached their destination to
     * the board where peiece are stored while not moving
     */
    private void addToFilledBoard() {
    	//checks to see if adding is required
    	boolean add = false;
    	for(TetrisSquare o: currentPiece) {
    		if(o.getY() >= 29 || filledboard[o.getX()][o.getY()+1] != null) {
    			add = true;
    		}
    	}
    	if(add) {
    		for(TetrisSquare o: currentPiece) {
    			filledboard[o.getX()][o.getY()] = new TetrisSquare(Tetris.tetrisBoard);
    			filledboard[o.getX()][o.getY()].moveToTetrisLocation(o.getX(), o.getY());
    			filledboard[o.getX()][o.getY()].setColor(o.getColor());
    			filledboard[o.getX()][o.getY()].setStroke(o.getStroke());
    		}
    		newPiece();
    	}
    }
    
    /**
     * Checks for and removes filled rows
     */
    private void filledRow() {
    	for(int i = 0; i<30; i++) {
    		boolean rowFilled = true;
    		for(int j = 0; j<15; j++) {
    			if(filledboard[j][i] == null) {
    				rowFilled = false;
    			}
    		}
    		if(rowFilled == true) {
    			for(int j = 0; j<15; j++) {
    				//remove the row and reset the space in the filledboard
    				filledboard[j][i].removeFromDrawing();
    				filledboard[j][i] = null;
    				
    			}
    			for(int k = i; k>0; k--) {
    				for(int g = 0; g<15; g++) {
    					if(filledboard[g][k] != null) {
    						//moves all squares above the removed row down
    						filledboard[g][k+1] = filledboard[g][k];
    						filledboard[g][k+1].moveToTetrisLocation(g,k+1);
    						filledboard[g][k] = null;
    						
    					}
    				}
    			}
    			//increases the score by 100 for each removed row
    			score = score + 100;
    		}
    	}
    }
    
    /**
     * returns the score
     * @return score
     */
    private int getScore() {
    	return score;
    }
    
    /**
     * Checks to see if the new piece will spawn on top of another, thus ending the game
     */
    private void gameOver() {
    	if(filledboard[6][1] != null) {
    		gameOver = true;
    	}
    }
}
