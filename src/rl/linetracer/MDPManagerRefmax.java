package rl.linetracer;

import rl.communication.message.MessageProcedure;
import rl.linetracer.communication.ReadMDPManagerRefmax;

public class MDPManagerRefmax extends MDPManager
{
	
	public MDPManagerRefmax()
	{
		super.stateManager = new StateManagerRefMax();
		super.controlManager = new ControlManagerNormal();
		super.costManager = new CostManagerNextStateRef(100.0);
	}

	@Override
	MessageProcedure getReadMDPManager()
	{
		return new ReadMDPManagerRefmax(
				(StateManagerRefMax)super.stateManager,
				(ControlManagerNormal)super.controlManager,
				(CostManagerNextStateRef)super.costManager
				);
	}
	
}
