package com.zzj.appmarket.utils;


import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by bjh on 16/9/2.
 */
public class RxBus {
    private final Subject<Object, Object>
            rxBus = new SerializedSubject<>(PublishSubject.create());
    private RxBus(){}

    public static RxBus getInstance(){
        return RxbusHolder.instance;
    }

    public static class RxbusHolder{
        private static final RxBus instance = new RxBus();
    }
    public void send(Object o){
        rxBus.onNext(o);
    }
    public Observable<Object> toObserverable(){
        return rxBus;
    }
}
