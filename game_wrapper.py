from wrapper import AIWrapper
from collections import namedtuple

FIELD_SIZE = 3
START_BOMBS = 4

class CellType:
	BOMB = "BOMB"
	EMPTY = "EMPTY"
	COVERED = "COVERED"

Cell = namedtuple("Cell", ["type", "flagged", "bombsAround"])

class State:
	def __init__(self, grid=None):
		self._flag = (-1, -1)
		self._step = (-1, -1)
		if grid:
			self.grid = grid
		else:
			self.grid = [[Cell(type=CellType.COVERED, flagged=False, bombsAround=-1) for _ in range(FIELD_SIZE)] for _ in range(FIELD_SIZE)]
		self.isBuilding = False

	def check_is_field(self, x, y):
		if x < 0 or x > FIELD_SIZE or y < 0 or y > FIELD_SIZE:
			raise RuntimeError("({}, {}) ist nicht im {}er Feld enthalten".format(x, y, FIELD_SIZE))
		if x != int(x) or y != int(y):
			raise RuntimeError("Nur Ints erlaubt! x: {} ({}), y: {} ({})".format(x, type(x), y, type(y)))

	def flag(self, x: int, y: int):
		self.check_is_field(x, y)
		self._flag = (x, y)

	def unflag(self, x=None, y=None):
		if not x:
			x = self._flag[0]
		if not y:
			y = self._flag[1]

		if (x, y) == self._flag:
			self._flag = (-1, -1)

	def uncover(self, x: int, y: int):
		self.check_is_field(x, y)
		self._step = (x, y)

	def cover(self, x=None, y=None):
		if not x:
			x = self._step[0]
		if not y:
			y = self._step[1]

		if (x, y) == self._step:
			self._step = (-1, -1)


class GameWrapper(AIWrapper):
	state = None

	def update(self, state: State):
		if state.isBuilding:
			field = self.ai.generateField()
			response = {
				"build": {
					"output": self.output.read(),
					"field": [[{"type": CellType.BOMB} if f else {"type": CellType.EMPTY} for f in row] for row in field]
				}
			}
		else:
			self.ai.step(state)
			response = {
				"solve": {
					"output": self.output.read(),
					"xStep": state._step[0], "yStep": state._step[1],
					"xFlag": state._flag[0], "yFlag": state._flag[1]
				}
			}
		return response

	def getState(self, d):
		state = State()
		if "building" in d:
			state.isBuilding = d["building"]

		if not state.isBuilding:
			if self.state:
				state.grid = self.state.grid
			for pos, change in d["change"].items():
				x, y = pos.split(":")
				x, y = int(x), int(y)
				state.grid[x][y] = Cell(type=change["type"], flagged=change["flagged"], bombsAround=change["bombsAround"])

		self.state = state

		return state


