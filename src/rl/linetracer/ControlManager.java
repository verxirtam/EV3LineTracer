package rl.linetracer;

import rl.Step;

public abstract class ControlManager
{
	// 指定したControlに応じた行動を行う
	abstract public void DoControl(Step step, MachineControl MC, int interval);

	abstract public void _SetStateCount(int statecount);

	abstract public void _SetControlCount(int i, double refmax, int controlcount);

	abstract public void _SetControl(int i, int u, int lmotorspeed, int rmotorspeed);

	abstract public Control _GetControl(int i, int u);


}
