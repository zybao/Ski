package com.oab.skyi.common.network;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bao on 2017/11/24.
 */

public class RetrofitException extends RuntimeException {
    private final String url;
    private final Response response;
    private final RetrofitException.Kind kind;
    private final Retrofit retrofit;

    public static RetrofitException httpError(String url, Response response, Retrofit retrofit) {
        String message = response.code() + ": " + response.message();
        return new RetrofitException(message, url, response, RetrofitException.Kind.HTTP,
                (Throwable) null, retrofit);
    }

    public static RetrofitException networkError(IOException exception) {
        return new RetrofitException(exception.getMessage(), (String) null, (Response) null,
                RetrofitException.Kind.NETWORK, exception, (Retrofit) null);
    }

    public static RetrofitException unexpectedError(Throwable exception) {
        return new RetrofitException(exception.getMessage(), (String) null, (Response) null,
                RetrofitException.Kind.UNEXPECTED, exception, (Retrofit) null);
    }

    public static RetrofitException from(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            Response response = httpException.response();
            return httpError(response.raw().request().url().toString(), response, (Retrofit) null);
        } else {
            return throwable instanceof IOException
                    ? networkError((IOException) throwable)
                    : unexpectedError(throwable);
        }
    }

    RetrofitException(String message, String url, Response response, RetrofitException.Kind kind,
                      Throwable exception, Retrofit retrofit) {
        super(message, exception);
        this.url = url;
        this.response = response;
        this.kind = kind;
        this.retrofit = retrofit;
    }

    public String getUrl() {
        return this.url;
    }

    public Response getResponse() {
        return this.response;
    }

    public RetrofitException.Kind getKind() {
        return this.kind;
    }

    public Retrofit getRetrofit() {
        return this.retrofit;
    }

    public <T> T getErrorBodyAs(Class<T> type) throws IOException {
        if (this.response != null && this.response.errorBody() != null) {
            Converter<ResponseBody, ?> converter = GsonConverterFactory.create()
                    .responseBodyConverter(type, new Annotation[0], this.retrofit);
            return (T) converter.convert(this.response.errorBody());
        } else {
            return null;
        }
    }

    public enum Kind {
        NETWORK,
        HTTP,
        UNEXPECTED;

        Kind() {
        }
    }
}
