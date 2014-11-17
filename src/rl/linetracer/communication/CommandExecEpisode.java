package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

// コマンドExecEpisode
// Body部は無し
class CommandExecEpisode implements MessageProcedure
{

	@Override
	public void process(MessageInputContext input, MessageOutputContext output) throws Exception
	{
		// TODO コマンド毎の処理を実装する
	}

}