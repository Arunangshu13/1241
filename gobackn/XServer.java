package gobackn;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.util.Scanner;



public class XServer {

	public static void main(String[] args) {
		try {
			System.out.println("Server has started ");
			ServerSocket xssocket=new ServerSocket(2452);
			System.out.println("Waiting for XClient's request ");
			Socket xsocket =xssocket.accept();
			System.out.println("XClient's request has bee accepted : ");
			
			Scanner scan =new Scanner(System.in);
			Scanner scanInt=new Scanner(System.in);
			System.out.println("Enter the data string to be send : ");//like 1 3 6 2 3 2 1 5 7 5 3 4 5 7 9 
			String data=scan.nextLine();
			String dataPackets[]=data.split(" ");
			BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(xsocket.getOutputStream()));
			BufferedReader reader=new BufferedReader(new InputStreamReader(xsocket.getInputStream()));
			//dataPackets[]= {1, 3, 6, 2, 3, 2, 1, 5, 7, 5, 3, 4, 5, 7, 9, 2, 6, 3}
			//                ___________
//			for now assume the window size to be 4
			int ack=0;
			for(int i=0;i<4;i++ )
			{
				System.out.print(" | "+dataPackets[i]+" --> "+i);
			}
			System.out.println();
			System.out.print("Waiting for ack...  ");
			String flag="next\r\n";
			writer.write(flag);
			writer.flush();
			
			ack=reader.read();
			
			int k=0;
			while(k!=dataPackets.length)
			{
				if(ack!=k)
				{
					System.out.println("Retransmitting frame : ");
					if((k+4)<dataPackets.length)
					{
						for(int j=k;j<(k+4);j++)
						{
							String token=dataPackets[j]+"\r\n";
							writer.write(token);
							writer.flush();
							System.out.print(" | "+dataPackets[j]+" --> "+j);
						}
					}
					else
					{
						for(int j=k;j<dataPackets.length;j++)
						{
							String token=dataPackets[j]+"\r\n";
							writer.write(token);
							writer.flush();
							System.out.print(" | "+dataPackets[j]+" --> "+j);
						}
					}
				}
				else
				{
					System.out.println("Current window has : ");
					k++;
					if((k+4)<dataPackets.length)
					{
						for(int j=k;j<(k+4);j++)
						{
							String token=dataPackets[j]+"\r\n";
							writer.write(token);
							writer.flush();
							System.out.print(" | "+dataPackets[j]+" --> "+j);
						}
					}
					else
					{
						for(int j=k;j<dataPackets.length;j++)
						{
							String token=dataPackets[j]+"\r\n";
							writer.write(token);
							writer.flush();
							System.out.print(" | "+dataPackets[j]+" --> "+j);
						}
					}
					System.out.println();
					if(k<dataPackets.length)
					{
						System.out.print("Next seqenceed data is sent : "+dataPackets[k]);
					}
					else
					{
						System.out.print("All packets has been sent successfully with a nomber of transmissio required : ");
						
						String token="bye\r\n";
						writer.write(token);
						writer.flush();
						break;
					}
				}
				String token="next\r\n";
				writer.write(token);
				writer.flush();
				System.out.print("\nWaiting for an ack ... ");
				ack=reader.read();
			}
			
		}catch(Exception ioe)
		{
			ioe.printStackTrace();
		}

	}

}

//ack=scanInt.nextInt();
//1 2 5 4 7 1 2 5 3 7 9 