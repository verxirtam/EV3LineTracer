package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

// Stateを取得する
//<State>::=N(<SingleState><endl>)
//		;Stateの定義 (StateIndexは0からN-1まで順に並ぶ(N=StateCount))
//<SingleState>::=
//	<StateIndex><tab>;StateIndex
//	<RefMax><tab>;RefMax
//	<ControlCount>;ControlCount
class ReadState implements MessageProcedure
{


	private int StateIndex;
	private double RefMax;
	private int ControlCount;

	public void setStateIndex(int stateindex)
	{
		StateIndex = stateindex;
	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output) throws Exception
	{
		// StateIndexの検証
		if (StateIndex != Integer.parseInt(input.nextToken()))
		{
			throw new Exception(this.getClass().getName());
		}
		// RefMaxの取得
		RefMax = Double.parseDouble(input.nextToken());
		// ControlCountの取得
		ControlCount = Integer.parseInt(input.nextToken());
		// 改行
		input.skipReturn();
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