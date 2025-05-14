package team.xg2.percussionplayer.chat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import team.xg2.percussionplayer.utils.PercussionUtils;

public class InputDialog implements EventHandler<ActionEvent>{
	private Stage inputStage;	 //��ǰ��̨
	private TextField nameField; //�û������ǳƵ��ı���
	private TextField ipField;	 //��������ip���ı���
	private Button btAdd;		 //ȷ�ϰ�ť
	private String name;		 //�û��س�
	private String ip;			 //��������IP��ַ
	private Label lblStatus;	 //������ʾ������ʾ�ı�ǩ
	private boolean[] onChatting;//�ж��ܷ�������ı�ʶ
	private double primaryStageWidth;	//����̨��ȣ�������������򵯳�λ��
	private double primaryStageHeight;	//����̨�߶�

	public InputDialog() {}
	public InputDialog(boolean[] onChatting, double primaryStageWidth, double primaryStageHeight) {
		this.onChatting = onChatting;
		this.onChatting[0] = true;
		this.primaryStageWidth = primaryStageWidth;
		this.primaryStageHeight = primaryStageHeight;
		buildGUI();
	}
	
	public void buildGUI() {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(10,10,10,10));
		pane.setHgap(5);
		pane.setVgap(5);
		
		nameField = new TextField();
		ipField = new TextField();
		
		pane.add(new Label("�ǳ�"),0,0);
		pane.add(nameField,1,0);
		pane.add(new Label("��������ip"),0,1);
		pane.add(ipField,1,1);
		btAdd = new Button("ȷ��");
		pane.add(btAdd,1,2);
		GridPane.setHalignment(btAdd,HPos.RIGHT);
		pane.add(new Label("��ܰ��ʾ:"), 0, 3);
		lblStatus = new Label();
		pane.add(lblStatus, 1, 3);
		
		// ��������ı����ȡ���㣬�򽫴�����ʾ����Ϊnull
		nameField.focusedProperty().addListener(e -> setLabelTip("black", "�������¼��Ϣ"));
		// �������2�ı����ȡ���㣬�򽫴�����ʾ����Ϊnull
		ipField.focusedProperty().addListener(e -> setLabelTip("black", "�������¼��Ϣ"));
		// ��ť��Ӽ�����
		btAdd.setOnAction(this);
		
		Scene scene = new Scene(pane);
		inputStage = new Stage();
		inputStage.setScene(scene);
		PercussionUtils.initStage(inputStage, primaryStageWidth/3, primaryStageHeight/3,"��¼");
		inputStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override						//���ڹر��¼�����
			public void handle(WindowEvent arg0) {
				onChatting[0] = false;
			}
		});
		
		inputStage.showAndWait();
	}

	@Override
	//��ť����������
	public void handle(ActionEvent e) {
		String regex = "[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}";
		if (nameField.getText().equals("") || ipField.getText().equals("")) {
		//����ı���Ϊ��
			setLabelTip("red", "������������¼��Ϣ");
			//��ɫ��ʾ
		} else if (!ipField.getText().matches(regex)) {
		//ip��ʽ������������ʽ
			setLabelTip("red", "��������IP��ַ��ʽ����");
		} else {
		//����Ҫ��
			name = nameField.getText();
			ip = ipField.getText();
			inputStage.close();
		}
	}
	
	//������ʾ��Ϣ
	public void setLabelTip(String color, String tip) {
		lblStatus.setStyle("-fx-text-fill: " + color + ";");
		lblStatus.setText(tip);
	}
	
	public String getName() {
		return name;
	}
	public String getIp() {
		return ip;
	}
}
