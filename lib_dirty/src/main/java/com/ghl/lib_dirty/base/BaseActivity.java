package com.ghl.lib_dirty.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ghl.lib_dirty.widgets.gray_layer.GrayBaseFrameLayout;

/**
 * 更换app主题色（灰度）
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        try {
            if ("FrameLayout".equals(name)) {
                int count = attrs.getAttributeCount();
                for (int i = 0; i < count; i++) {
                    String attributeName = attrs.getAttributeName(i);
                    String attributeValue = attrs.getAttributeValue(i);
                    if ("id".equals(attributeName)) {
                        int id = Integer.parseInt(attributeValue.substring(1));
                        String resourceName = getResources().getResourceName(id);
                        if ("android:id/content".equals(resourceName)) {
                            return new GrayBaseFrameLayout(context, attrs);
                        }
                    }
                    System.out.println("布局id和名称为\t" + attributeName);
                }
            }
        } catch (NumberFormatException | Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
