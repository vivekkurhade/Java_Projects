package team.xg2.percussionplayer.frame;

import java.util.List;

import javax.sound.midi.Sequencer;

import javafx.scene.control.CheckBox;

//������Ľӿڣ���Ϊ����������ϵ������

public interface MusicHandler {
	public void setUpMidi();
	public void buildTrackAndStart();
	public Sequencer getSequencer();
	public void setCheckbox(List<CheckBox> checkboxList);
}
