package rl.communication.message;

import rl.communication.message.context.MessageContext;

// メッセージの処理を行う
public interface MessageProcedure
{
	void process(MessageContext context) throws Exception;
}