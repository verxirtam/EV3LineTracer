package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.EV3LineTracer;

// StateCountを取得する
//<StateCount>::=DIGIT
class ReadStateCount implements MessageProcedure
{

	private int StateCount;

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		// EV3LineTracer(Singleton)を取得
		EV3LineTracer ev3 = EV3LineTracer.getInstance();

		// StateCountを取得
		StateCount = Integer.parseInt(input.nextToken());

		// StateCountの設定
		ev3.SetStateCount(StateCount);
	}

	public int getStateCount()
	{
		return StateCount;
	}
}