package team.xg2.percussionplayer.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import team.xg2.percussionplayer.utils.PercussionUtils;

public class ChatClient {
	private Stage chatStage;			//��ʾ��ǰ����̨
	private TextArea textBefore;		//��������
	private TextArea textNow;			//��������
	private Button sendBtn;				//���Ͱ�ť 
	private BufferedReader recvReader;	//��ȡ���յ���Ϣ
	private PrintWriter sendWriter;		//д�뷢�͵���Ϣ
	private Socket sock;				//�׽���
	private String userName;			//�û��ǳ�
	private String serverIp;			//��������ip
	private boolean[] onChatting;		//�Ƿ���Կ�������
	private double primaryWidth;		//��ȡ������Ŀ�ȣ������������촰�ڵ�λ��
	
	public ChatClient() {}
	public ChatClient(String userName, String serverIp, boolean[] onChatting, double primaryWidth) {
		this.userName = userName;
		this.serverIp = serverIp;
		this.onChatting = onChatting;
		this.primaryWidth = primaryWidth;
		buildGUI();
		init();
	}
	
	//ʵ�����촰��ͼ�ν���ķ���
	public void buildGUI() {
		BorderPane p0 = new BorderPane();
		//create border pane1 and border pane2
		BorderPane p1 = new BorderPane();
		BorderPane p2 = new BorderPane();
		BorderPane p3 = new BorderPane();
		
		p0.setPadding(new Insets(5,5,5,5));
		p1.setPadding(new Insets(0,5,5,5));
		p2.setPadding(new Insets(5,5,5,5));
		p3.setPadding(new Insets(5,5,5,15));
		
		p0.setTop(p1);
		p0.setCenter(p2);
		p0.setBottom(p3);
		p3.setCenter(sendBtn = new Button("����"));
		
		//�ı���
		textBefore = new TextArea();
		textNow = new TextArea();
		textBefore.setEditable(false);		//�������򲻿ɱ༭
		textBefore.setWrapText(true);		//�Զ�����
		textNow.setWrapText(true);
		textBefore.setPrefSize(400, 320);
		textNow.setPrefSize(400, 45);
		p1.setCenter(textBefore);
		p2.setCenter(textNow);
		
		chatStage = new Stage();
		chatStage.setScene(new Scene(p0));
		PercussionUtils.initStage(chatStage, primaryWidth, 0, userName+"�����촰��");
		chatStage.show();//��ʾ��̨
		
		chatStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override	//���ڹر��¼�����
			public void handle(WindowEvent arg0) {
				try {
					sendWriter.println(userName + "[" + InetAddress.getLocalHost().getHostAddress() + "]" + "�˳�������\r\n");
				} catch (Exception e) { e.printStackTrace(); }
				onChatting[0] = false;
			}
		});
	}

	public void init() {		
		//������Ϣ��ť��Ӽ���
		sendBtn.setOnAction(e ->  {
				try { //�����д������
					sendWriter.println(userName + "[" + InetAddress.getLocalHost().getHostAddress() + "]" + " ˵:");
					sendWriter.println(textNow.getText() + "\r\n");				
				} catch(Exception event) {
					System.out.println(event);
				}
				textNow.setText("");
				textNow.requestFocus();	//���۽��ڷ�����Ϣ���
			}
		);
		
		setUpConnection(serverIp);		//�����������������
		if (onChatting[0]) { 			//������Կ�������
			Thread readerThread = new Thread(new ReceMsgReader());
			readerThread.start(); 		//�����µ��̣߳�����������Է������˵���Ϣ
		} else {
			chatStage.close();
		}
	}
	
	//�����ͻ�����������˵�����
	public void setUpConnection(String ip) {
		try {
			sock = new Socket(ip, 6666); 
			recvReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
										//��ȡSocket�Ķ�ȡ��
			sendWriter = new PrintWriter(sock.getOutputStream(), true);
			sendWriter.println(userName + "[" + InetAddress.getLocalHost().getHostAddress() + "]" + "����������\r\n");
		} catch (Exception ex) {
			logInError(); 				//��¼ʧ��
			onChatting[0] = false; 		//�����������������
		}
	}
	
	//��¼ʧ�ܵĴ�����
	public void logInError() {
		//����Ի���
		String title = "����";
		String errmsg = "��������ʧ��";
		PercussionUtils.initDialog(title, errmsg, new Dialog<>());
	}
	
	//���մ������Է������˵���Ϣ
	public class ReceMsgReader implements Runnable {
		public void run() {
			String message;
			try {
				while ((message = recvReader.readLine()) != null) {
					textBefore.appendText(message + "\r\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
