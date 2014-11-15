package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.linetracer.EV3LineTracer;

abstract class MessageProcedure_EV3LineTracer_1_0 implements MessageProcedure
{
	private EV3LineTracer EV3;

	public EV3LineTracer getEV3LineTracer()
	{
		return EV3;
	}

	public MessageProcedure_EV3LineTracer_1_0(EV3LineTracer ev3)
	{
		super();
		this.EV3 = ev3;
	}

}