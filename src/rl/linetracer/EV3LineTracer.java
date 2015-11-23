package rl.linetracer;

import rl.*;
import rl.communication.TCPServer;

//TODO TEST
public class EV3LineTracer
{
	//////////////////////////////////////////
	public static void main(String args[])
	{
		TCPServer ts = new TCPServer();
		
		ts.ServerRun();
		
	}
	///////////////////////////////////////////
	
	//EV3LineTracerの唯一のインスタンス
	private static EV3LineTracer ev3 = new EV3LineTracer();
	///////////////////////////////////////////
	///////////////////////////////////////////
	private final int regularInterval=10;
	private MachineControl MC;
	private StochasticPolicy RegularPolicy;
	private StochasticPolicy CurrentPolicy;
	private boolean IsReady;
	private long StartTime;
	private MDPManager mdpManager;
	
	//唯一のコンストラクタ
	//Singletonにするため、privateにしている
	private EV3LineTracer()
	{
		MC=new MachineControl();
		IsReady=false;
		mdpManager = new MDPManagerRefmax();
	}
	
	//EV3LineTracerのインスタンスの取得
	//このメソッドが最初に実行される際にインスタンスが生成される
	public static EV3LineTracer getInstance()
	{
		return ev3;
	}
	
	
	//Intervalを設定する
	public void SetInterval(int t)
	{
		mdpManager._setInterval(t);
	}
	//StateCountを指定して、
	//StateとConrolの配列の領域を確保する
	public void SetStateCount(int statecount)
	{
		mdpManager._SetStateCount(statecount);
	}
	//Stateを設定する
	public void SetState(int i,double refmax,int controlcount)
	{
		mdpManager._SetState(i, refmax, controlcount);
	}
	//StateCountの取得
	public int GetStateCount()
	{
		return mdpManager.getStateCount();
	}
	//ControlCountの取得
	public int GetControlCount(int i)
	{
		return mdpManager.getControlCount(i);
	}
	//Contolを設定する
	public void SetControl(int i,int u,int lmotorspeed,int rmotorspeed)
	{
		mdpManager._SetControl(i, u, lmotorspeed, rmotorspeed);
	}
	//RegularPolicyの設定
	public void SetRegularPolicy(double[][] prob)
	{
		RegularPolicy=new StochasticPolicy(prob);
	}
	//CurrentPolicyの設定
	public void SetCurrentPolicy(double[][] prob)
	{
		CurrentPolicy=new StochasticPolicy(prob);
	}
	//CurrentPolicyの設定
	public void SetCurrentPolicy(StochasticPolicy p)
	{
		CurrentPolicy=p;
	}


	//経過時間を取得する
	public double GetElapsedTime()
	{
		double NowTime=java.lang.System.currentTimeMillis();
		return (double)(NowTime-StartTime)/1000.0;
	}
	//現在のStateとCostを取得する
	//argument:
	//		Step step:	取得したStateを格納するStep
	//		Step previousstep:	取得したCostを格納するStep
	public void GetStateAndCost(Step step,Step previousstep)
	{
		double elapsed_time = GetElapsedTime();
		
		switch(MC.GetColor())
		{
		//ゴールした場合
		case Color.RED:
			step.State=0;
			previousstep.Cost=mdpManager.getCostWhenGoal(step, elapsed_time);//GetElapsedTime();
			break;
		//コースアウトした場合
		case Color.BLUE:
			step.State=0;
			previousstep.Cost=mdpManager.getCostWhenCourseOut(step,elapsed_time);//CostMax;
			break;
		//上記以外(コースを進行中の場合)
		default:
			if(elapsed_time >= mdpManager._getCostMax())
			{
				//CostMax秒以上経過した場合
				//タイムアウトとして終了
				step.State=0;
				previousstep.Cost=mdpManager.getCostWhenTimeOut(step, elapsed_time);//CostMax;
			}
			//現在のstateを求める
			mdpManager.getCurrentState(step, MC);
			previousstep.Cost=mdpManager.getCostWhenRunning(step, elapsed_time);//0.0;
			break;
		}
	}
	//コースに復帰する
	public void ReturnCource()
	{
		int lspeed=  0;
		int rspeed=100;
		int c=0;
		while((!MC.OnBlackLine())&&(MC.GetColor()!=Color.RED))
		{
			MC.GoBack(lspeed,rspeed);
			MC.Delay(regularInterval);
			c++;
			c%=10;
			if((c==0)&&(lspeed<rspeed))
			{
				lspeed++;
			}
		}
		//EV3を停止させる
		MC.Stop();
	}
	
