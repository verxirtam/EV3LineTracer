package rl.linetracer;

import rl.Step;
import rl.communication.message.MessageProcedure;

abstract public class MDPManager
{
	protected StateManager stateManager;
	protected ControlManager controlManager;
	protected CostManager costManager;
	
	abstract MessageProcedure getReadMDPManager();
	
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
	
	///////////////////////
	//移行期間限定のメソッド
	//メソッド名先頭に"_"をつけて区別する
	///////////////////////
	//StateCountを指定して、
	//Conrolの配列の領域を確保する
	final public void _SetStateCount(int statecount)
	{
		stateManager._SetStateCount(statecount);
		controlManager._SetStateCount(statecount);
	}
	//Stateを設定する
	final public void _SetState(int i,double refmax,int controlcount)
	{
		stateManager._SetState(i,refmax,controlcount);
		controlManager._SetControlCount(i,refmax,controlcount);
	}
	//Contolを設定する
	final public void _SetControl(int i,int u,int lmotorspeed,int rmotorspeed)
	{
		controlManager._SetControl(i,u,lmotorspeed,rmotorspeed);
	}
	final public Control _GetControl(int i, int u)
	{
		return controlManager._GetControl(i, u);
	}
	final public State _GetState(int i)
	{
		return stateManager._GetState(i);
	}
	final public void _setCostMax(double cost_max)
	{
		costManager.setCostMax(cost_max);
	}
	final public double _getCostMax()
	{
		return costManager._getCostMax();
	}
	final public int _getInterval()
	{
		return controlManager._getInterval();
	}
	final public void _setInterval(int t)
	{
		controlManager._setInterval(t);
	}
}
