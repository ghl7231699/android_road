package com.mmc.lamandys.liba_datapick.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import android.view.View;

import com.mmc.lamandys.liba_datapick.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConstraintActivity extends Activity implements View.OnClickListener {
    private Group mGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);

        mGroup = findViewById(R.id.group);
        findViewById(R.id.button1).setOnClickListener(this);

        mGroup.setSelected(true);

        Random random=new Random();
        int nextInt = random.nextInt(3);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }

    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                if (mGroup.isSelected()) {
                    mGroup.setVisibility(View.INVISIBLE);
                    mGroup.setSelected(false);
                } else {
                    mGroup.setVisibility(View.VISIBLE);
                    mGroup.setSelected(true);
                }
                break;
        }
    }
}
