package gobackn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
public class XClient {

	public static void main(String[] args) {
		try {
			Scanner scan =new Scanner(System.in);
			
			Socket xsocket=new Socket("localhost", 2452);
			System.out.println("XClient is up ...");
			BufferedReader reader=new BufferedReader(new InputStreamReader(xsocket.getInputStream()));
			BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(xsocket.getOutputStream()));
			String receive="";
			List<String> dataReceived=new ArrayList<String>();
			String temp="";
			while(true)
			{
				receive=reader.readLine();
				System.out.print("Received from server : ");
				System.out.print(" "+receive);
				
				
				
				System.out.println();
				
				if(receive.equals("next"))
				{
					System.out.println("Enter Ack : ");
					int send=scan.nextInt();
					if(send!=-1)
					{
						dataReceived.add(String.valueOf(temp.charAt(0)));
					}
					temp="";
					writer.write(send);
//					writer.write("\r\n");
					writer.flush();
				
				}
				else if(receive.equals("bye"))
				{
					break;
				}
				else
				{
					temp+=receive;
				}
			}
			System.out.println("Pleasure doing businsess");
			System.out.print("Actual data received from server : "+dataReceived);
			
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}

	}

}
