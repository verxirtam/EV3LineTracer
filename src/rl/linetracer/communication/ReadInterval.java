package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageContext;

// Intervalを取得する
//<Interval>::=DIGIT
class ReadInterval implements MessageProcedure
{
	

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