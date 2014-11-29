package rl.linetracer.communication;

import rl.Episode;
import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.EV3LineTracer;

// コマンドExecEpisode
//	<Null>;コマンドの内容(無し)
//
//<EpisodeBody>::=
//	<StepCount><endl>
//	N(<SingleStep><endl>)	;NはStepCountの値
//
//
//<SingleStep>::=
//	<EpisodeIndex><tab>
//	<State><tab>
//	<Control><tab>
//	<Cost>
//
//<Reason>::=(発生した例外のStackTrace)
//
//出力
//<ExecEpisodeResult>;コマンドの結果
//<ExecEpisodeResult>::=
//(
//"OK"<endl>
//<EpisodeBody>
//|
//"NG"<endl>
//<Reason>
//)
class CommandExecEpisode implements MessageProcedure
{
	public static final String COMMAND_STRING = "ExecEpisode";
	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		EV3LineTracer ev3 = EV3LineTracer.getInstance();

		Episode e = new Episode();
		ev3.ExecEpisode(e);

		outputEpisode(e, output);
	}

	private void outputEpisode(Episode e, MessageOutputContext output)
	{
		// TODO outputへの出力処理を記入する
	}

}