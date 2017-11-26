package com.oab.skyi.common.network;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.CallAdapter.Factory;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by bao on 2017/11/24.
 */

public class RxErrorCallAdapterFactory extends Factory {
    private final RxJava2CallAdapterFactory original;

    private RxErrorCallAdapterFactory() {
        this.original = RxJava2CallAdapterFactory.create();
    }

    public RxErrorCallAdapterFactory(Scheduler scheduler) {
        this.original = RxJava2CallAdapterFactory.createWithScheduler(scheduler);
    }

    public static Factory create() {
        return new RxErrorCallAdapterFactory();
    }

    public static Factory createWithScheduler(Scheduler scheduler) {
        return new RxErrorCallAdapterFactory(scheduler);
    }

    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        CallAdapter<?, ?> callAdapter = this.original.get(returnType, annotations, retrofit);
        return callAdapter == null ? null : new RxErrorCallAdapterFactory.RxCallAdapterWrapper(retrofit, callAdapter);
    }

    private static class RxCallAdapterWrapper<R> implements CallAdapter<R, Object> {
        private final Retrofit retrofit;
        private final CallAdapter<R, ?> wrapped;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<R, ?> wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        public Type responseType() {
            return this.wrapped.responseType();
        }

        public Object adapt(Call<R> call) {
            Object o = this.wrapped.adapt(call);
            if (o == null) {
                return o;
            } else if (o instanceof Observable) {
                Observable observable = (Observable) o;
                return observable.onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                    public ObservableSource apply(Throwable throwable) throws Exception {
                        return Observable.error(RxCallAdapterWrapper.this.asRetrofitException(throwable));
                    }
                });
            } else if (o instanceof Flowable) {
                Flowable flowable = (Flowable) o;
                return flowable.onErrorResumeNext(new Function<Throwable, Publisher>() {
                    public Publisher apply(Throwable throwable) throws Exception {
                        return Flowable.error(RxCallAdapterWrapper.this.asRetrofitException(throwable));
                    }
                });
            } else if (o instanceof Single) {
                Single single = (Single) o;
                return single.onErrorResumeNext(new Function<Throwable, SingleSource>() {
                    public SingleSource apply(Throwable throwable) throws Exception {
                        return Single.error(RxCallAdapterWrapper.this.asRetrofitException(throwable));
                    }
                });
            } else if (o instanceof Maybe) {
                Maybe maybe = (Maybe) o;
                return maybe.onErrorResumeNext(new Function<Throwable, MaybeSource>() {
                    public MaybeSource apply(Throwable throwable) throws Exception {
                        return Maybe.error(RxCallAdapterWrapper.this.asRetrofitException(throwable));
                    }
                });
            } else if (o instanceof Completable) {
                Completable completable = (Completable) o;
                return completable.onErrorResumeNext(new Function<Throwable, CompletableSource>() {
                    public CompletableSource apply(Throwable throwable) throws Exception {
                        return Completable.error(RxCallAdapterWrapper.this.asRetrofitException(throwable));
                    }
                });
            } else {
                return o;
            }
        }

        private RetrofitException asRetrofitException(Throwable throwable) {
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                return RetrofitException.httpError(response.raw().request().url().toString(), response, this.retrofit);
            } else {
                return throwable instanceof IOException ? RetrofitException.networkError((IOException) throwable) : RetrofitException.unexpectedError(throwable);
            }
        }
    }
}
