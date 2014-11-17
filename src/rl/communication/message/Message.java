package rl.communication.message;

import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

// メッセージ全体
//<Message>::=<MessageVersion><endl> ;メッセージのバージョン
//<MessageBody>;メッセージ本体
//<endl>;空行
class Message implements MessageProcedure
{
	@Override
	public void process(MessageInputContext input, MessageOutputContext output) throws Exception
	{
		// メッセージの識別子
		MessageProcedure mb = getMessgeBody(input.nextToken());
		// 改行
		input.skipReturn();
		// メッセージ本体の処理
		mb.process(input,output);
		// 空行のチェック
		input.skipToken("");
	}

	private MessageProcedure getMessgeBody(String version_string) throws Exception
	{
		if (version_string.equals("MESSAGE_1.0"))
		{
			return new Message_1_0_Body();
		}
		
		throw new Exception("MessageVersion String is unmatch.");
		
	}
}
