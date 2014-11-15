package rl.linetracer.communication;

import rl.communication.message.context.MessageContext;
import rl.linetracer.EV3LineTracer;

// Stateを取得する
//<State>::=N(<SingleState><endl>)
//		;Stateの定義 (StateIndexは0からN-1まで順に並ぶ(N=StateCount))
//<SingleState>::=
//	<StateIndex><tab>;StateIndex
//	<RefMax><tab>;RefMax
//	<ControlCount>;ControlCount
class ReadState extends MessageProcedure_EV3LineTracer_1_0
{

	public ReadState(EV3LineTracer ev3)
	{
		super(ev3);
	}

	private int StateIndex;
	private double RefMax;
	private int ControlCount;

	public void setStateIndex(int stateindex)
	{
		StateIndex = stateindex;
	}

	@Override
	public void process(MessageContext context) throws Exception
	{
		// StateIndexの検証
		if (StateIndex != Integer.parseInt(context.nextToken()))
		{
			throw new Exception(this.getClass().getName());
		}
		// RefMaxの取得
		RefMax = Double.parseDouble(context.nextToken());
		// ControlCountの取得
		ControlCount = Integer.parseInt(context.nextToken());
		// 改行
		context.skipReturn();
	}

	public int getControlCount()
	{
		return ControlCount;
	}

	public double getRefMax()
	{
		return RefMax;
	}

}