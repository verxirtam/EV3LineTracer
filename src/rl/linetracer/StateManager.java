package rl.linetracer;

import rl.Step;

public interface StateManager
{

	void getCurrentState(Step step, MachineControl MC);
	
	int getControlCount(int i);

	State _GetState(int i);

	int getStateCount();

}
