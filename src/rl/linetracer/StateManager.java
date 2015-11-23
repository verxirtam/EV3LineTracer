package rl.linetracer;

import rl.Step;

public interface StateManager
{

	void getCurrentState(Step step, MachineControl MC);
	
	//Stateを設定する
	void _SetState(int i,double refmax,int controlcount);

	int getControlCount(int i);

	State _GetState(int i);

	int getStateCount();

}
