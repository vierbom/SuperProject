package xiaofei.library.hermeseventbustest;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xiaofei.library.hermeseventbus.HermesEventBus;

public class MyService extends Service {
    private int count=0;
    private Handler ttsHandler;
    private Runnable runnable = new Runnable() {
        public void run() {
            count++;
            HermesEventBus.getDefault().post(count);
             /*每次上传间隔时长*/
            int time = 1000;
            ttsHandler.postDelayed(runnable, time);
        }
    };
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HermesEventBus.getDefault().register(this);
        HandlerThread mHandlerThread = new HandlerThread("MyService");
        mHandlerThread.start();
        ttsHandler = new Handler(mHandlerThread.getLooper());
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void showText(Integer text) {
        /**获取从子项目发送的消息*/
        /**返回一条喷火龙给子项目*/
//        HermesEventBus.getDefault().post(new Dragon(1, "喷火龙"));
//        HermesEventBus.getDefault().post("喷火龙");
//    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 开始后台服务
        ttsHandler.removeCallbacksAndMessages(null);
        ttsHandler.postDelayed(runnable, 1000);

        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        HermesEventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showText(Integer dragon) {
        /**获取从主项目返回的Pokemon的名字*/
    }
}
