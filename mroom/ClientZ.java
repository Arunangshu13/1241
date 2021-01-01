package com.codezone.mroom;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;

public class ClientZ extends JFrame implements ActionListener, Runnable{
	BufferedWriter writer;
	BufferedReader reader;
	JScrollPane scroller;
	JScrollPane scrollerInput;
	JTextArea textArea=new JTextArea();
	JTextArea textAreaMain=new JTextArea();
	JButton btn=new JButton("send");
	ClientZ(){
		this.setTitle("Client Z");
		this.setVisible(true);
		this.setSize(350, 500);
		this.setLocation(50, 50);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		btn.setLocation(230, 420);
		btn.setSize(100, 40);
		btn.addActionListener(this);
		add(btn);
		
		
		textArea.setWrapStyleWord(true);
		scrollerInput=new JScrollPane(textArea);
		scrollerInput.setSize(210, 40);
		scrollerInput.setLocation(10, 420);
		
		scrollerInput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollerInput);
		
		textAreaMain.setEditable(false);
		scroller=new JScrollPane(textAreaMain);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setLocation(10,10);
		scroller.setSize(310,400);
		add(scroller);

		try {
			Socket socketClinet =new Socket("localhost",1432);
			writer = new BufferedWriter (new OutputStreamWriter(socketClinet.getOutputStream()));
			reader = new BufferedReader (new InputStreamReader(socketClinet.getInputStream()));
			
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	public void run() {
		try {
			String msg="";
			while((msg=reader.readLine())!=null)
			{
				textAreaMain.append(msg);
				textAreaMain.append("\r\n");
			}
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent ae)
	{
		String text="[ Client Z ] : "+textArea.getText()+"\r\n";
		textArea.setText("");
		try {
			writer.write(text);
			writer.flush();
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ClientZ client=new ClientZ();
		
		Thread thread=new Thread(client);
		thread.start();
	}

}
