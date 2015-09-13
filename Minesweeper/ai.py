####################################################################################
# ai.py
#
# Copyright (C) 2015 Pixelgaffer
#
# This work is free software; you can redistribute it and/or modify it
# under the terms of the GNU Lesser General Public License as published by the
# Free Software Foundation; either version 2 of the License, or any later
# version.
#
# This work is distributed in the hope that it will be useful, but without
# any warranty; without even the implied warranty of merchantability or
# fitness for a particular purpose. See version 2 and version 3 of the
# GNU Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
####################################################################################
import game_wrapper as minesweeper
import random
from pprint import pprint

class AI:
	def __init__(self):
		print("KI Instanz erschaffen")
		self.firststep = True

	def generateField(self):
		print("Generiere nen Feld")
		choices = [(x, y) for x in range(minesweeper.FIELD_SIZE) for y in range(minesweeper.FIELD_SIZE)]
		grid = [[False for _ in range(minesweeper.FIELD_SIZE)] for _ in range(minesweeper.FIELD_SIZE)]
		for x, y in random.sample(choices, minesweeper.START_BOMBS):
			grid[x][y] = True
		print("Feld generiert:")
		pprint(grid)
		return grid

	def step(self, state: minesweeper.State):
		if self.firststep:
			self.firststep = False
			state.uncover(random.randrange(minesweeper.FIELD_SIZE), random.randrange(minesweeper.FIELD_SIZE))
			return

		for x, row in enumerate(state.grid):
			for y, field in enumerate(row):
				if field.type == minesweeper.CellType.COVERED:
					state.uncover(x, y)
					return
