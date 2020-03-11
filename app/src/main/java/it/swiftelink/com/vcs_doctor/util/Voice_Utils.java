package it.swiftelink.com.vcs_doctor.util;


import android.media.MediaPlayer;

import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.R;

public class Voice_Utils {

    private MediaPlayer mediaPlayer;


    public Voice_Utils() {
        this.mediaPlayer = MediaPlayer.create(App.getInstance(), R.raw.ling);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.setLooping(true);
                mp.start();
            }
        });
    }

    private static class VoiceUtilsHoler {
        private static Voice_Utils INSTANCE = new Voice_Utils();
    }


    public static Voice_Utils getInstance() {
        return VoiceUtilsHoler.INSTANCE;
    }


    /**
     * @作者 Arvin
     * @时间 2019/11/8 20:58
     * @一句话描述 开始播放
     */
    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    /**
     * @作者 Arvin
     * @时间 2019/11/8 20:59
     * @一句话描述 停止播放
     */

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * @作者 Arvin
     * @时间 2019/11/8 21:00
     * @一句话描述 暂停
     */
    public void onPause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }

    /**
     * @作者 Arvin
     * @时间 2019/11/8 21:02
     * @一句话描述 是否在播放
     */
    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;

    }

}
