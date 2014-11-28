package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

// コマンド
//入力用
//===================================
//<Command>::=
//(
//	(
//		"SetMDP"<endl>;MDPを設定
//		<SetMDPBody>;コマンドの内容
//	)
//	|
//	(
//		"SetPolicy"<endl>;MDPを設定
//		<SetPolicyBody>;コマンドの内容
//	)
//	|
//	(
//		"ExecEpisode"<endl>;Episodeを実行
//		<Null>;コマンドの内容(無し)
//	)
//	|
//	(
//		"NullCommand"<endl>;何もしないコマンド
//		<Null>;コマンドの内容(無し)
//	)
//)
//出力用
//===================================
//<Command>::=
//(
//	"SetMDP"<endl>;MDPを設定
//	<SetMDPResult>;コマンドの結果
//)
//|
//(
//	"SetPolicy"<endl>;MDPを設定
//	<SetPolicyResult>;コマンドの結果
//)
//|
//(
//	"ExecEpisode"<endl>;Episodeを実行
//	<ExecEpisodeResult>;コマンドの結果
//)
//|
//(
//	"NullCommand"<endl>;何もしない
//	<NullCommandResult>;コマンドの結果
//)
//)
//
public class EV3LineTracer_1_0_Command implements MessageProcedure
{
	public static String RESULT_OK="OK";
	public static String RESULT_NG="NG";
	
	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		// コマンド文字列の取得
		String commandstring = input.nextToken();
		// 改行
		input.skipReturn();

		// コマンドの取得と実行
		createCommand(commandstring).process(input, output);

	}

	// コマンド文字列に応じたコマンドの生成
	MessageProcedure createCommand(String commandstring) throws Exception
	{
		// SetMDP
		if (commandstring.equals("SetMDP"))
		{
			return new CommandSetMDPBody();
		}
		// ExecEpisode
		if (commandstring.equals("ExecEpisode"))
		{
			return new CommandExecEpisode();
		}
		// NullCommand
		if (commandstring.equals("NullCommand"))
		{
			return new CommandNullCommand();
		}
		// どのコマンドにも当てはまらない場合は例外を投げる
		throw new Exception(this.getClass().getName());
	}
}