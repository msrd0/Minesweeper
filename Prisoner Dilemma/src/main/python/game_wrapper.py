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

