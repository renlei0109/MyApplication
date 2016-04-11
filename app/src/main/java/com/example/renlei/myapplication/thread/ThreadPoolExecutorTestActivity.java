package com.example.renlei.myapplication.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by renlei
 * DATE: 16-4-11
 * Time: 下午3:13
 * Email: lei.ren@renren-inc.com
 */
public class ThreadPoolExecutorTestActivity extends BaseActivity {
    private final String TAG = "TreadPoolExecutorTestActivity";
    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_layout);
        startBtn = (Button) findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testPoolExecute();
            }
        });

    }

    /**
     * （一）ThreadPoolExecutor对象初始化时，不创建任何执行线程，当有新任务进来时，才会创建执行线程。
     * 构造ThreadPoolExecutor对象时，需要配置该对象的核心线程池大小和最大线程池大小：
     * 1、当目前执行线程的总数小于核心线程大小时，所有新加入的任务，都在新线程中处理。
     * 2、当目前执行线程的总数大于或等于核心线程时，所有新加入的任务，都放入任务缓存队列中。
     * 3、当目前执行线程的总数大于或等于核心线程，并且缓存队列已满，同时此时线程总数小于线程池的最大大小，那么创建新线程，加入线程池中，协助处理新的任务。
     * 4、当所有线程都在执行，线程池大小已经达到上限，并且缓存队列已满时，就rejectHandler拒绝新的任务。
     */
    private void testPoolExecute() {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 5, 30, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(10), new RejectDone());
//        Executors.newCachedThreadPool();
//        Executors.newFixedThreadPool();
        for (int i = 0; i < 200; i++) {
            String taskName = "task@" + i;
            Log.d(TAG, "创建任务并提交到线程池中" + taskName + "poolsize" + poolExecutor.getPoolSize());
            poolExecutor.execute(new ThreadPoolTask(taskName));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    class RejectDone extends ThreadPoolExecutor.AbortPolicy {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
//            super.rejectedExecution(r, e);
            Log.d(TAG, ((ThreadPoolTask) r).taskName + "被阻止");
        }
    }

    class ThreadPoolTask implements Runnable {
        String taskName;

        public ThreadPoolTask(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            try {
                Log.d(TAG, "开始执行:" + taskName);
                Thread.sleep(2000);
                Log.d(TAG, "执行结束:" + taskName);
                taskName = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
