/*
 * MinesweeperLogic.java
 *
 * Copyright (C) 2015 Pixelgaffer
 *
 * This work is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or any later
 * version.
 *
 * This work is distributed in the hope that it will be useful, but without
 * any warranty; without even the implied warranty of merchantability or
 * fitness for a particular purpose. See version 2 and version 3 of the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pixelgaffer.turnierserver.minesweeper.logic;

import org.pixelgaffer.turnierserver.gamelogic.AllBuilderAllSolverLogic;
import org.pixelgaffer.turnierserver.gamelogic.GameLogic;
import org.pixelgaffer.turnierserver.gamelogic.interfaces.Ai;
import org.pixelgaffer.turnierserver.gamelogic.messages.BuilderSolverResponse;
import org.pixelgaffer.turnierserver.minesweeper.Cell;
import org.pixelgaffer.turnierserver.minesweeper.Grid;
import org.pixelgaffer.turnierserver.minesweeper.MinesweeperBuilderResponse;
import org.pixelgaffer.turnierserver.minesweeper.MinesweeperSolverResponse;

import com.google.gson.reflect.TypeToken;

public class MinesweeperLogic extends AllBuilderAllSolverLogic<MinesweeperObject, Grid, MinesweeperBuilderResponse, MinesweeperSolverResponse> {
	
	public MinesweeperLogic() {
		super(new TypeToken<BuilderSolverResponse<MinesweeperBuilderResponse, MinesweeperSolverResponse>>() {
		});
	}
	
	@Override
	public void failed(boolean building, Ai ai) {
		if (building) {
			getUserObject(ai).loose("Die KI hat beim Bauen des Spielfeldes einen Fehler gemacht");
			return;
		}
		getUserObject(ai).score -= getUserObject(ai).building.getMoves();
	}
	
	@Override
	public void succeeded(boolean building, Ai ai) {
		if (!building) {
			getUserObject(ai).score += Cell.FIELD_SIZE * Cell.FIELD_SIZE;
			getUserObject(ai).score -= getUserObject(ai).building.getMoves();
		}
	}
	
	@Override
	protected void gameFinished() {
		GameLogic.logger.info("Das Spiel wurde beendet.");
	}
	
	@Override
	protected void setup() {
		for (Ai ai : game.getAis()) {
			getUserObject(ai).millisLeft = 10000;
		}
		maxTurns = 10;
	}
	
	@Override
	protected MinesweeperObject createUserObject(Ai ai) {
		return new MinesweeperObject();
	}
	
	@Override
	public Grid createGameState(Ai ai) {
		Grid grid = new Grid();
		grid.setAi(ai);
		return grid;
	}
	
}
