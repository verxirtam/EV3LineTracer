package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

//NullCommand
//何もしないコマンド
//何も実行されずRESULT_OKを出力する
//入力用
//===================================
//<Null>;コマンドの内容(無し)
//
//<Null>::="";空文字列
//
//出力用
//===================================
//"NullCommand"
//<NullCommandResult>;コマンドの結果
//
//<NullCommandResult>="OK"
//
public class CommandNullCommand implements MessageProcedure
{
	public static final String COMMAND_STRING = "NullCommand";
	public static final String RESULT_OK = "OK";

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		// inputに対して何も読み取らない

		// outputに対してはRESULT_OKを出力する
		output.writeToken(COMMAND_STRING);
		output.newLine();
		output.writeToken(RESULT_OK);
		output.newLine();
	}

}
