package rl.linetracer;

import rl.communication.message.MessageProcedure;
import rl.linetracer.communication.ReadMDPManagerRefmax;

public class MDPManagerRefmax extends MDPManager
{
	public static final String MANAGER_NAME = "MDPManagerRefmax";
	private StateManagerRefMax stateManagerRefMax;
	private ControlManagerNormal controlManagerNormal;
	private CostManagerNextStateRef costManagerNextStateRef;
	
	public MDPManagerRefmax()
	{
		stateManagerRefMax = new StateManagerRefMax();
		controlManagerNormal = new ControlManagerNormal();
		costManagerNextStateRef = new CostManagerNextStateRef(100.0);
		
		super.stateManager = stateManagerRefMax;
		super.controlManager = controlManagerNormal;
		super.costManager = costManagerNextStateRef;
	}

	@Override
	public MessageProcedure getReadMDPManager()
	{
		return new ReadMDPManagerRefmax(this);
	}
	
	///////////////////////
	//移行期間限定のメソッド
	//メソッド名先頭に"_"をつけて区別する
	///////////////////////
	final public Control getControl(int i, int u)
	{
		return controlManagerNormal._GetControl(i, u);
	}
	final public State getState(int i)
	{
		return stateManagerRefMax._getState(i);
	}
	final public int getInterval()
	{
		return controlManagerNormal._getInterval();
	}
	
	final public void setStateCount(int state_count)
	{
		stateManagerRefMax.setStateCount(state_count);
		controlManagerNormal.setStateCount(state_count);
	}

	final public void setInterval(int interval)
	{
		controlManagerNormal.setInterval(interval);
	}

	final public void setCostMax(double cost_max)
	{
		costManagerNextStateRef.setCostMax(cost_max);
	}

	final public void setState(int stateIndex, double refmax, int controlCount)
	{
		stateManagerRefMax.setState(stateIndex, refmax, controlCount);
		controlManagerNormal.setControlCount(stateIndex, controlCount);
	}

	final public void setControl(int stateIndex, int controlIndex, int l_motor_speed,
			int r_motor_speed)
	{
		controlManagerNormal.setControl(stateIndex, controlIndex, l_motor_speed, r_motor_speed);
		
	}

	@Override
	public String getManagerName()
	{
		return MANAGER_NAME;
	}
}
