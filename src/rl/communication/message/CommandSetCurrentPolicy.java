package rl.communication.message;

import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

public class CommandSetCurrentPolicy implements MessageProcedure
{

	public static final String COMMAND_STRING = "SetCurrentPolicy";
	public static final String RESULT_OK = "OK";

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		// TODO Auto-generated method stub

	}

}
