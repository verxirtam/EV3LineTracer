package rl.linetracer;

import rl.Step;

public class MDPManagerRefmax extends MDPManager
{

	public MDPManagerRefmax()
	{
		super.controlManager= new ControlManagerNormal();
	}

	@Override
	public void GetStateAndCost(Step step, Step previousstep, MachineControl MC)
	{
		// TODO Auto-generated method stub

	}


}
