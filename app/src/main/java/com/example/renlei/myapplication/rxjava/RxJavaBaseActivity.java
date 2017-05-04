package com.example.renlei.myapplication.rxjava;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by  renlei
 * DATE: 16/7/11
 * Time: 19:04
 */
public class RxJavaBaseActivity extends BaseActivity implements View.OnClickListener {
    String TAG = getClass().getSimpleName();
    int count = 0;
    TextView mTV;
    Subscription sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbase_layout);
        mTV = (TextView) findViewById(R.id.rx_text);
        findViewById(R.id.start_rxexample1_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, count + "");
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
        findViewById(R.id.start_rxexample5_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example5();
            }
        });
        findViewById(R.id.start_rxexample6_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example6();
            }
        });
        findViewById(R.id.start_rxexample7_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example7();
            }
        });

        sb = RXBus.getInstance().toObserverable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o instanceof Intent) {
                    Intent intent = (Intent) o;
                    String key = intent.getStringExtra("key");
                    Log.d(getClass().getSimpleName(), key);
                    mTV.setText(key);
                }
            }
        });

    }

    private void example1() {

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.d(TAG, "observable  call:" + count);
                switch (count) {
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
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext " + "s:" + s);
            }
        };
        observable.subscribe(observer);

    }


    private void example2() {
        String[] names = {"1", "2", "3"};
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

    class Student {
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
    private void example3() {
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
                        return new Student("renlei", "22", "9090");
                    }
                });
                Observable<Student> obs_1 = Observable.from(list_1);
                Observable.zip(obs_0, obs_1, new Func2<Student, Student, Object>() {
                    @Override
                    public Object call(Student student, Student student2) {
                        List<Student> all = new ArrayList<Student>();
                        all.addAll(list_0);
                        all.addAll(list_1);
                        StringBuilder sb = new StringBuilder();
                        for (Student s : all) {
                            sb.append(s.name).append("\n");
                        }
                        Log.d(TAG, sb.toString());
                        return sb.toString();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.d(TAG, o.toString());
                        mTV.setText(o.toString());
                    }
                });
            }
        }).start();
//
    }

    //rxbus
    private void example4() {
        Intent intent = new Intent();
        intent.putExtra("key", "renlei");
        RXBus.getInstance().send(intent);
    }

    @Override
    public void onClick(View v) {

    }

    private void example5() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String authInfo = getAuthInfo();

                subscriber.onNext(authInfo);
                subscriber.onCompleted();
            }
        }).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                Log.d(TAG, "authInfo  flatMap:" + s);
                String deviceInfo = getDeviceInfo(s);
                return Observable.just(deviceInfo);
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext:" + s);
            }
        });
    }

    private String getAuthInfo() {
        Log.d(TAG, "-----getAuthInfo-------");
        return "authinfo";
    }

    private String getDeviceInfo(String s) {
        Log.d(TAG, "-----getDeviceInfo-------" + s);
        return "deviceInfo";
    }

    private void example6() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 5; i++) {
                    Log.d(TAG, "Emit Data:" + i + "");
                    subscriber.onNext("" + i);
                }
                subscriber.onCompleted();
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                //showToast(s);
                Log.d(TAG, "Consume Data:" + s);
            }
        });

        Log.d(TAG, "***********************************************");
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 5; i++) {
                    Log.d(TAG, "Emit Data:" + i + "");
                    subscriber.onNext("" + i);
                }
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Consume Data:" + s);
            }
        });
    }

    /***
     * 获取github用户的信息
     */
    private void example7(){
        try {
            Observable.just("https://api.github.com/users")
                    .flatMap(new Func1<String, Observable<String>>() {///拿url去获取信息
                        @Override
                        public Observable<String> call(String s) {

                            Observable observable =   Observable.create(new Observable.OnSubscribe<String>() {
                                @Override
                                public void call(Subscriber<? super String> subscriber) {
                                    try {
                                        HttpURLConnection connection = (HttpURLConnection)new URL("https://api.github.com/users").openConnection();
                                        connection.setRequestMethod("GET");
                                        connection.setConnectTimeout(3000);
                                        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                        connection.setDoInput(true);
                                        InputStream inputStream = connection.getInputStream();
                                        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                                        StringBuilder stringbuffer = new StringBuilder();
                                        String line;
                                        while ((line = br.readLine())!=null){
                                            stringbuffer.append(line).append("\n");
                                        }
                                        br.close();
                                        subscriber.onNext(stringbuffer.toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).subscribeOn(Schedulers.newThread());
                            return observable;
                        }
                    })
                    .flatMap(new Func1<String, Observable<JSONArray>>() {///将其转换为JsonArray
                        @Override
                        public Observable<JSONArray> call(final String s) {
                                return Observable.create(new Observable.OnSubscribe<JSONArray>() {
                                    @Override
                                    public void call(Subscriber<? super JSONArray> subscriber) {
                                        try {
                                            JSONArray array = new JSONArray(s);
                                            subscriber.onNext(array);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).subscribeOn(Schedulers.computation());
                        }
                    })
                    .flatMap(new Func1<JSONArray, Observable<String>>() {///获取username
                        @Override
                        public Observable<String> call(final JSONArray array) {
                            return Observable.create(new Observable.OnSubscribe<String>() {
                                @Override
                                public void call(Subscriber<? super String> subscriber) {
                                    try {
                                        JSONObject ob = array.getJSONObject(0);
                                        String username = ob.optString("login","renlei");
                                        subscriber.onNext(username);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String o) {
                            try {
                                mTV.setText(o.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sb.unsubscribe();

    }


}
