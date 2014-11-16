package rl.communication.message.context;

public class TSVOutputContext implements OutputContext
{
	//現在位置が行頭であるかどうかを示す
	boolean atHeadOfLine;
	
	//書き込み先
	// TODO 書き込み先メンバ変数を作成する

	TSVOutputContext()
	{
		atHeadOfLine = true;
	}

	// 行の最初の場合はそのまま書き込む
	// 現在行ですでに書き込みがある場合はタブ"\t"をつけてから書き込む
	// (行末にタブが書き込まれることを防止している)
	@Override
	public void writeToken(String s)
	{
		//引数が空文字列の場合は何もしない
		if (s.equals(""))
		{
			return;
		}
		// 現在行ですでに書き込みがある場合はタブ"\t"を書き込む
		if (!atHeadOfLine)
		{
			// TODO タブの書き込み
		}
		
		// TODO sの書き込み
		
		//行頭から進んだためatHeadOfLineを更新
		atHeadOfLine = false;
	}

	// 改行文字を書き込む
	@Override
	public void newLine()
	{
		// TODO 改行文字を書き込み
		
		//行頭に移動したのでatHeadOfLineを更新
		atHeadOfLine = true;

	}

}