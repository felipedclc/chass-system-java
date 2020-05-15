package boardgame;

public class Piece {

	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;   // (NÃO É NECESSARIO) AUTOMATICAMENTE O JAVA JÁ CONSIDERA *NULO O VALOR QUE NÃO ESTÁ ATRIBUÍDO 
	}

	protected Board getBoard() { // APENAS O PACOTE BOARDGAME PODE ACESSAR 
		return board;
	}	
	
}

