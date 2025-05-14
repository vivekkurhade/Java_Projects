package team.xg2.percussionplayer.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.scene.control.ButtonBar.ButtonData;


//������

public class PercussionUtils {
	private PercussionUtils() {}
	
	//��ʼ����̨
	/*
	 * Stage stage����̨
	 * double relativeX�������Ļ1/4���ĺ�����
	 * double relativeY�������Ļ1/5����������
	 * String title����̨����
	 */
	public static void initStage(Stage stage, double relativeX, double relativeY, String title) {
		//������̨����λ��
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int screenWidth = (int)screen.getWidth();
		int screenHeight = (int)screen.getHeight();
		stage.setX(screenWidth/4 + relativeX);
		stage.setY(screenHeight/5 + relativeY);
		//������̨����
		stage.setTitle(title);
		//�������û������ڴ�С
		stage.setResizable(false);
	}
	
	//��ʼ���Ի���
	/*
	 * String title���Ի������
	 * String content���Ի�������
	 * Dialog<String> dialog���Ի���ʵ��
	 */
	public static void initDialog(String title, String content, Dialog<String> dialog) {
		 ButtonType confirmButtonType = new ButtonType("ȷ��", ButtonData.YES);
		 dialog.getDialogPane().getButtonTypes().add(confirmButtonType);
		 dialog.setTitle(title);
		 dialog.setContentText(content);
		 dialog.showAndWait();
	}
}
