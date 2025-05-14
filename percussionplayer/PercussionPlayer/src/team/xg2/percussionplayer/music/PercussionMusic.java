package team.xg2.percussionplayer.music;

import java.util.List;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import javafx.scene.control.CheckBox;
import team.xg2.percussionplayer.frame.MusicHandler;

//������

public class PercussionMusic implements MusicHandler{
	private Sequencer sequencer;				//���ſ�����
	private Sequence sequence;					//��Ϣ��֯
	private Track track;						//������洢�����¼�
	private List<CheckBox> checkboxList;		//��ѡ���б�
	
	private int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};
	
	public Sequencer getSequencer() {
		return sequencer;
	}
	
	public void setCheckbox(List<CheckBox> checkboxList) {
		this.checkboxList = checkboxList;
	}
	
	public void buildTrackAndStart() {
		int[] trackList = null;				//�洢һ��������ֵ
		
		sequence.deleteTrack(track);		//���ȥ�ɵ�track�������µ�
		track = sequence.createTrack();
		
		for (int i = 0; i < 16; ++i) {		//ÿ��ִ��һ������
			trackList = new int[16];
			int key = instruments[i];		//�趨�����Ĺؼ���
			for (int j = 0; j < 16; ++j) {
				CheckBox jc = checkboxList.get((16*i) + j);
											//16*i ��Ϊ�˶�λ����i�У�+j��Ϊ�˶�λ����j��		
				trackList[j] = jc.isSelected() ? key : 0;
											//��ѡ�򱻹�ѡ
			}		
			makeTracks(trackList);			//�������������¼����ӵ�track��
			track.add(makeEvent(176, 1, 127, 0, 16));
		}
		
		track.add(makeEvent(192, 9, 1, 0, 16));	
											//ȷ����16�Ĵ����¼����Ա�֤beatbox�����ظ�����
		try {
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);	//ָ��������ظ�����
			sequencer.start();				//��ʼ����
			sequencer.setTempoInBPM(120);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//��ʼ��Midi��Ϣ
	public void setUpMidi() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, 4);
			track = sequence.createTrack();	//�����������
			sequencer.setTempoInBPM(120);	//����ÿ���ӽ��ĵ��ٶ�
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//����ĳ�������������¼�
	public void makeTracks(int[] list) {	
		for (int i = 0; i < 16; ++i) {
			int key = list[i];
			
			if (key != 0) {
				track.add(makeEvent(144, 9, key, 100, i));	//note on�¼�
				track.add(makeEvent(128, 9, key, 100, i+1));//note off�¼�
			}
		}
	}
	//����Midi�¼����൱��һ��������Ϣ
	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return event;
	}



}
