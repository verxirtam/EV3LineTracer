package rl.linetracer;

public class MDPManagerRefmax extends MDPManager
{
	public MDPManagerRefmax()
	{
		super.stateManager = new StateManagerRefMax();
		super.controlManager = new ControlManagerNormal();
		super.costManager = new CostManagerNextStateRef(100.0);
	}
}
