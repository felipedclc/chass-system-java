package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch { // CLASSE ONDE CONTEM AS REGRAS DO JOGO

	private int turn;
	private Color currentPlayer;
	private Board board; // IMPORT BOARD (TABULEIRO)
	private boolean check; // boolean JA COMEÇA VALENDO FALSE

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() { // CONSTRUTOR DA PARTIDA
		board = new Board(8, 8); // DIMENSÃO DO TABULEIRO
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public int getTurn() {
		return turn;
	}
	
	public boolean getCheck() {
		return check;
	}

	public ChessPiece[][] getPieces() { // RETORNA UMA MATRIZ DE PEÇAS DE XADREZ CORRESPONDENTE À "CHESS MATCH"
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}

	public boolean[][] possibleMoves(ChessPosition sourcepoPosition) {
		Position position = sourcepoPosition.toPosition(); // CONVERTENDO A POSIÇÃO DE LETRAS(a1) PARA MATRIZ
		validateSourcePosition(position); // VALIDANDO A POSICAO DE ORIGEM
		return board.piece(position).possibleMoves(); // POSIÇÕES POSSIVEIS
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) { // POSIÇÃO DE ORIGEM E DESTINO
		Position source = sourcePosition.toPosition(); // CONVERTENDO O SOURCE PARA POSIÇÃO DA MATRIZ
		Position target = targetPosition.toPosition();
		validateSourcePosition(source); // VALIDANDO A POSIÇÃO DE ORIGEM
		validaTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target); // REALIZA O MOVIMENTO DA PEÇA PELA MATRIZ
		
		if(testCheck(currentPlayer)) { // SE A PEÇA(REI) ESTIVER SE COLOCANDO EM CHEQUE
			undoMove(source, target, capturedPiece); // DESFAZENDO O MOVIMENTO
			throw new ChessException("You can't put yourself in check");
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false; // TESTANDO SE O OPONENTE ESTA EM CHEQUE
		
		nextTurn();
		return (ChessPiece) capturedPiece;
	}

	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source); // RETIRANDO A PEÇA QUE ESTAVA NA POSIÇÃO DE ORIGEM
		Piece capturedPiece = board.removePiece(target); // PEÇA CAPTURADA NA VARIAVEL (CAPTURED PIECE)
		board.placePiece(p, target); // SUBSTITUIÇÃO DAS PEÇAS

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target); // REMOVENDO A PEÇA DO DESTINO FINAL
		board.placePiece(p, source); // RETORNA A PEÇA PARA A POSIÇAO DE ORIGEM

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target); // VOLTANDO A PEÇA CAPTURADA PARA O JOGO
			capturedPieces.remove(capturedPiece); // REMOVENDO A PEÇA DA LISTA DE CAPTURADAS
			piecesOnTheBoard.add(capturedPiece); // VOLTANDO A PEÇA CAPTURADA PARA A LISTA DO TABULEIRO
		}
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) { // SE NÃO HOUVER UMA PEÇA NA POSIÇÃO DE ORIGEM
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) { // TESTANDO A COR DA PEÇA E DO
																				// ADVERSARIO OU A SUA
			throw new ChessException("The chosen piece is not yours");
		}

		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}

	private void validaTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) { // SE O MOVIMENTO DE ORIGEM PARA O DESTINO NÃO É POSSÍVEL
			throw new ChessException("The chosen piece can't move to target position");
		}
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; // TROCA A COR DO JOGADOR A CADA
																					// JOGADA
	}

	private Color opponent(Color color) {
		return (color == color.WHITE) ? Color.BLACK : Color.WHITE; // SE A COR FOR BRANCA VAI RETORNAR O
																	// PRETO(OPONENTE), CASO CONTRARIO RETORNA UM BRANCO
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList()); // FILTRANDO A LISTA PELA COR
		for (Piece p : list) {
			if (p instanceof King) { // SE P FOR O REI
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no " + color + "King on the board");
	}

	private boolean testCheck(Color color) { // TESTANDO SE O REI ESTA EM CHEQUE
		Position kingPosition = king(color).getChessPosition().toPosition(); // PEGANDO A POSIÇÃO EM MATRIZ DO REI
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList()); // PEGANDO A LSITA DE OPONENTES
		for(Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves(); // MATRIZ DE MOVIMENTOS POSSIVEIS DA PEÇA ADVERSARIA P
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) { // SE TIVER MOVIMENTOS POSSIVEIS EM DIREÇÃO AO P(REI)
				return true;
			}
		}
		return false; // SE PASSAR DO IF ELE RETORNA FALSE
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) { // RECEBE AS COORDENADAS EM LETRAS (a1)
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); // COLOCANDO AS PEÇAS NO TABULEIRO
		piecesOnTheBoard.add(piece); // ADICIONANDO AS PEÇAS NA LISTA
	}

	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		placeNewPiece('d', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.WHITE));

		placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
