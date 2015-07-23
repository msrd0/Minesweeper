package org.pixelgaffer.turnierserver.minesweeper;

import java.util.Arrays;

import org.pixelgaffer.turnierserver.minesweeper.Cell.CellFromAi;

public class MinesweeperBuilderResponse {
	
	public String output;
	public CellFromAi[][] field;
	
	@Override
	public String toString() {
		return "Output: " + output + ", field: " + Arrays.toString(field);
	}
	
}
