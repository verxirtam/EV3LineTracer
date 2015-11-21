package rl.linetracer;

import rl.Step;

abstract public class StateManager
{

	abstract public void GetCurrentState(Step step, MachineControl MC);
	
	abstract public void _SetStateCount(int statecount);
	//Stateを設定する
	abstract public void _SetState(int i,double refmax,int controlcount);

	abstract public int _GetControlCount(int i);

	abstract public State _GetState(int i);

}
