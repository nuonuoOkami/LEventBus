package xzzb.com.myeventbus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TestService extends Service {
    private static final String TAG = "TestService";

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }
    /**
     *  @describe 符合要求的方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false, priority = 100)
    public void okEvent(MessageEvent messageEvent) {

        Log.e(TAG, "TestService符合要求的方法收到消息是 "+messageEvent.getContent());

    }
    /**
     *  @describe 不符合要求的方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false, priority = 100)
    public void ErrorEvent(ErrorMessageEvent messageEvent) {

        Log.e(TAG, "TestServiceErrorEvent收到消息是 "+messageEvent.getContent());

    }

    public TestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
