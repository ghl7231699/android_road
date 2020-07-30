/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ghl.lib_dirty.network;

import com.ghl.lib_dirty.net.base.BaseBean;
import com.ghl.lib_dirty.net.exception.ExceptionHandle;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

@SuppressWarnings({"unchecked", "rawtypes"})
public class RoadGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private Type type;

    RoadGsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String responseBody = value.string();
        try {
            BaseBean response = gson.fromJson(responseBody, BaseBean.class);
            if (response.isOk()) {
                return fromJson(responseBody, type);
            }
            throw new ExceptionHandle.ServerException(response.getErrorCode(), response.getErrorMsg(), response.getData() != null ? response.getData().toString() : "");
        } finally {
            value.close();
        }
    }


    public T fromJson(String in, Type type) {
        Class<?> rawType = $Gson$Types.getRawType(type);
        BaseBean baseEntity = gson.fromJson(in, BaseBean.class);
        String dataJson = gson.toJson(baseEntity.getData());
        if (BaseBean.class.isAssignableFrom(rawType)) {
            return gson.fromJson(in, type);
        } else if (String.class.isAssignableFrom(rawType)) {
            if (gson.fromJson(dataJson, type) == null) {
                return (T) "";
            }
            return (T) gson.fromJson(dataJson, type);
        } else {
            if (baseEntity.getData() != null) {
                return gson.fromJson(dataJson, type);
            }
        }
        return gson.fromJson(dataJson, type);
    }
}
