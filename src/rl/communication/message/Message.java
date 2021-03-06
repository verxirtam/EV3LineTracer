package rl.communication.message;

import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

// メッセージ全体
//<Message>::=<MessageVersion><endl> ;メッセージのバージョン
//<MessageBody>;メッセージ本体
//<endl>;空行
//
//出力用
//===================================
//
public class Message implements MessageProcedure
{
	@Override
	public void process(MessageInputContext input, MessageOutputContext output) throws Exception
	{
		//メッセージの1行目が空の場合は何もしない
		//コネクションを張ったがクライアントが
		//メッセージを送らずに終了した場合の対策
		if(!input.hasNextToken())
		{
			return;
		}
		// メッセージの識別子
		MessageProcedure mb = getMessgeBody(input.nextToken());
		// 改行
		input.skipReturn();
		// メッセージ本体の処理
		mb.process(input,output);
		// 空行のチェック
		input.skipToken("");
		
		//空行の入力
		output.newLine();
		output.flush();
	}

	private MessageProcedure getMessgeBody(String version_string) throws Exception
	{
		if (version_string.equals(Message_1_0_Body.VERSION_STRING))
		{
			return new Message_1_0_Body();
		}
		
		throw new Exception("MessageVersion String is unmatch.");
		
	}
}
