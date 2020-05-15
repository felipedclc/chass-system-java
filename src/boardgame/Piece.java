package boardgame;

public class Piece {

	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;   // (N�O � NECESSARIO) AUTOMATICAMENTE O JAVA J� CONSIDERA *NULO O VALOR QUE N�O EST� ATRIBU�DO 
	}

	protected Board getBoard() { // APENAS O PACOTE BOARDGAME PODE ACESSAR 
		return board;
	}	
	
}

