package rl.communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import rl.communication.message.Message;
import rl.communication.message.MessageProcedure;
import rl.communication.message.context.TSVInputContext;
import rl.communication.message.context.TSVOutputContext;

public class TCPServer
{
	private final int Port = 50000;

	public static void main(String args[])
	{
		TCPServer ts = new TCPServer();
		
		ts.ServerRun();
		
	}
	
	// サーバサービスの実行
	public void ServerRun()
	{
		// サーバ用のソケット
		ServerSocket serversocket = null;
		try
		{
			// ソケットの初期化
			serversocket = new ServerSocket(Port);
			// サーバの待ち受け状態が完了したことを標準出力に表示
			System.out.println("Ready to Transfer");
			// 待ち受け開始
			for (;;)
			{
				try (
				// 接続があれば接続ソケットを新規に作成
				Socket s = serversocket.accept();)
				{
					// クライアントからのリクエストを処理
					run(s);
				}
			}
		} catch (Exception e)
		{
			System.out.println(e);
		} finally
		{
			// serversocketの開放
			try
			{
				serversocket.close();
			} catch (Exception e)
			{
			}
		}
	}

	public void run(Socket socket) throws Exception
	{
		try
		(
			// メッセージを受信するためのストリーム
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			// 受信メッセージ読み取り用Contextの初期化
			TSVInputContext tsvinc = new TSVInputContext(in);
			// クライアントへの送信用出力ストリーム
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			// 返信メッセージ書き込み用Contextの初期化
			TSVOutputContext tsvoutc= new TSVOutputContext(out);
			
		)
		{

			//メッセージプロシージャの初期化
			MessageProcedure mp = new Message();
			
			// MessageProcedureにmessagebodyreaderを渡しコマンド実行
			mp.process(tsvinc,tsvoutc);
			
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

	}




}
