package rl.linetracer.communication;

import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;

//コマンド
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
//<Version>::="EV3LineTracer_1.0"
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

public abstract class EV3LineTracerCommand implements MessageProcedure
{

	public abstract String getVersionString();

	public EV3LineTracerCommand()
	{
		super();
	}

	@Override
	public void process(MessageInputContext input, MessageOutputContext output) throws Exception
	{
		// コマンド文字列の取得
		String commandstring = input.nextToken();
		// 改行
		input.skipReturn();
		
		//出力
		//EV3バージョン
		output.writeToken(getVersionString());
		//改行
		output.newLine();
		
		// コマンドの取得
		MessageProcedure com = createCommand(commandstring);
		//コマンドの実行
		com.process(input, output);
	
	}

	MessageProcedure createCommand(String commandstring) throws Exception
	{
		//派生クラスでの独自実装のコマンドとマッチすればそれを返す。無ければnullを返す。
		MessageProcedure mp = createAdditionalCommand(commandstring);
		if(mp != null)
		{
			return mp;
		}
		// SetCurrentPolicy
		if (commandstring.equals(CommandSetCurrentPolicy.COMMAND_STRING))
		{
			return new CommandSetCurrentPolicy();
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

	protected CommandExecEpisode createCommandExecEpisode()
	{
		return new CommandExecEpisode();
	}
	
	//派生クラスでの独自実装のコマンドとマッチすればそれを返す。無ければnullを返す。
	protected abstract MessageProcedure createAdditionalCommand(String command_string);
}