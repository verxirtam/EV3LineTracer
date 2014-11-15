package rl.communication.message.context;

import java.io.BufferedReader;
import java.io.IOException;

// タブ区切りのメッセージ
public class TSVContext implements MessageContext
{
	// メッセージを保持するBufferedReader
	BufferedReader MessageReader;
	// 現在の行をタブ区切りでTokenにわけて配列で保持したもの
	String[] CurrentLine;
	// CurrentLine上での現在位置
	int CurrentIndex;
	// 処理対象オブジェクト
	Object Target;

	// 現在行を次の行に移す
	// 行末に達しているかどうかのチェックは行わない
	private void newLine() throws IOException
	{
		// 次の行を取得
		String newline = MessageReader.readLine();
		// バッファの最後に達したか確認
		if (newline == null)
		{
			throw new IOException("End of Buffer.");
		}
		// 取得した行をタブ区切りに分割
		CurrentLine = newline.split("\t");
		// 現在位置の初期化
		CurrentIndex = -1;
	}

	public TSVContext(BufferedReader br, Object target) throws IOException
	{
		MessageReader = br;
		newLine();

		Target = target;
	}

	@Override
	public String nextToken() throws Exception
	{
		// 現在位置が行末か確認
		if (!hasNextToken())
		{
			// 行末なら例外発生
			throw new Exception("error on nextToken()");
		}
		// 現在位置を1つ進める
		CurrentIndex++;
		// 進めた位置でのTokenを返却
		return CurrentLine[CurrentIndex];
	}

	@Override
	public void skipToken(String skipString) throws Exception
	{
		// 次のTokenが無ければ例外発生
		if (!hasNextToken())
		{
			throw new Exception("error on skipToken():" + "end of line.");
		}
		// 次のTokenがskipStringと一致して入れば現在位置を進める
		if (CurrentLine[CurrentIndex + 1].equals(skipString))
		{
			CurrentIndex++;
		} else
		{
			// 一致していない場合は例外発生
			throw new Exception(
					"error on skipToken():NExtString is unmatch to "
							+ skipString);
		}

	}

	@Override
	public void skipReturn() throws Exception
	{
		// 現在行に次のTokenがある場合は例外発生
		if (hasNextToken())
		{
			throw new Exception("error on skipReturn():"
					+ "here is not end of line.");
		}
		// 次の行に進む
		newLine();

	}

	@Override
	public boolean hasNextToken()
	{
		// 現在行で次のTokenがあるかを返却する(Tokenがあるならtrue)
		return CurrentIndex != (CurrentLine.length - 1);
	}

	@Override
	public Object getTarget()
	{
		return Target;
	}

}