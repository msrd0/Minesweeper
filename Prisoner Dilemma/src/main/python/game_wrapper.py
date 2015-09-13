####################################################################################
# game_wrapper.py
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
from wrapper import AIWrapper

class Actions:
	COOPERATE = True
	BETRAY = False

class GameWrapper(AIWrapper):
	def update(self, d):
		if "enemyResponse" in d and "ownResponse" in d:
			if hasattr(self.ai, "process"):
				self.ai.process(d["ownResponse"], d["enemyResponse"])
			else:
				print("KI verarbeitet Daten aufgrund fehlender 'process' Methode nicht.")

		cooperate = not not self.ai.step()
		response = {
			"output": self.output.read(),
			"response": cooperate
		}
		return response

	def del_output(self, d):
		d.pop("output")

	def add_output(self, d, o):
		d["output"] += o

