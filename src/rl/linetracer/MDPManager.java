package rl.linetracer;

import rl.Step;
import rl.communication.message.MessageProcedure;

abstract public class MDPManager
{
	protected StateManager stateManager;
	protected ControlManager controlManager;
	protected CostManager costManager;
	
	abstract public MessageProcedure getReadMDPManager();
	
	// 現在のStateとCostを取得する
	// argument:
	// Step step: 取得したStateを格納するStep
	// Step previousstep: 取得したCostを格納するStep
	final public void getCurrentState(Step step, MachineControl MC)
	{
		stateManager.getCurrentState(step, MC);
	}

	// 指定したControlに応じた行動を行う
	final public void doControl(Step step, MachineControl MC)
	{
		controlManager.doControl(step, MC);
	}
	//StateCountの取得
	final public int getStateCount()
	{
		return stateManager.getStateCount();
	}
	//ControlCountの取得
	final public int getControlCount(int i)
	{
		return stateManager.getControlCount(i);
	}
	
	/////////////////////////////////////////
	
	//ゴール時のcostを算出
	final public double getCostWhenGoal(Step step, double elapsed_time)
	{
		return costManager.getCostWhenGoal(step, elapsed_time);
	}

	//コースアウト時のcostを算出
	final public double getCostWhenCourseOut(Step step, double elapsed_time)
	{
		return costManager.getCostWhenCourseOut(step, elapsed_time);
	}
	
	//タイムアウト時のcostを算出
	final public double getCostWhenTimeOut(Step step, double elapsed_time)
	{
		return costManager.getCostWhenTimeOut(step, elapsed_time);
	}

	//実行時のcostを算出
	final public double getCostWhenRunning(Step step, double elapsed_time)
	{
		return costManager.getCostWhenRunning(step, elapsed_time);
	}
	
	///////////////////////////////////////////////
	final public double _getCostMax()
	{
		return costManager._getCostMax();
	}

}
