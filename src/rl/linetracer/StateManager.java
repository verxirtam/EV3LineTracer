package rl.linetracer;

import rl.Step;

public interface StateManager
{

	void getCurrentState(Step step, MachineControl MC);
	
	void _SetStateCount(int statecount);
	//Stateを設定する
	void _SetState(int i,double refmax,int controlcount);

	int getControlCount(int i);

	State _GetState(int i);

	int getStateCount();

}
