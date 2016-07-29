package com.example.renlei.myapplication.handler;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.MainThread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by  renlei
 * DATE: 16/7/29
 * Time: 15:33
 */
public abstract class MyAsyncTask<Params, Progress, Result> {
    /**
     * 含有的成员变量
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();//
    private static final int CORE_COUNT = CPU_COUNT + 1;
    private static final int MAX_COUNT = CPU_COUNT * 2 + 1;
    private static final int POST_EXECUTE = 0x00001;
    private static final int PUBLISH_PROGRSS = 0x00002;
    private final WorkRunnable<Params, Result> mWork;
    private final FutureTask<Result> mFutureTask;
    private static final int KEEP_ALIVE = 1;
    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);


    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };
    private static final Executor mExecutor = new ThreadPoolExecutor(CORE_COUNT, MAX_COUNT, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

    private static InnerUIHandler mUIHandler;

    private static Handler getHandler() {
        synchronized (AsyncTask.class) {
            if (mUIHandler == null) {
                mUIHandler = new InnerUIHandler();
            }
            return mUIHandler;
        }
    }

    /**
     * 构造函数
     */
    public MyAsyncTask() {

        /**
         * 构造函数中初始化
         */
        mWork = new WorkRunnable<Params, Result>() {
            @Override
            public Result call() throws Exception {
                Result result = doInBackground(paramses);
                return result;
            }
        };
        mFutureTask = new FutureTask<Result>(mWork) {
            @Override
            protected void done() {/// 线程执行完成之后会执行该函数
                super.done();
                try {
                    postResult(get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 用来获取futuretask执行完成的结果
     *
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public final Result get() throws InterruptedException, ExecutionException {
        return mFutureTask.get();
    }


    private void postResult(Result result) {
        getHandler().obtainMessage(POST_EXECUTE, new AsyncTaskResult<Result>(this, result)).sendToTarget();
    }

    /**
     * 可以回调的一些函数
     */
    @MainThread
    protected void onPreExecute() {
    }

    protected abstract Result doInBackground(Params... params);

    @MainThread
    protected void onPostExecute(Result result) {
    }

    @MainThread
    protected void onCancelled() {
    }

    @MainThread
    protected void onProgressUpdate(Progress... progress) {
    }

    private static class InnerUIHandler extends Handler {

        public InnerUIHandler() {
            super(Looper.getMainLooper());//给handle一个Looper
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case POST_EXECUTE:
                    AsyncTaskResult result = (AsyncTaskResult) msg.obj;
                    result.myAsyncTask.postResult(result.mData);
                    break;
                case PUBLISH_PROGRSS:
                    AsyncTaskResult result2 = (AsyncTaskResult) msg.obj;
                    result2.myAsyncTask.onProgressUpdate(result2.mData);
                    break;
            }

        }
    }

    public final void publishProgress(Progress... progress) {
        getHandler().obtainMessage(PUBLISH_PROGRSS,
                new AsyncTaskResult<Progress>(this, progress)).sendToTarget();
    }

    /**
     * Indicates the current status of the task. Each status will be set only once
     * during the lifetime of a task.
     */
    public enum Status {
        /**
         * Indicates that the task has not been executed yet.
         */
        PENDING,
        /**
         * Indicates that the task is running.
         */
        RUNNING,
        /**
         * Indicates that {@link AsyncTask#onPostExecute} has finished.
         */
        FINISHED,
    }

    private volatile Status mStatus = Status.PENDING;

    public final MyAsyncTask<Params, Progress, Result> execute(Params... params) {
        return executeOnExecutor(mExecutor, params);
    }

    public final MyAsyncTask<Params, Progress, Result> executeOnExecutor(Executor executor, Params... paramses) {
        if (mStatus != Status.PENDING) {
            switch (mStatus) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task:"
                            + " the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task:"
                            + " the task has already been executed "
                            + "(a task can be executed only once)");
            }
        }

        mStatus = Status.RUNNING;
        onPreExecute();
        mWork.paramses = paramses;
        executor.execute(mFutureTask);
        return this;
    }

    private static abstract class WorkRunnable<Params, Result> implements Callable<Result> {
        Params[] paramses;
    }


    private static class AsyncTaskResult<Data> {
        MyAsyncTask myAsyncTask;
        Data[] mData;

        public AsyncTaskResult(MyAsyncTask myAsyncTask, Data... mData) {
            this.myAsyncTask = myAsyncTask;
            this.mData = mData;
        }
    }

}
