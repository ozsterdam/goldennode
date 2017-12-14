package com.goldennode.api.core;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

import com.goldennode.testutils.GoldenNodeJunitRunner;
import com.goldennode.testutils.RepeatTest;

public class LockServiceImplTest extends GoldenNodeJunitRunner {
    @Test
    @RepeatTest(times = 1)
    public void testLockUsageEndToEnd1() {
        LockService service = new LockServiceImpl();
        service.createLock("lock1", 60000);
        service.lock("lock1", Thread.currentThread().getName());
        service.unlock("lock1", Thread.currentThread().getName());
        service.deleteLock("lock1");
    }

    @Test(expected = LockException.class)
    @RepeatTest(times = 1)
    public void testLockUsageEndToEnd2() {
        LockService service = new LockServiceImpl();
        service.createLock("lock1", 60000);
        service.lock("lock2", Thread.currentThread().getName());
        service.unlock("lock1", Thread.currentThread().getName());
        service.deleteLock("lock1");
    }

    @Test(expected = LockException.class)
    @RepeatTest(times = 1)
    public void testLockUsageEndToEnd3() {
        LockService service = new LockServiceImpl();
        service.lock("lock1", Thread.currentThread().getName());
        service.unlock("lock1", Thread.currentThread().getName());
        service.deleteLock("lock1");
    }

    @Test(expected = LockException.class)
    @RepeatTest(times = 1)
    public void testLockUsageEndToEnd4() {
        LockService service = new LockServiceImpl();
        service.unlock("lock1", Thread.currentThread().getName());
        service.deleteLock("lock1");
    }

    @Test(expected = LockException.class)
    @RepeatTest(times = 1)
    public void testLockUsageEndToEnd5() {
        LockService service = new LockServiceImpl();
        service.deleteLock("lock1");
    }

    @Test(expected = IllegalMonitorStateException.class)
    @RepeatTest(times = 1)
    public void testLockUsageEndToEnd6() {
        LockService service = new LockServiceImpl();
        service.createLock("lock1", 60000);
        service.lock("lock1", Thread.currentThread().getName());
        service.unlock("lock1", "dummyId");
        service.deleteLock("lock1");
    }

    @Test
    @RepeatTest(times = 1)
    public void testLockUsageEndToEnd8() throws InterruptedException {
        final Lock lock = new ReentrantLock();
        final Condition c = lock.newCondition();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                lock.lock();
                c.signal();
                lock.unlock();
            }
        };
        new Timer().schedule(task, 3000);
        lock.lock();
        c.await();
        lock.unlock();
    }
}
