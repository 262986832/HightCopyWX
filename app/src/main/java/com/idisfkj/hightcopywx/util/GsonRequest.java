package com.idisfkj.hightcopywx.util;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by fvelement on 2017/9/4.
 */

public class GsonRequest<T> extends Request<T> {
    private final Response.Listener<T> mListener;
    private Gson mGson;
    private Class<T> mClass;
    private Map<String, String> mParams;//post Params
    private TypeToken<T> mTypeToken;

    public GsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> mListener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.mListener = mListener;
        this.mGson = new Gson();
        mClass = clazz;
    }

    public GsonRequest(int method, String url, TypeToken<T> typeToken, Response.Listener<T> mListener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.mListener = mListener;
        this.mGson = new Gson();
        mTypeToken = typeToken;
    }

    //get
    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener, errorListener);
    }

    public GsonRequest(String url, TypeToken<T> typeToken, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.GET, url, typeToken, listener, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String jsonString = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            if (mTypeToken == null)
                return Response.success(mGson.fromJson(jsonString, mClass), HttpHeaderParser.parseCacheHeaders(networkResponse));//用Gson解析返回Java对象
            else
                return (Response<T>) Response.success(mGson.fromJson(jsonString, mTypeToken.getType()),
                        HttpHeaderParser.parseCacheHeaders(networkResponse));//通过构造TypeToken让Gson解析成自定义的对象类型
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T t) {
        mListener.onResponse(t);//回调T对象
    }
}
