package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch { // CLASSE ONDE CONTEM AS REGRAS DO JOGO

	private Board board; //IMPORT BOARD (TABULEIRO)
	
	public ChessMatch() { // CONSTRUTOR DA PARTIDA
		board = new Board(8,8);	// DIMENS�O DO TABULEIRO
		initialSetup();
	}

	public ChessPiece[][] getPieces(){ // RETORNA UMA MATRIZ DE PE�AS DE XADREZ CORRESPONDENTE � "CHESS MATCH"
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { // RECEBE AS COORDENADAS EM LETRAS (a1)
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	private void initialSetup() {
		placeNewPiece('a', 8, new Rook(board, Color.WHITE));
		placeNewPiece('e', 8, new King(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.BLACK));
	}
}
