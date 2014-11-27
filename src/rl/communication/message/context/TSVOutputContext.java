package rl.communication.message.context;

import java.io.BufferedWriter;
import java.io.IOException;

public class TSVOutputContext implements MessageOutputContext
{
	//現在位置が行頭であるかどうかを示す
	boolean atHeadOfLine;
	
	//書き込み先
	BufferedWriter MessageWriter;

	public TSVOutputContext(BufferedWriter bw)
	{
		MessageWriter=bw;
		atHeadOfLine = true;
	}

	// 行の最初の場合はそのまま書き込む
	// 現在行ですでに書き込みがある場合はタブ"\t"をつけてから書き込む
	// (行末にタブが書き込まれることを防止している)
	@Override
	public void writeToken(String s) throws IOException
	{
		//引数が空文字列の場合は何もしない
		if (s.equals(""))
		{
			return;
		}
		// 現在行ですでに書き込みがある場合はタブ"\t"を書き込む
		if (!atHeadOfLine)
		{
			//タブの書き込み
			MessageWriter.write("\t");
			
		}
		
		// sの書き込み
		MessageWriter.write(s);
		
		//行頭から進んだためatHeadOfLineを更新
		atHeadOfLine = false;
	}

	// 改行文字を書き込む
	@Override
	public void newLine() throws IOException
	{
		// 改行文字を書き込み
		MessageWriter.newLine();
		
		//行頭に移動したのでatHeadOfLineを更新
		atHeadOfLine = true;

	}

	@Override
	public void flush() throws IOException
	{
		MessageWriter.flush();
	}

}