package org.yj.media;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.socks.library.KLog;

public class AudioPlay {
	private boolean isPlaying = false;

	private int audioTime = -1;// 时间戳
	private int audioType = -1; // 语音类型， 0表示adpcm
	private int audioCycle = -1; // 赫兹
	private int audioBit = -1; // 采样位数
	private int audioChannel = -1; // 声道
	private int audioBlock = -1; // 原始块大小
	private AudioTrack audioTrack; // 音频播放类
	private byte blockBuff[];
	private byte adpcmBuff[];
	private boolean isStop = true;

	public AudioPlay() {

	}

	public void start() {
		isStop = false;
	}
	
	/**
	 * 开始播放
	 */
	public void play(byte[] head) {
		if(false == isStop && null != audioTrack) {
			try{
				audioTrack.write(head, 0, head.length);
				audioTrack.flush();
			} catch(Exception e) {
				KLog.e("onAudio play error:" + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * 停止播放
	 */
	public void stop() {
		isStop = true;
		if (null != audioTrack) {
			audioTrack.stop();
			audioTrack.release();
			audioTrack = null;
			audioCycle = 0;
			audioChannel = 0;
		}
	}
	
	/**
	 * 设置采样率
	 */
	public void setConfig(int sample, int channel) {
//		System.out.println("====================audioTrack.play():stop:" + isStop);
		if(false != isStop) {
			return;
		}
		
		if(audioCycle == sample && audioChannel == channel) {
			return;
		}
		
		audioCycle = sample;
		audioChannel = channel;
		
		if (null != audioTrack) {
			audioTrack.stop();
			audioTrack.release();
			audioTrack = null;
		}
		
		try{
			int format = (2==audioChannel)?AudioFormat.CHANNEL_CONFIGURATION_STEREO:AudioFormat.CHANNEL_CONFIGURATION_DEFAULT;
			int bufsize = AudioTrack.getMinBufferSize(audioCycle, audioChannel, format);// audioCycle * 20;
			audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
					audioCycle,
					format,
					AudioFormat.ENCODING_PCM_16BIT, bufsize,
					AudioTrack.MODE_STREAM);
			audioTrack.play();
			KLog.e("====================audioTrack.play()");
		} catch( Exception e) {
			KLog.e("onAudio error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
