package com.example.renlei.myapplication.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by  renlei
 * DATE: 16/6/1
 * Time: 11:14
 */
public class ProducerAndConsumer {
    static class Storage {
        private int p = 0;
        List<String> mStorages = Collections.synchronizedList(new ArrayList<String>());

        private synchronized void produce() {
            while (mStorages.size() == 10) {
                try {
                    System.out.println("仓库已满不能生产");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mStorages.add(p + "");
            System.out.println("生产了" + p);
            p++;
            notifyAll();
        }

        private synchronized void comsume() {
            while (mStorages.size() == 0) {
                try {
                    System.out.println("仓库为空不能消费");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("消费了" + mStorages.get(0));
            mStorages.remove(0);
            notifyAll();
        }
    }

    static class Producer implements Runnable {
        Storage mStorage;

        public Producer(Storage mStorage) {
            this.mStorage = mStorage;
        }

        @Override
        public void run() {
            while (true)
                mStorage.produce();
        }
    }

    static class Comsume implements Runnable {
        Storage mStorage;

        public Comsume(Storage mStorage) {
            this.mStorage = mStorage;
        }

        @Override
        public void run() {
            while (true)
                mStorage.comsume();
        }
    }

    public static void main(String[] arsg) {
        Storage mStorage = new Storage();
        Comsume c = new Comsume(mStorage);
        Producer producer = new Producer(mStorage);
        new Thread(producer).start();
        new Thread(c).start();
    }


}
