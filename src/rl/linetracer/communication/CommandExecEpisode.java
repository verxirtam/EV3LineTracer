package rl.linetracer.communication;

import java.io.IOException;

import rl.Episode;
import rl.Step;
import rl.communication.message.MessageProcedure;
import rl.communication.message.context.MessageInputContext;
import rl.communication.message.context.MessageOutputContext;
import rl.linetracer.EV3LineTracer;

// コマンドExecEpisode
//	<Null>;コマンドの内容(無し)
//
//
//出力
//"ExecEpisode"<endl>;
//<ExecEpisodeResult>;コマンドの結果
//
//<ExecEpisodeResult>::=
//(
//"OK"<endl>
//<EpisodeBody>
//|
//"NG"<endl>
//<Reason>
//)

//
//<Reason>::=(発生した例外のStackTrace)
//
public class CommandExecEpisode implements MessageProcedure
{
	public static final String COMMAND_STRING = "ExecEpisode";
	public static final String RESULT_OK = "OK";
	public static final String RESULT_NG = "NG";
	@Override
	public void process(MessageInputContext input, MessageOutputContext output)
			throws Exception
	{
		Episode e = new Episode();
		
		doExecEpisode(e);
		
		outputEpisodeOK(e, output);
	}
	
	//EV3でエピソードを実行する
	protected void doExecEpisode(Episode e)
	{
		EV3LineTracer ev3 = EV3LineTracer.getInstance();
		ev3.ExecEpisode(e);
	}

	//<ExecEpisodeResult>;コマンドの結果
	//
	//<ExecEpisodeResult>::=
	//(
	//"OK"<endl>
	//<EpisodeBody>
	//|
	//"NG"<endl>
	//<Reason>
	//)
	private void outputEpisodeOK(Episode e, MessageOutputContext output) throws IOException
	{
		//コマンド名の出力
		output.writeToken(COMMAND_STRING);
		output.newLine();
		//ExecEpisodeResultの出力
		output.writeToken(RESULT_OK);
		output.newLine();
		//EpisodeBodyの出力
		outputEpisodeBody(e,output);
		
	}

	//<EpisodeBody>::=
	//<StepCount><endl>
	//N(<SingleStep><endl>)	;NはStepCountの値
	private void outputEpisodeBody(Episode e, MessageOutputContext output) throws IOException
	{
		//StepCountの出力
		int step_count = e.GetStepCount();
		output.writeToken(Integer.toString(step_count));
		output.newLine();
		//Stepの出力
		for(int i = 0;i<step_count;i++)
		{
			//単独のステップの出力
			outputSingleStep(i,e,output);
			//改行
			output.newLine();
		}
	}

	//<SingleStep>::=
	//<EpisodeIndex><tab>
	//<State><tab>
	//<Control><tab>
	//<Cost>
	//
	//<EpisodeIndex>::=DIGIT
	//<State>::=DIGIT
	//<Control>::=DIGIT
	//<Cost>::=DIGIT"."DIGIT
	private void outputSingleStep(int i, Episode e, MessageOutputContext output) throws IOException
	{
		//エピソードからステップを取得
		Step s = e.GetStep(i);
		//エピソードインデックスを出力
		output.writeToken(Integer.toString(i));
		//ステップのプロパティを出力
		output.writeToken(Integer.toString(s.State));
		output.writeToken(Integer.toString(s.Control));
		output.writeToken(Double.toString(s.Cost));
	}

}