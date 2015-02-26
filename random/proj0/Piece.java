/** Piece class and methods
 * Zach Strenfel
 * CS61B
 */

public class Piece {
	
		private boolean isFire;
		private Board b;
		private int x;
		private int y;
		private String type;
		private boolean kinged = false;

		private boolean jumped;

	public Piece(boolean isFire, Board b, int x, int y, String type) {
		this.isFire = isFire;
		this.b = b;
		this.x = x;
		this.y = y;
		this.type = type;
	}

	/* determines if piece is fire or not */
	public boolean isFire() {
		if(this.isFire == true) {
			return true;
		}
		return false;
	}

	/*return 0 if piece is a fire piece, or 1 if not */
	public int side() {
		if(this.isFire == true) {
			return 0;
		} else {
			return 1;
		}
	}

	/*return whether or not a piece has been crowned*/
	public boolean isKing() {
		if(this.kinged) {
			return true;
		} else {
			return false;
		}
	}

	/*returns whether a piece is tha bomb */
	public boolean isBomb() {
		if(this.type == "bomb") {
			return true;
		} else {
			return false;
		}
	}

	/*returns whether a piece uses protection */
	public boolean isShield() {
		if(this.type == "shield") {
			return true;
		} else {
			return false;
		}
	}

	/* moves a piece to (x,y) assuming the move is valid */
	public void move(int x, int y) {

		if(kingMe(y)){
			this.kinged = true;
		}

		if(Math.abs(this.y - y) == 2) {
			System.out.println("jumped!");
			if(this.isBomb()){
				bombDotCom(x, y);
				jumped = true;
				b.remove(this.x,this.y);
			} else {
				if((this.x - x) < 0 && (this.y - y) < 0) upRight(x, y, false);
				else if((this.x - x) > 0 && (this.y - y) < 0) upLeft(x, y, false);
				else if((this.x - x) < 0 && (this.y - y) > 0) downRight(x ,y, false);
				else downLeft(x, y, false);
				jumped = true;
				this.x = x;
				this.y = y;
				b.place(this, x, y);
			} 
		}
		 else {
			this.x = x;
			this.y = y;
			b.place(this, x, y);
		}
	}


	/* helper method that controls a bomb's jump */
	private void bombDotCom(int x, int y) {
		if((this.x - x) < 0 && (this.y - y) < 0) {
			upRight(x, y, false);
			upLeft(x,y, true);
			downRight(x,y, true);
			downLeft(x,y, true);
		} else if((this.x - x) > 0 && (this.y - y) < 0) {
			upRight(x,y, true);
			upLeft(x, y, false);
			downRight(x,y, true);
			downLeft(x,y, true);
		} else if((this.x - x) < 0 && (this.y - y) > 0) {
			upRight(x,y, true);
			upLeft(x,y, true);
			downRight(x ,y, false);
			downLeft(x,y, true);
		} else {
			upRight(x,y, true);
			upLeft(x,y, true);
			downRight(x,y, true);
			downLeft(x, y, false);
		}
	}

	/* helper method to determine action when moving up and to the right */
	private void upRight(int x, int y, boolean bomb) {
		if(bomb){
			if(b.pieceAt(x - 1, y - 1) != null){
				if(!b.pieceAt(x - 1, y - 1).isShield()) {
					b.remove(x - 1, y - 1);
					b.remove(x,y);
				}
			}
		} else{
			b.remove(x - 1, y - 1);
		}
	}

	/* helper method to determine action when moving up and to the left */
	private void upLeft(int x, int y, boolean bomb) {
		if(bomb){
			if(b.pieceAt(x + 1, y - 1) != null){
				if(!b.pieceAt(x + 1, y - 1).isShield()) {
					b.remove(x + 1, y - 1);
					b.remove(x,y);
				}
			}
		} else{
			b.remove(x + 1, y - 1);
		}
	}

	/* helper method to determine action when moving down and to the right */
	private void downRight(int x, int y, boolean bomb) {
		if(bomb){
			if(b.pieceAt(x - 1, y + 1) != null){
				if(!b.pieceAt(x - 1, y + 1).isShield()) {
					b.remove(x - 1, y + 1);
					b.remove(x,y);
				}
			}
		} else{
			b.remove(x - 1, y + 1);
		}
	}

	/* helper method to determine action when moving down and to the left */
	private void downLeft(int x, int y, boolean bomb) {
		if(bomb){
			if(b.pieceAt(x + 1, y + 1) != null){
				if(!b.pieceAt(x + 1, y + 1).isShield()) {
					b.remove(x + 1, y + 1);
					b.remove(x,y);
				}
			}
		} else{
				b.remove(x + 1, y + 1);
		}
	}

	/* boolean that provides whether a piece should be kinged */
	private boolean kingMe(int y) {
		if(y == 7 && this.isFire()) return true;
		else if(y == 0 && !this.isFire()) return true;
		else return false;
	}

	/*returns whether or not this piece captured another */
	public boolean hasCaptured() {
		if(jumped == true) return true;
		else return false;
	}

	
	/*called at the end of each turn on the piece that moved.
	returns hasCaptured() value back to false */
	public void doneCapturing() {
		jumped = false;
	}

}

/* thats all folks ╰(.•́͜ʖ•̀.)╯ */