	//State、CurrentPolicyに応じたControlを取得する
	public void GetControl(Step step)
	{
		//Controlを定める
		step.Control=CurrentPolicy.At(step.State).Get();
	}
	//指定したControlに応じた行動を行う
	public void DoControl(Step step)
	{
		mdpManager.doControl(step, MC);
	}
	//Episodeを実行する準備を行う
	//コースアウトしていたら復帰し、
	public void Ready()
	{
		//既に準備完了なら終了
		if(IsReady)
		{
			return;
		}
		
		//コースアウトしていたらコースに復帰する
		//ReturnCource();
		
		//スタートラインをわずかに超えていた場合に備え、
		//少し後退しておく
		System.out.println("--------------------GoBack().");//TODO
		MC.GoBack(50, 50);
		MC.Delay(500);
		
		//CurrentPolicyをoldpolicyに退避しておく
		StochasticPolicy oldpolicy=CurrentPolicy;
		//CurrentPolicyをRegularPolicyに設定する
		CurrentPolicy=RegularPolicy;
		Step step=new Step(0,0,0.0);
		//EV3がスタートラインに達するまでループ
		while(MC.GetColor()!=Color.RED)
		{
			if(MC.GetColor()==Color.BLUE)
			{
				//コースアウトしている場合
				//コースに復帰する
				ReturnCource();
			}
			//ポリシーに従って走行する
			GetStateAndCost(step,step);
			GetControl(step);
			DoControl(step);
		}
		System.out.println("--------------------On Red Line.");//TODO
		//EV3がスタートラインを超えるまで前進する
		//whitecountmax回連続で白を検出したら超えたと判断する
		int whitecounter=0;
		int whitecountmax=10;
		while(whitecounter!=whitecountmax)
		{
			switch(MC.GetColor())
			{
			case Color.WHITE:
				//カウンタを1つ増やす
				whitecounter++;
				break;
			default:
				//カウンタをリセットする
				whitecounter=0;
				break;
			}
			
			MC.GoForward(50, 50);
			MC.Delay(10);
		}
		System.out.println("--------------------Over Red Line.");//TODO
		//EV3を停止させる
		MC.Stop();
		//CurrentPolicyを退避していたものに戻す
		CurrentPolicy=oldpolicy;
		//準備完了状態にする
		IsReady=true;
	}
	//Episodeを開始する
	private void StartEpisode()
	{
		StartTime=System.currentTimeMillis();
		IsReady=false;
		System.out.println("--------------------Episode Start.");//TODO
	}
	
	
	//Episodeを実行する
	public void ExecEpisode(Episode e)
	{
		//Episodeを初期化する
		e.ClearEpisode();
		
		System.out.println("--------------------Ready() Start..");//TODO
		MC.OnLED(MachineControl.LED_PATTERN_STATIC_RED);//TODO
		//スタートラインに着く
		Ready();
		
		System.out.println("--------------------Ready() End.");//TODO
		
		//Episodeを開始する
		StartEpisode();
		MC.OnLED(MachineControl.LED_PATTERN_STATIC_GREEN);//TODO
		
		//次のStep
		Step nextstep=new Step(0,0,0.0);
		//現在のStep
		Step currentstep=new Step(0,0,0.0);
		
		//現在の状態を取得
		GetStateAndCost(currentstep,new Step(0,0,0.0));
		while(true)
		{
			//StateとCurrentPolicyに応じたControlを取得
			GetControl(currentstep);
			//Controlに応じた行動を実行
			DoControl(currentstep);
			//次のStateと行動の結果掛かったCostを取得
			GetStateAndCost(nextstep,currentstep);
			//現在のStepをEpisodeに追加
			e.AddStep(currentstep);
			//TerminateStateに達したらループを抜けて終了
			if(nextstep.State==0)
			{
				nextstep.Control=0;
				nextstep.Cost=0.0;
				e.AddStep(nextstep);
				break;
			}
			
			//nextstepとcurrentstepを入れ替える
			Step dummy=nextstep;
			nextstep=currentstep;
			currentstep=dummy;
		}
		//Episode終了
		//EV3を停止させる
		MC.Stop();
		System.out.println("--------------------Episode End.");//TODO
		MC.OnLED(MachineControl.LED_PATTERN_OFF);//TODO
	}
	public int GetInterval()
	{
		return mdpManager._getInterval();
	}
	public StochasticPolicy GetRegularPolicy()
	{
		return RegularPolicy;
	}
	public StochasticPolicy GetCurrentPolicy()
	{
		return CurrentPolicy;
	}

	public State GetState(int i)
	{
		return mdpManager._GetState(i);
	}

	public Control GetControl(int i, int u)
	{
		return mdpManager._GetControl(i, u);
	}

	public void setCostMax(double cost_max)
	{
		mdpManager._setCostMax(cost_max);
	}

	public double GetCostMax()
	{
		return mdpManager._getCostMax();
	}


}
