package rl.linetracer;

public class MDPManagerRefmax extends MDPManager
{
	public MDPManagerRefmax()
	{
		super.stateManager = new StateManagerRefMax();
		super.controlManager = new ControlManagerNormal();
	}
}
