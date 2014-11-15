package rl.linetracer.communication;

import rl.communication.message.context.MessageContext;
import rl.linetracer.EV3LineTracer;

// Intervalを取得する
//<Interval>::=DIGIT
class ReadInterval extends MessageProcedure_EV3LineTracer_1_0
{
	public ReadInterval(EV3LineTracer ev3)
	{
		super(ev3);
	}

	private int Interval;

	public int getInterval()
	{
		return Interval;
	}

	@Override
	public void process(MessageContext context) throws Exception
	{
		// Intervalを取得
		Interval = Integer.parseInt(context.nextToken());

	}

}