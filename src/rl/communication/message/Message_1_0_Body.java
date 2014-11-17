package rl.communication.message;

import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.communication.EV3LineTracer_1_0_Command;

// メッセージ本体
// <MessageBody>:
// <Version> ;バージョン
// <Command>|<Result> ;コマンド|コマンドの結果
// /////////////////////////////////////////////
// <Result>は現状実装予定なし(Output専用)
// EV3はコマンドを受け付けて結果を出力するのみの予定
class Message_1_0_Body implements MessageProcedure
{

	@Override
	public void process(MessageInputContext input, MessageOutputContext output) throws Exception
	{
		// バージョン
		//context.skipToken("EV3LineTracer_1.0");
		MessageProcedure com=getMessageCommand(input,input.nextToken());
		// 改行
		input.skipReturn();
		// コマンドの処理
		com.process(input,output);
	}

	private MessageProcedure getMessageCommand(MessageInputContext context, String version_string) throws Exception
	{
		if (version_string.equals("EV3LineTracer_1.0"))
		{
			return new EV3LineTracer_1_0_Command();
		}
		
		throw new Exception("CommandVersion String is unmatch.");
		
	}

}