/**
 * 
 */
package rl.linetracer;

import rl.Step;

/**
 * @author daisuke
 * 最初に作ったコスト算出法に基づいたクラス
 * ゴール時の経過時間に基づいてコストを算出している
 * 走行中はコストは発生しない
 */
public class CostManagerElapsedTime extends CostManager
{

	/**
	 * @param cost_max
	 */
	public CostManagerElapsedTime(double cost_max)
	{
		super(cost_max);
	}

	/* (non-Javadoc)
	 * @see rl.linetracer.CostGetter#getCostWhenGoal(rl.Step, double)
	 */
	@Override
	public double getCostWhenGoal(Step step, double elapsed_time)
	{
		return elapsed_time;
	}

	/* (non-Javadoc)
	 * @see rl.linetracer.CostGetter#getCostWhenRunning(rl.Step, double)
	 */
	@Override
	public double getCostWhenRunning(Step step, double elapsed_time)
	{
		return 0.0;
	}

}
