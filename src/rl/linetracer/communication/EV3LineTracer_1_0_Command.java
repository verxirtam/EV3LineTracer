package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;


public class EV3LineTracer_1_0_Command extends EV3LineTracerCommand
{
	public static final String VERSION_STRING = "EV3LineTracer_1.0";

	@Override
	protected MessageProcedure createAdditionalCommand(String command_string)
	{
		// SetMDP
		if (command_string.equals(CommandSetMDP.COMMAND_STRING))
		{
			return new CommandSetMDP();
		}
		return null;
	}

	@Override
	public String getVersionString()
	{
		return VERSION_STRING;
	}
}