package com.example.renlei.myapplication.rxjava;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Time 2016/11/25.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class RXBus {
    private static volatile RXBus mRxBus;
    private Subject<Object,Object> subject= new SerializedSubject<>(PublishSubject.create());
    private RXBus(){}
    public static RXBus getInstance(){
        if (mRxBus == null){
            synchronized (RXBus.class){
                if (mRxBus == null)
                    mRxBus = new RXBus();
            }
        }
        return mRxBus;
    }
    public void send(Object o){
        subject.onNext(o);
    }
    public Subject<Object,Object> toObserverable() {

        return subject;
    }

}
