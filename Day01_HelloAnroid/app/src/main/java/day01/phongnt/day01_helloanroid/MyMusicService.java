package day01.phongnt.day01_helloanroid;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyMusicService extends Service {
    MediaPlayer mp_object;
    public MyMusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        mp_object = MediaPlayer.create(getApplicationContext(), R.raw.recording);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       mp_object.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mp_object.release();
        super.onDestroy();
    }
}
