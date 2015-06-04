package org.pixelgaffer.turnierserver.minesweeper;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;

import org.pixelgaffer.turnierserver.Parsers;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class Cell {
	
	/**
	 * Die Größe des Feldes
	 */
	public static final int FIELD_SIZE = 16;
	/**
	 * Die Anzahl an Bomben pro Feld
	 */
	public static final int BOMB_COUNT = 64;
	
	public enum Type {
		BOMB, EMPTY, COVERED;
	}
	
	/**
	 * True, wenn sich eine Flagge auf der Zelle befindet
	 */
	@Getter @Setter
	private boolean flagged;
	/**
	 * True, wenn die Zelle uncovered wurde (wird von der GameLogic verwendet)
	 */
	@Getter
	private boolean uncovered;
	/**
	 * Die Anzahl an Bomben, welche sich um dieses Feld herum befinden. -1, wenn das Feld nicht aufgedeckt wurde.
	 */
	@Getter @Setter
	private int bombsArround = -1;
	/**
	 * Der Typ dieser Zelle
	 */
	@Getter
	private Type type;
	
	/**
	 * Erstellt eine neue Zelle. TYP DARF BEIM GENERIEREN NICHT COVERED SEIN!
	 * 
	 * @param type
	 */
	public Cell(Type type) {
		this.type = type;
	}
	
	/**
	 * Diese Methode wird nichts ändern. Verwende MinesweeperAi.uncover(x, y)
	 */
	public void uncover() {
		uncovered = true;
	}
	
	/**
	 * True, wenn die Koordinate sich im Feld befindet
	 * 
	 * @param x Die x-Koordinate
	 * @param y Die y-Koordinate
	 * @return Ob sich die Koordinate im Feld befindet
	 */
	public static boolean isInField(int x, int y) {
		return x >= 0 && x < FIELD_SIZE && y >= 0 && y < FIELD_SIZE;
	}
	
	public static CellForAi[][] getArrayForAi(Cell[][] cells) {
		CellForAi[][] forAi = new CellForAi[Cell.FIELD_SIZE][Cell.FIELD_SIZE];
		for(int i = 0; i < Cell.FIELD_SIZE; i++) {
			for(int j = 0; j < Cell.FIELD_SIZE; j++) {
				forAi[i][j] = new CellForAi(cells[i][j]);
			}
		}
		return forAi;
	}
	
	public static Cell[][] getArrayFromAi(CellFromAi[][] cells) {
		Cell[][] fromAi = new Cell[Cell.FIELD_SIZE][Cell.FIELD_SIZE];
		for(int i = 0; i < Cell.FIELD_SIZE; i++) {
			for(int j = 0; j < Cell.FIELD_SIZE; j++) {
				fromAi[i][j] = cells[i][j].toCell();
			}
		}
		return fromAi;
	}
	
	public static class CellForAi {
		public CellForAi() {}
		public CellForAi(Cell cell) {
			if(!cell.uncovered) {
				type = Type.COVERED;
				flagged = cell.flagged;
			}
			else if(cell.type == Type.BOMB) {
				type = Type.BOMB;
			}
			else if(cell.type == Type.EMPTY) {
				type = Type.EMPTY;
				bombsAround = cell.bombsArround;
			}
		}
		public boolean flagged;
		public Type type;
		public int bombsAround = -1;
		Cell toCell() {
			return new Cell(type);
		}
	}
	
	public static class CellFromAi {
		public CellFromAi() {}
		public CellFromAi(Type type) {
			this.type = type;
		}
		public Type type;
		Cell toCell() {
			Cell cell = new Cell(type);
			cell.uncovered = false;
			return new Cell(type);
		}
	}
	
}
