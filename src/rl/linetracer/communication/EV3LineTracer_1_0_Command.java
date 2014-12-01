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
	public static final String VERSION_STRING="EV3LineTracer_1.0";

	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		// コマンド文字列の取得
		String commandstring = input.nextToken();
		// 改行
		input.skipReturn();

		// コマンドの取得
		MessageProcedure com = createCommand(commandstring);
		//コマンドの実行
		com.process(input, output);

	}

	// コマンド文字列に応じたコマンドの生成
	MessageProcedure createCommand(String commandstring)
			throws Exception
	{
		// SetMDP
		if (commandstring.equals(CommandSetMDP.COMMAND_STRING))
		{
			return new CommandSetMDP();
		}
		// ExecEpisode
		if (commandstring.equals(CommandExecEpisode.COMMAND_STRING))
		{
			return createCommandExecEpisode();
		}
		// NullCommand
		if (commandstring.equals(CommandNullCommand.COMMAND_STRING))
		{
			return new CommandNullCommand();
		}
		// どのコマンドにも当てはまらない場合は例外を投げる
		throw new Exception(this.getClass().getName());
	}

	//CommandExecEpisodeのインスタンスを返す
	protected CommandExecEpisode createCommandExecEpisode()
	{
		return new CommandExecEpisode();
	}
}