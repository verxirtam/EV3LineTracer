package rl.linetracer.communication;

import rl.communication.message.context.MessageContext;
import rl.linetracer.EV3LineTracer;

// StateCountを取得する
//<StateCount>::=DIGIT
class ReadStateCount extends MessageProcedure_EV3LineTracer_1_0
{
	public ReadStateCount(EV3LineTracer ev3)
	{
		super(ev3);
	}

	private int StateCount;

	@Override
	public void process(MessageContext context) throws Exception
	{
		// StateCountを取得
		StateCount = Integer.parseInt(context.nextToken());
	}

	public int getStateCount()
	{
		return StateCount;
	}
}