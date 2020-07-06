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
		return pieces[position.getRow()][position.getColum()]; // RETORNAR A MATRIZ "PIECES" NAS POSI��ES
	}
	
	public void placePiece(Piece piece, Position position) {  
		pieces[position.getRow()][position.getColum()] = piece; // POSI�OES NO TABULEIRO ONDE AS PE�AS EST�O
		piece.position = position; // INFORMANDO AS POSI��ES DAS PE�AS 
	}
}
