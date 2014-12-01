package rl;

import rl.Step;

//MDPのEpisodeを表すクラス
public class Episode
{
	// Episodeを成すStepを格納するArrayList
	private java.util.ArrayList<Step> Steps;

	// コンストラクタ
	// 空のエピソードを作成する
	public Episode()
	{
		Steps = new java.util.ArrayList<Step>();
	}

	// コピーコンストラクタ
	public Episode(Episode e)
	{
		//eでthisを初期化する
		setEpisode(e);
	}

	//インスタンスeと同一の内容をthisに設定する
	public void setEpisode(Episode e)
	{
		//thisとeが同一のインスタンスを指しているときは何もしない
		if(this==e)
		{
			return;
		}
		
		//空のエピソードを作成
		Steps = new java.util.ArrayList<Step>();

		//eのStepを設定する
		int step_count = e.GetStepCount();
		for (int i = 0; i < step_count; i++)
		{
			this.AddStep(e.GetStep(i));
		}
	}
	
	public int GetStepCount()
	{
		return Steps.size();
	}

	// Stepを追加する
	// 引数のStepのコピーを作ってStepsに追加する
	public void AddStep(Step s)
	{
		Step t = new Step(s);
		Steps.add(t);
	}

	// Stepを追加する(Stepの内容を個別に指定)
	public void AddStep(int state, int control, double cost)
	{
		Step t = new Step(state, control, cost);
		this.AddStep(t);
	}

	// i番目のStepを取得する
	public Step GetStep(int m)
	{
		return Steps.get(m);
	}

	// Episode内のStepを全て削除する
	public void ClearEpisode()
	{
		Steps.clear();
	}
}
