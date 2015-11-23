package rl.linetracer;

import rl.Step;

public interface ControlManager
{
	// 指定したControlに応じた行動を行う
	void doControl(Step step, MachineControl MC);


	void setControlCount(int i, int controlcount);

	void setControl(int i, int u, int lmotorspeed, int rmotorspeed);

	Control _GetControl(int i, int u);

	int _getInterval();



}
