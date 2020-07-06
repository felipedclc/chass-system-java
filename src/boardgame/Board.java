package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		if(rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column"); // PROGRAMA��O DEFENSIVA
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public Piece piece(int row, int column) {
		if(!positionExists(row, column)) {
			throw new BoardException("Position not on the board"); // PROGRAMA��O DEFENSIVA
		}
		return pieces[row][column];  // METODO QUE RETORNA A MATRIZ "PIECES" NA LINHA "ROW" E COLUNA "column"
	}
	
	public Piece piece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board"); 
		}
		return pieces[position.getRow()][position.getColumn()]; // RETORNAR A MATRIZ "PIECES" NAS POSI��ES
	}
	
	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position" + position); 
		}
		pieces[position.getRow()][position.getColumn()] = piece; // POSI�OES NO TABULEIRO ONDE AS PE�AS EST�O
		piece.position = position; // INFORMANDO AS POSI��ES DAS PE�AS 
	}
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns; // CONDI��O PARA VER SE A POSI��O EXISTE 
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn()); // RETORNA AS POSI��ES EXISTENTES 
	}
	
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board"); // TESTANDO SE A PO��O EXISTE
		}
		return piece(position) != null; // REFERECIA AO METODO ACIMA QUE RETORNA A POSI��O DA PE�A 
	}
}
