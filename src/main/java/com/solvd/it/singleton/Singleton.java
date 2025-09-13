package com.solvd.it.singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Singleton {
    private static final org.apache.logging.log4j.Logger LOGGER = (Logger) LogManager.getLogger(Singleton.class);

    private Singleton(){
        LOGGER.info(" App working successfully ");
    }

    private static class SingletonHelper{
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public void showMessage(){
        LOGGER.info(" Please follow these advices to strengthen your results ");
    }

}

