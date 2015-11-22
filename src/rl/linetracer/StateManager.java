package rl.linetracer;

import rl.Step;

public interface StateManager
{

	void GetCurrentState(Step step, MachineControl MC);
	
	void _SetStateCount(int statecount);
	//Stateを設定する
	void _SetState(int i,double refmax,int controlcount);

	int _GetControlCount(int i);

	State _GetState(int i);

	int _GetStateCount();

}
