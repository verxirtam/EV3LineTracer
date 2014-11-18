package rl.communication.message;

import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.communication.EV3LineTracer_1_0_Command;

// メッセージ本体
// <MessageBody>:
// <Version> ;バージョン
// <Command>|<Result> ;コマンド|コマンドの結果
// /////////////////////////////////////////////
//出力用
//===================================
//メッセージ全体
//<Message>::="MESSAGE_1.0"<endl> ;メッセージのバージョン
//<MessageBody>;メッセージ本体
//<endl>;空行
class Message_1_0_Body implements MessageProcedure
{

	@Override
	public void process(MessageInputContext input, MessageOutputContext output) throws Exception
	{
		// バージョン
		MessageProcedure com=getMessageCommand(input,input.nextToken());
		// 改行
		input.skipReturn();
		
		//返信用メッセージ作成
		output.writeToken("MESSAGE_1.0");
		output.newLine();
		
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