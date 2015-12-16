package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;

public class EV3LineTracer_1_1_Command extends EV3LineTracerCommand
{
	public static final String VERSION_STRING = "EV3LineTracer_1.1";

	@Override
	protected MessageProcedure createAdditionalCommand()
	{
		return null;
	}

	@Override
	public String getVersionString()
	{
		return VERSION_STRING;
	}
}