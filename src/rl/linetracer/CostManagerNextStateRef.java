/**
 * 
 */
package rl.linetracer;

import rl.Step;

/**
 * @author daisuke
 * 次のstateのindexが2より大きいかどうかで1ステップあたりのcostを変更する
 * ゴール時にも走行時と同じコストがかかる
 */
public class CostManagerNextStateRef extends CostManager
{

	/**
	 * @param cost_max
	 */
	public CostManagerNextStateRef(double cost_max)
	{
		super(cost_max);
	}

	/* (non-Javadoc)
	 * @see rl.linetracer.CostGetter#getCostWhenGoal(rl.Step, double)
	 */
	@Override
	public double getCostWhenGoal(Step step, double elapsed_time)
	{
		return getCostWhenRunning(step,elapsed_time);
	}

	/* (non-Javadoc)
	 * @see rl.linetracer.CostGetter#getCostWhenRunning(rl.Step, double)
	 */
	@Override
	public double getCostWhenRunning(Step step, double elapsed_time)
	{
		return (step.State > 2) ? 1.0 : 0.125;
	}

}
