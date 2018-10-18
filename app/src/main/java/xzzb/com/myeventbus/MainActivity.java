package xzzb.com.myeventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //onCreate注册
        EventBus.getDefault().register(this);
        //按钮
        Button startPost = (Button) findViewById(R.id.btn);
        //开启Service
        startService(new Intent(MainActivity.this, TestService.class));
        //点击按钮发送消息
        startPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建事件
                MessageEvent messageEvent = new MessageEvent();
                //输入测试内容
                messageEvent.setContent("测试消息收到！！！");
                //发送事件
                EventBus.getDefault().post(messageEvent);
            }
        });

    }
/**
 *  @author Administrator
 *  @time 2018/10/11  15:26
 *  @describe 符合要求的方法
 */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false, priority = 100)
    public void okEvent(MessageEvent messageEvent) {
        Log.e(TAG, "MainActivity符合要求的方法收到消息是 "+messageEvent.getContent());
    }
    /**
     *  @author Administrator
     *  @time 2018/10/11  15:26
     *  @describe 不符合要求的方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false, priority = 100)
    public void ErrorEvent(ErrorMessageEvent messageEvent) {

        Log.e(TAG, "MainActivityErrorEvent收到消息是 "+messageEvent.getContent());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //onDestroy反注册
        EventBus.getDefault().unregister(this);
    }
}
