package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.EV3LineTracer;

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
	private int ControlCount;

	public void setStateIndex(int stateindex)
	{
		StateIndex = stateindex;
	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		// EV3LineTracer(Singleton)を取得
		EV3LineTracer ev3 = EV3LineTracer.getInstance();

		// StateIndexの検証
		if (StateIndex != Integer.parseInt(input.nextToken()))
		{
			throw new Exception(this.getClass().getName()+": StateIndex is Invalid.");
		}
		// RefMaxの取得
		double refmax = Double.parseDouble(input.nextToken());
		// ControlCountの取得
		ControlCount = Integer.parseInt(input.nextToken());
		// 改行
		input.skipReturn();

		// Stateの設定
		ev3.SetState(StateIndex, refmax, ControlCount);

	}

	public int getControlCount()
	{
		return ControlCount;
	}

}