package com.codezone.room;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
public class CSuserY {
 
	public static int temp=0;
	public static boolean flag=false;
	JFrame frame = null;
    JTextArea textArea = null;
    JButton send = null;
    JScrollPane pane = null;

    JTextArea textOp=null;
    JScrollPane output= null;
    public static Socket socket=null;
	CSuserY()
	{
		frame = new JFrame();
		
		//TextBox settings 
		textArea = new JTextArea();
		textArea.setVisible(true);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		pane = new JScrollPane(textArea);
		textArea.setBounds(10, 410, 210, 40);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pane.setBounds(10, 410, 210, 40);
		frame.add(pane);
		
		textOp=new JTextArea();
		textOp.setEditable(false);
		textOp.setLineWrap(true);
		textOp.setWrapStyleWord(true);
		textOp.setBounds(10, 10, 300, 400);
		textOp.setVisible(true);		
		output=new JScrollPane(textOp);
		output.setBounds(10, 10, 300, 400);
		output.setVisible(true);

		output.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		frame.add(output);

		
		//Send Button Settings 
		send=new JButton("Send");
		send.setVisible(true);
		send.setBounds(230, 410, 100, 40);
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				String data = textArea.getText();
				String gendata=textOp.getText();
				if(!data.equals(""))
				{
					if(flag)
					{
						textOp.append("\n");
						flag=false;
					}
					
					gendata="\n USER Y -->  "+data;
					textOp.append(gendata);
					frame.revalidate();
					frame.repaint();
					try {
						DataOutputStream dopt=new DataOutputStream(socket.getOutputStream());
						byte[] bytedata=data.getBytes();
						dopt.writeInt(bytedata.length);
						dopt.write(bytedata);	
						dopt.flush();
						textArea.setText("");
					}catch(IOException ioe)
					{
						ioe.printStackTrace();
					}
					
				}
				else
				{
					
				}
			}
		});
		frame.add(send);
	
		//Frame properties
		frame.setTitle("User Y ");
		frame.setBounds(100, 100, 350, 500);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		while(true) {
			String data="";
			try {
				DataInputStream dinp=new DataInputStream(socket.getInputStream());
				int datalength=dinp.readInt();
				byte bytes[]=new byte[datalength];
				dinp.read(bytes, 0, datalength);
				data=new String(bytes, StandardCharsets.UTF_8);
				
			}catch(IOException u){
				u.printStackTrace();
				break;
			}
			
			if(!data.equals("")) {
				if(!flag)
				{
					textOp.append("\n");
					flag=true;
				}
				
				String gendata=textOp.getText();
				gendata+="\n [USER X] ==> "+data;
				textOp.setText(gendata);
				frame.revalidate();
				frame.repaint();
				frame.revalidate();
				frame.repaint();
			}
		}
	}
	

	
	public static void main(String[] args) {
		try {
			socket=new Socket("127.0.0.1", 1442);
			System.out.println("User Y is up ");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		CSuserY obj=new CSuserY();
	}

}
