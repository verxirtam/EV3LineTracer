package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

//NullCommand
//何もしないコマンド
//何も実行されずRESULT_OKを出力する
public class CommandNullCommand implements MessageProcedure
{

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		//inputに対して何も読み取らない
		
		//outputに対してはRESULT_OKを出力する
		output.writeToken(EV3LineTracer_1_0_Command.RESULT_OK);
		output.newLine();
	}

}
