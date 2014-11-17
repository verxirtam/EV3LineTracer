package rl.communication.message.context;

import java.io.IOException;

public interface MessageOutputContext
{
	public void writeToken(String s) throws IOException;
	public void newLine() throws IOException;
	
}