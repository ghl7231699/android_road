package com.ghl.biz_login.service;

import android.content.Context;

import androidx.annotation.Nullable;

import com.ghl.common.service.LoginModuleService;
import com.ghl.imc.AbsModuleService;

import java.util.Map;

/**
 * Time：2023/3/22
 * description:
 **/
public class LoginModuleImp extends AbsModuleService implements LoginModuleService {
    @Override
    public boolean openPageByUrl(@Nullable Context context, @Nullable String url, @Nullable Map<?, ?> params) {
        return false;
    }
}
