package rl.linetracer.communication;

import rl.communication.message.context.MessageContext;
import rl.linetracer.EV3LineTracer;

// コマンドExecEpisode
// Body部は無し
class CommandExecEpisode extends MessageProcedure_EV3LineTracer_1_0
{

	public CommandExecEpisode(EV3LineTracer ev3)
	{
		super(ev3);
	}

	@Override
	public void process(MessageContext context) throws Exception
	{
		// TODO コマンド毎の処理を実装する
	}

}