package team.xg2.percussionplayer.frame;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import team.xg2.percussionplayer.chat.ChatClient;
import team.xg2.percussionplayer.chat.InputDialog;
import team.xg2.percussionplayer.music.PercussionMusic;
import team.xg2.percussionplayer.utils.PercussionUtils;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Sequencer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;

// �������������
public class PercussionFrame extends Application {
	//���ڴ洢����̨�����㵯�����
	private Stage primaryStage;
	//��ѡ���б�
	private List<CheckBox> checkboxList;
	// ���ð�ť
	private Button btStart;
	private Button btStop;
	private Button btUpTempo;
	private Button btDownTempo;
	private Button btRandom;
	private Button btClear;
	private Button btSerializelt;
	private Button btLoad;
	private Button btChat;
	private Button btAbout;
	//���ֵĴ�����
	private MusicHandler musicHandler;
	//���ſ���
	private Sequencer sequencer;
	//��ʶ�Ƿ���Խ���������
	public boolean[] onChatting = new boolean[]{false};
	
	public PercussionFrame() {
		musicHandler = new PercussionMusic();	//music��ͱ���ͨ���ӿ���ϵ
	}
	
	//����start����������ͼ�ν���
	public void buildGUI() { 
		Application.launch(new String[0]);
	}
	
