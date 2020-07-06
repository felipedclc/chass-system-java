package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public Piece piece(int row, int colum) {
		return pieces[row][colum];  // METODO QUE RETORNA A MATRIZ "PIECES" NA LINHA "ROW" E COLUNA "COLUM"
	}
	
	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColum()]; // RETORNAR A MATRIZ "PIECES" NAS POSIÇÕES
	}
	
	public void placePiece(Piece piece, Position position) {  
		pieces[position.getRow()][position.getColum()] = piece; // POSIÇOES NO TABULEIRO ONDE AS PEÇAS ESTÃO
		piece.position = position; // INFORMANDO AS POSIÇÕES DAS PEÇAS 
	}
}
