package rl.linetracer.communication;

import java.util.ArrayList;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.EV3LineTracer;

//RegularPolicyの読み取り
//<RegularPolicy>::=
//	N(<SinglePolicy><endl>);RegularPolicyの定義(StateIndexは0からN-1まで順に並ぶ(N=StateCount))
//	
//<SinglePolicy>::=
//	<StateIndex><tab>;StateIndex
//	<ControlIndex>;ControlIndex
public class ReadRegularPolicy implements MessageProcedure
{

	private int StateCount;
	private ArrayList<Integer> ControlCount;

	/**
	 * @param stateCount
	 *            the stateCount to set
	 */
	public void setStateCount(int stateCount)
	{
		StateCount = stateCount;
	}

	/**
	 * @param controlCount
	 *            the controlCount to set
	 */
	public void setControlCount(ArrayList<Integer> controlCount)
	{
		ControlCount = controlCount;
	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		// EV3LineTracer(Singleton)を取得
		EV3LineTracer ev3 = EV3LineTracer.getInstance();

		// 推移確率格納用の配列の0.0で初期化
		double[][] prob = new double[StateCount][];
		for (int i = 0; i < StateCount; i++)
		{
			prob[i] = new double[ControlCount.get(i)];
			for (int u = 0; u < prob[i].length; u++)
			{
				prob[i][u] = 0.0;
			}
		}

		for (int i = 0; i < StateCount; i++)
		{
			if (i != Integer.parseInt(input.nextToken()))
			{
				throw new Exception(this.getClass().getName());
			}
			int u = Integer.parseInt(input.nextToken());
			if (u < 0 || ControlCount.get(i) <= u)
			{
				throw new Exception(this.getClass().getName());
			}
			// 取得したcontrolの確率を1.0にする
			prob[i][u] = 1.0;

			input.skipReturn();
		}
		// RegularPolicyの初期化
		ev3.SetRegularPolicy(prob);
	}

}