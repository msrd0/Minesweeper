from enum import Enum
from wrapper import AIWrapper

FIELD_SIZE = 3
MINES = 3

class CellType(Enum):
	BOMB, EMPTY, COVERED = 0, 1, 2

class State:
	def __init__(self):
		self._flag = (-1, -1)
		self._step = (-1, -1)
		self.grid = None
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
	def update(self, state: State):
		if state.isBuilding:
			field = self.ai.generateField()
			response = {
				"build": {
					"output": self.output.read(),
					"field": [[{"type": "BOMB"} if f else {"type": "EMPTY"} for f in row] for row in field]
				}
			}
		else:
			self.ai.step(state)
			response = {
				"output": self.output.read(),
				"xStep": state._step[0], "yStep": state._step[1],
				"xFlag": state._flag, "yFlag": state._flag[1]
			}
		return response

	def getState(self, d):
		s = State()
		if "building" in d:
			s.isBuilding = d["building"]
		return s


