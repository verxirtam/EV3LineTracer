package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.EV3LineTracer;

public class ReadCostMax implements MessageProcedure
{

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		//EV3LineTracer(Singleton)を取得
		EV3LineTracer ev3=EV3LineTracer.getInstance();
		
		// Intervalを取得
		double cost_max = Double.parseDouble(input.nextToken());
		
		//EV3にIntervalを設定
		ev3.setCostMax(cost_max);

	}

}
