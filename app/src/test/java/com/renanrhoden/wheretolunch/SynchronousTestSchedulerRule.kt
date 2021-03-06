package com.renanrhoden.wheretolunch

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Executor

/**
 * Mocks RX schedulers to make test synchronous
 * @see <a href="https://medium.com/@fabioCollini/testing-asynchronous-rxjava-code-using-mockito-8ad831a16877">Medium Post</a>
 *
 */
class SynchronousTestSchedulerRule : TestRule {

//    override fun apply(base: Statement, d: Description): Statement {
//        return object : Statement() {
//            @Throws(Throwable::class)
//            override fun evaluate() {
//                RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
//                RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
//                RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
//                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
//
//                try {
//                    base.evaluate()
//                } finally {
//                    RxJavaPlugins.reset()
//                    RxAndroidPlugins.reset()
//                }
//            }
//        }
//    }

    private val immediate = object : Scheduler() {
        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
        }
    }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setInitIoSchedulerHandler { immediate }
                RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
                RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
                RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}
