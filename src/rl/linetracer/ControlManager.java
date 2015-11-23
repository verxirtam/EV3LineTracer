package rl.linetracer;

import rl.Step;

public interface ControlManager
{
	// 指定したControlに応じた行動を行う
	void doControl(Step step, MachineControl MC);

	void _SetStateCount(int statecount);

	void _SetControlCount(int i, double refmax, int controlcount);

	void _SetControl(int i, int u, int lmotorspeed, int rmotorspeed);

	Control _GetControl(int i, int u);

	int _getInterval();

	void _setInterval(int t);


}