	//ʵ����ͼ�ν���ķ���
	public void start(Stage primaryStage) {
		//��ȡ����̨
		this.primaryStage = primaryStage;
		
		//��ȡ������ť
		btStart = new Button("��ʼ����");
		btStop = new Button("��ͣ����");
		btUpTempo = new Button("��ǿ����");
		btDownTempo = new Button("��������");
		btRandom = new Button("�������");
		btClear = new Button("��ս���");
		btSerializelt = new Button("�浵");
		btLoad = new Button("����");
		btChat = new Button("����");
		btAbout = new Button("����");
		
		//ͳһ��ť�ߴ�
		btStart.setPrefWidth(80);
		btStop.setPrefWidth(80);
		btUpTempo.setPrefWidth(80);
		btDownTempo.setPrefWidth(80);
		btRandom.setPrefWidth(80);
		btClear.setPrefWidth(80);
		btSerializelt.setPrefWidth(80);
		btLoad.setPrefWidth(80);
		btChat.setPrefWidth(80);
		btAbout.setPrefWidth(80);
		
		// �����:BorderPane
		BorderPane bPane = new BorderPane();
		bPane.setPadding(new Insets(10, 10, 10, 10));
		
		// �������ӵ�scene
		Scene scene = new Scene(bPane);
		
		// ���ñ���ͼƬ
		Image image = new Image("background.png");
		ImageView iv = new ImageView();
		iv.setImage(image);
		iv.setSmooth(true);
		iv.fitHeightProperty().bind(bPane.heightProperty());
		iv.fitWidthProperty().bind(bPane.widthProperty());
		bPane.getChildren().add(iv);

		// �����������\�м�\�ұ�����GridPane���
		GridPane gPaneleft = new GridPane();
		GridPane gPaneright = new GridPane();
		GridPane gPanecenter = new GridPane();
		gPanecenter.setPadding(new Insets(0, 5, 0, 5));	// �м�������߾�
		bPane.setLeft(gPaneleft);
		bPane.setCenter(gPanecenter);
		bPane.setRight(gPaneright);

		// ���������ڲ��ڵ���
		gPaneleft.setVgap(6);
		gPaneright.setVgap(6);
		gPanecenter.setVgap(5);

		// ���������label
		gPaneleft.add(new Label("������"), 0, 0);
		gPaneleft.add(new Label("����"), 0, 1);
		gPaneleft.add(new Label("����"), 0, 2);
		gPaneleft.add(new Label("ԭ������"), 0, 3);
		gPaneleft.add(new Label("ǿ����"), 0, 4);
		gPaneleft.add(new Label("����"), 0, 5);
		gPaneleft.add(new Label("����Ͱ��"), 0, 6);
		gPaneleft.add(new Label("�������"), 0, 7);
		gPaneleft.add(new Label("ɳ��"), 0, 8);
		gPaneleft.add(new Label("����"), 0, 9);
		gPaneleft.add(new Label("��������"), 0, 10);
		gPaneleft.add(new Label("ţ��"), 0, 11);
		gPaneleft.add(new Label("������"), 0, 12);
		gPaneleft.add(new Label("����Ͱ��"), 0, 13);
		gPaneleft.add(new Label("������������"), 0, 14);
		gPaneleft.add(new Label("��������"), 0, 15);

		// �ұ������Ӱ�ť
		gPaneright.add(btStart, 0, 0);
		gPaneright.add(btStop, 0, 1);
		gPaneright.add(btUpTempo, 0, 2);
		gPaneright.add(btDownTempo, 0, 3);
		gPaneright.add(btRandom, 0, 4);
		gPaneright.add(btClear, 0, 5);
		gPaneright.add(btSerializelt, 0, 6);
		gPaneright.add(btLoad, 0, 7);
		gPaneright.add(btChat, 0, 8);
		gPaneright.add(btAbout, 0, 9);
		
		checkboxList = new ArrayList<CheckBox>();
		// ����16��*16�и�ѡ��,�����м����
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				CheckBox c = new CheckBox("");
				c.setOpacity(0.75);
				c.setSelected(false);
				checkboxList.add(c);
				gPanecenter.add(c, i, j);
			}
		}
		// ��scene��ӵ�stage
		primaryStage.setScene(scene);
		// ���ù������ʼ����̨
		PercussionUtils.initStage(primaryStage, 0, 0, "�û��ִ���");
		//��ʾ��̨
		primaryStage.show();
		//���ùرճ���
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				System.exit(0);
			}
		});
		//��ʼ������
		init(true);
	}
	
	//��ʼ������
	public void init(boolean bl) {
		//��ťע���¼�
		if (bl) {
			btStart.setOnAction(new StartAction());
			btStop.setOnAction(new StopAction());
			btUpTempo.setOnAction(new UpTempoAction());	
			btDownTempo.setOnAction(new DownTempoAction());
			btRandom.setOnAction(new RandomAction());
			btClear.setOnAction(new ClearAction());		
			btSerializelt.setOnAction(new SaveAction());
			btLoad.setOnAction(new LoadAction());
			btChat.setOnAction(new ChatAction());
			btAbout.setOnAction(new AboutAction());
			
			musicHandler.setUpMidi();		
			sequencer = musicHandler.getSequencer();	//��ò��ſ�����
		}
	}

	//��ʼ���ŵ��¼���������
	public class StartAction implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			musicHandler.setCheckbox(checkboxList);
			musicHandler.buildTrackAndStart();
		}
	}
	//��ͣ���ŵ��¼���������
	public class StopAction implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			sequencer.stop();
		}
	}
	//��ǿ������¼���������
	public class UpTempoAction implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			float tempoFactor = sequencer.getTempoFactor(); 		//�����������ĵ�ǰ�ٶ����ӡ� Ĭ��ֵΪ1.0
			sequencer.setTempoFactor((float)(tempoFactor * 1.03));	//��ǿ����
		}
	}
	//����������¼���������
	public class DownTempoAction implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * 0.97));	//��������
		}
	}
	//����������¼���������
	public class RandomAction implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			for (CheckBox checkBox : checkboxList) {
				checkBox.setSelected(Math.random() > 0.8); //����1��4�ı�����
			}
			sequencer.stop();
		}
	}
	//��ս��ĵ��¼���������
	public class ClearAction implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			for (CheckBox checkBox : checkboxList) {
				checkBox.setSelected(false);
			}
			sequencer.stop();
		}
	}
	//�浵���¼���������
	public class SaveAction implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			boolean[] checkboxState = new boolean[256];
			for (int i = 0; i < 256; ++i) {
				CheckBox check = checkboxList.get(i);
				if (check.isSelected()) {
					checkboxState[i] = true;
				}
			}
			FileChooser fileSave = new FileChooser();
			File file = fileSave.showSaveDialog(primaryStage);
			if (file != null)
				saveFile(file, checkboxState);	
		}
	}
	//������¼���������
	public class LoadAction implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			FileChooser fileLoad = new FileChooser();
			File file = fileLoad.showOpenDialog(primaryStage);
			if (file == null) return;
			boolean[] checkboxState = loadFile(file);
			if (checkboxState != null)
				for (int i = 0; i < 256; ++i) {
					CheckBox check = checkboxList.get(i);
					check.setSelected(checkboxState[i]);	//��ѡ���
				}
			else {
				//����Ի���
				String title = "����";
				String content = "�����ļ�ʧ�ܣ�������ѡ��";
				PercussionUtils.initDialog(title, content, new Dialog<>());
			}
			sequencer.stop(); 	//ֹͣ���ڲ��ŵĽ���
		}
	}
	//������¼���������
	public class ChatAction implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (!onChatting[0]) { //�����ǰû�п������죬����Ե�����½��
				InputDialog input = new InputDialog(onChatting, primaryStage.getWidth(), primaryStage.getHeight());
				if (onChatting[0]) { //����û�����������������˵�¼������Խ���������
					new ChatClient(input.getName(), input.getIp(), onChatting, primaryStage.getWidth());
				}
			} else {
				//����Ի���
				String title = "����";
				String content = "�����ڵ�¼ģʽ���ѽ���������";
				PercussionUtils.initDialog(title, content, new Dialog<>());
			}
		}
	}
	//���ڵ��¼���������
	public class AboutAction implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			//��ȡ�ļ�����ʾ���Ի�����
			String title = "����";
			String content = readFile(new File("README.txt")).toString(); 
			PercussionUtils.initDialog(title, content, new Dialog<>());
		}
	}
	//�洢�ļ�	
	public void saveFile(File file, boolean[] checkboxState) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(checkboxState);
			os.close();
		} catch(Exception ex) {
			System.out.println(ex + "\r\n�浵ʧ��");
		}
	}
	//�����ļ�
	public boolean[] loadFile(File file) {
		boolean[] checkboxState = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream is = new ObjectInputStream(fis);
			checkboxState = (boolean[])is.readObject();
			is.close();
		} catch (Exception ex) {
			return null;
		}
		return checkboxState;
	}
	//��ȡ�ļ�
	public StringBuilder readFile(File file) {
		StringBuilder destString = new StringBuilder();
		try {
			InputStream in = PercussionFrame.class.getResourceAsStream(file.getName()); //�ӵ�ǰ������Ŀ¼Ѱ���ļ�
			BufferedReader bufr = new BufferedReader(new InputStreamReader(in));
			String line = null;
			boolean flag = false;
			while ((line = bufr.readLine()) != null) {
				if (flag) destString.append("\r\n" + line);
				else flag = true;
			}
			bufr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destString;
	}
}