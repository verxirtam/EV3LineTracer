package rl.communication.message;

import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

// メッセージの処理を行う
public interface MessageProcedure
{
	void process(MessageInputContext input, MessageOutputContext output) throws Exception;
}