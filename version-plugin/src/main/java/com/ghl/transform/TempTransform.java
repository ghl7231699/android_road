package com.ghl.transform;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.ghl.TransformCallBack;

import java.io.IOException;
import java.util.Set;

/**
 * Time：2023/3/22
 * description:
 **/
public class TempTransform extends Transform {

    // plugin 所需要的name
    private String mName;

    // transform
    private TransformCallBack mTransformAction;

    public TempTransform(String name, TransformCallBack transformInvocationAction) {
        this.mName = name;
        this.mTransformAction = transformInvocationAction;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        mTransformAction.call(transformInvocation);
    }
}
