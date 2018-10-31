package com.jaszczurowskip.recyclerlivedata.util.rx;


import io.reactivex.Scheduler;

/**
 * Created by jaszczurowskip on 04.09.2018
 */
public interface AppSchedulers {
    Scheduler io();

    Scheduler ui();

    Scheduler newThread();

    Scheduler computation();
}
