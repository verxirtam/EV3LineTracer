package rl.communication.message;

import rl.communication.message.context.MessageContext;

// メッセージの処理を行う
public interface MessageProcedure
{
	// TODO 引数に書き込み先OutputContextを作成する
	void process(MessageContext context) throws Exception;
}