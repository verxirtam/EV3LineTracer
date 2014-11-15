package rl.communication.message;

import rl.communication.message.context.MessageContext;
import rl.linetracer.EV3LineTracer;
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
	public void process(MessageContext context) throws Exception
	{
		// バージョン
		//context.skipToken("EV3LineTracer_1.0");
		MessageProcedure com=getMessageCommand(context,context.nextToken());
		// 改行
		context.skipReturn();
		// コマンドの処理
		com.process(context);
	}

	private MessageProcedure getMessageCommand(MessageContext context, String version_string) throws Exception
	{
		if (version_string.equals("EV3LineTracer_1.0"))
		{
			return new EV3LineTracer_1_0_Command((EV3LineTracer)context.getTarget());
		}
		
		throw new Exception("CommandVersion String is unmatch.");
		
	}

}