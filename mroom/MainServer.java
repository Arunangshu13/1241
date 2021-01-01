package com.codezone.mroom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Vector;
public class MainServer implements Runnable {
	private Socket socket;
	public static Vector<BufferedWriter> clients=new Vector<BufferedWriter>();
	
	MainServer(Socket socket){
		this.socket=socket;
	}
	public void run() {
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader( socket.getInputStream()));
			BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			clients.add(writer);
			while(true)
			{
				String str=	reader.readLine().trim();
				System.out.println(str);
				for(int i=0;i<clients.size();i++)
				{
					BufferedWriter bw=(BufferedWriter)clients.get(i);
					bw.write(str);
					bw.write("\r\n");
					bw.flush();
				}
			}
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		try {
			ServerSocket server=new ServerSocket(1432);
			System.out.println("Server started ");
			while(true)
			{
				Socket socket=server.accept();
				MainServer mainServer =new MainServer(socket);
				Thread thread=new Thread(mainServer);
				thread.start();
			}
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

}
