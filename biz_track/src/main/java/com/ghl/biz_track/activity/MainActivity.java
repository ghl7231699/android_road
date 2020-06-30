package com.ghl.biz_track.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ghl.biz_track.R;
import com.ghl.lib_dirty.base.WebViewActivity;
import com.ghl.lib_dirty.constants.main.HomeConstantsKt;
import com.ghl.lib_dirty.util.GrayUtilsKt;
import com.ghl.lib_dirty.util.StatusBarUtils;
import com.ghl.router.lib.Router;
import com.ghl.router_annotation.Route;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.ghl.lib_dirty.constants.flutter.FlutterConstantsKt.ACTIVITY_FLUTTER_FRAGMENT;

@Route(HomeConstantsKt.ACTIVITY_MAIN)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button jumpButton;
    private Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GrayUtilsKt.grayPageColor(getWindow().getDecorView());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        StatusBarUtils.transparencyBar(this); //设置状态栏全透明
        StatusBarUtils.StatusBarLightMode(this); //设置白底黑字
        initView();
        EventBus.getDefault().register(this);
//        testBtn = findViewById(R.id.videoBtn);
//        testBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//////                dialog.setTitle("我是弹窗");
//////                dialog.setMessage("点击弹出尝试一下");
//////                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//////                    @Override
//////                    public void onClick(DialogInterface dialogInterface, int i) {
//////                        dialogInterface.dismiss();
//////                    }
//////                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//////                    @Override
//////                    public void onClick(DialogInterface dialogInterface, int i) {
//////                        System.out.println("this is a test ");
//////                        dialogInterface.dismiss();
//////                    }
//////                }).show();
//
//                goScheme("qn413e80ac2897://");
//            }
//        });

        Looper looper = Looper.myLooper();

        new Thread(() -> {
            handler = new Handler(looper, msg -> {
                System.out.println("我收到了handler消息");
                return false;
            });

            handler.sendEmptyMessage(0);
        }).start();


    }

    private void initView() {
        jumpButton = findViewById(R.id.jumpButton);
        findViewById(R.id.jumpButton).setOnClickListener(this);
        findViewById(R.id.tabButton).setOnClickListener(this);
        findViewById(R.id.toolbarButton).setOnClickListener(this);
        findViewById(R.id.radioButton).setOnClickListener(this);
        findViewById(R.id.drawerButton).setOnClickListener(this);
        findViewById(R.id.moduleButton).setOnClickListener(this);
        findViewById(R.id.videoBtn).setOnClickListener(this);
        findViewById(R.id.newsBtn).setOnClickListener(this);
        findViewById(R.id.qqBtn).setOnClickListener(this);
        findViewById(R.id.ConBtn).setOnClickListener(this);
        findViewById(R.id.BehaviorBtn).setOnClickListener(this);
        findViewById(R.id.AnBtn).setOnClickListener(this);
        findViewById(R.id.AidlBtn).setOnClickListener(this);

        testBtn = findViewById(R.id.AnBtn);
    }

    Handler handler;

    /**
     * scheme协议跳转
     */
    private void goScheme(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            //没有安装对应的app
            Toast.makeText(MainActivity.this, "还未安装该APP！", Toast.LENGTH_LONG).show();
        }
    }

    private boolean click;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.jumpButton) {//                State state = new StartState();
//                StateManger manger = new StateManger(state);
//                manger.doAction();
//                startActivity(new Intent(this, SecondActivity.class));
//                startActivity(new Intent(this, ViewPager2Activity.class));
            startActivity(new Intent(this, LooperActivity.class));
        } else if (id == R.id.tabButton) {
            startActivity(new Intent(this, TabActivity.class));
        } else if (id == R.id.toolbarButton) {
            startActivity(new Intent(MainActivity.this, ToolBarActivity.class));
        } else if (id == R.id.radioButton) {
            startActivity(new Intent(this, RadioActivity.class));
        } else if (id == R.id.drawerButton) {
            startActivity(new Intent(this, DrawerActivity.class));
        } else if (id == R.id.videoBtn) {
            goScheme("qn2at72s9jb3xk://");
        } else if (id == R.id.newsBtn) {
            goScheme("qn413e80ac2897://");
        } else if (id == R.id.qqBtn) {//                goScheme("4uau8k3tskx7y0://");
            Intent intent = new Intent(this, WebViewActivity.class);
//                intent.putExtra(WebViewActivity.KEY_URL, "https://mp.weixin.qq.com/s/EioJ8ogsCxQEFm44mKFiOQ");
//                intent.putExtra(WebViewActivity.KEY_URL, "https://blog.csdn.net/qq_41999654/article/details/88962432");
            intent.putExtra(WebViewActivity.KEY_URL, "http://note.youdao.com/noteshare?id=d27b3b46156dc25d16cefd7eb44c9376");
//                https://blog.csdn.net/gxx_csdn/article/details/78971630
            startActivity(intent);
        } else if (id == R.id.ConBtn) {//                startActivity(new Intent(this, ConstraintActivity.class));
            if (click) {
                ObjectAnimator.ofFloat(testBtn, "translationX", 200f, 0f).setDuration(500).start();
                click = false;
                Log.e("ghl", testBtn.getScrollX() + "");
                return;
            }
            ObjectAnimator.ofFloat(testBtn, "translationX", 0f, 200f).setDuration(500).start();
            click = true;
            Log.e("ghl", testBtn.getScrollX() + "");
        } else if (id == R.id.AnBtn) {
            Router.with(this).target(ACTIVITY_FLUTTER_FRAGMENT).start();
        } else if (id == R.id.BehaviorBtn) {
            startActivity(new Intent(this, BehaviorActivity.class));
        } else if (id == R.id.AidlBtn) {
            Router.with(this).target("AidlActivity").start();
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
//        Process.killProcess(Process.myPid());
//        System.exit(0);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        System.out.println("onRestart 执行时");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        System.out.println("onStart 执行时");
        super.onStart();
    }

    @Override
    protected void onPause() {
        System.out.println("onPause 执行时");
        super.onPause();
    }

    @Override
    protected void onStop() {
        System.out.println("onStop 执行时");
        super.onStop();


    }

    @Subscribe
    public void onMessageEvent(Object b) {
        if (b instanceof String) {
            boolean equals = "second".equals(b.toString());
            if (equals) {
                Toast.makeText(this, "Second obtain message ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}













