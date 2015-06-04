package org.pixelgaffer.turnierserver.minesweeper;

import java.util.Arrays;

public class MinesweeperBuilderResponse {
	
	public String output;
	public Cell[][] field;
	
	@Override
	public String toString() {
		return "Output: " + output + ", field: " + Arrays.toString(field);
	}
	
}
