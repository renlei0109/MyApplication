package com.example.renlei.myapplication.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by  renlei
 * DATE: 16/7/11
 * Time: 19:04
 */
public class RxJavaBaseActivity extends BaseActivity implements View.OnClickListener{
    String TAG = getClass().getSimpleName();
    int count =0;
    TextView mTV;
    Subscription sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbase_layout);
        mTV = (TextView)findViewById(R.id.rx_text);
        findViewById(R.id.start_rxexample1_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,count+"");
                    example1();
                    count++;
            }
        });
        findViewById(R.id.start_rxexample2_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example2();
            }
        });
        findViewById(R.id.start_rxexample3_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example3();
            }
        });
        findViewById(R.id.start_rxexample4_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example4();
            }
        });

       sb =  RXBus.getInstance().toObserverable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o instanceof  Intent){
                    Intent intent = (Intent)o;
                    String key = intent.getStringExtra("key");
                    Log.d(getClass().getSimpleName(),key);
                    mTV.setText(key);
                }
            }
        });

    }

    private void example1(){

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.d(TAG,"observable  call:"+count);
                switch (count){
                    case 1:
                        subscriber.onNext("Hello");
                        break;
                    case 2:
                        subscriber.onNext("Hi");
                        break;
                    case 3:
                        subscriber.onNext("Aloha");
                        break;
                    case 4:
                        subscriber.onCompleted();
                        break;
                }
            }
        });
        Observer<String>observer= new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,"onNext "+"s:"+s);
            }
        };
        observable.subscribe(observer);

    }


    private void example2(){
        String[] names = {"1","2","3"};
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String name) {

                        Log.d(TAG, name);
                        mTV.setText(name);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    class Student{
        String name;
        String age;
        String mClass;

        public Student(String name, String age, String mClass) {
            this.name = name;
            this.age = age;
            this.mClass = mClass;
        }
    }
    //zip
    private void example3(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Student> list_0 = new ArrayList<>();
                final List<Student> list_1 = new ArrayList<>();
                list_0.add(new Student("Merge-A11", "20", "1101"));
                list_0.add(new Student("Merge-A12", "23", "1102"));
                list_0.add(new Student("Merge-A13", "22", "1103"));
                list_0.add(new Student("Merge-A14", "21", "1104"));
                list_0.add(new Student("Merge-A15", "20", "1105"));

                list_1.add(new Student("Merge-B11", "20", "1101"));
                list_1.add(new Student("Merge-B12", "23", "1102"));
                list_1.add(new Student("Merge-B13", "22", "1103"));
                List<Student> err = null;
                Observable<Student> obs_0 = Observable.from(list_0).onErrorReturn(new Func1<Throwable, Student>() {
                    @Override
                    public Student call(Throwable throwable) {
                        return new Student("renlei","22","9090");
                    }
                });
                Observable<Student> obs_1 = Observable.from(list_1);
                Observable.zip(obs_0, obs_1, new Func2<Student, Student, Object>() {
                    @Override
                    public Object call(Student student, Student student2) {
                        List<Student>all = new ArrayList<Student>();
                        all.addAll(list_0);
                        all.addAll(list_1);
                        StringBuilder sb = new StringBuilder();
                        for (Student s: all){
                            sb.append(s.name).append("\n");
                        }
                        Log.d(TAG,sb.toString());
                        return sb.toString();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.d(TAG,o.toString());
                        mTV.setText(o.toString());
                    }
                });
            }
        }).start();
//
    }

    //rxbus
    private void example4(){
        Intent intent = new Intent();
        intent.putExtra("key","renlei");
        RXBus.getInstance().send(intent);
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sb.unsubscribe();

    }
}
