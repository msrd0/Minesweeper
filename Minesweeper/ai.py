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
