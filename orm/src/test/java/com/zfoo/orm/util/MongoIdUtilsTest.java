/*
 * Copyright (C) 2020 The zfoo Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.zfoo.orm.util;

import com.zfoo.util.ThreadUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jaysunxiao
 * @version 3.0
 */
@Ignore
public class MongoIdUtilsTest {

    @Test
    public void startApplication0() {
        var context = new ClassPathXmlApplicationContext("application.xml");
        ThreadUtils.sleep(8000);

        var count = 0L;
        for (int i = 0; i < 10000_00; i++) {
            count = MongoIdUtils.getIncrementIdFromMongoDefault("myDocument");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                var count = 0L;
                for (int i = 0; i < 10000_00; i++) {
                    count = MongoIdUtils.getIncrementIdFromMongoDefault("myDocument");
                }
                System.out.println(count);
            }
        }).start();
        System.out.println(count);

        ThreadUtils.sleep(Long.MAX_VALUE);
    }

    @Test
    public void startApplication1() {
        var context = new ClassPathXmlApplicationContext("application.xml");
        ThreadUtils.sleep(6000);

        var count = 0L;
        for (int i = 0; i < 10000_00; i++) {
            count = MongoIdUtils.getIncrementIdFromMongoDefault("myDocument");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                var count = 0L;
                for (int i = 0; i < 10000_00; i++) {
                    count = MongoIdUtils.getIncrementIdFromMongoDefault("myDocument");
                }
                System.out.println(count);
            }
        }).start();
        System.out.println(count);

        ThreadUtils.sleep(Long.MAX_VALUE);
    }

    @Test
    public void startApplication2() {
        var context = new ClassPathXmlApplicationContext("application.xml");
        ThreadUtils.sleep(4000);

        var count = 0L;
        for (int i = 0; i < 10000_00; i++) {
            count = MongoIdUtils.getIncrementIdFromMongoDefault("myDocument");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                var count = 0L;
                for (int i = 0; i < 10000_00; i++) {
                    count = MongoIdUtils.getIncrementIdFromMongoDefault("myDocument");
                }
                System.out.println(count);
            }
        }).start();
        System.out.println(count);

        ThreadUtils.sleep(Long.MAX_VALUE);
    }

    @Test
    public void startApplication3() {
        var context = new ClassPathXmlApplicationContext("application.xml");
        ThreadUtils.sleep(2000);

        var count = 0L;
        for (int i = 0; i < 10000_00; i++) {
            count = MongoIdUtils.getIncrementIdFromMongoDefault("myDocument");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                var count = 0L;
                for (int i = 0; i < 10000_00; i++) {
                    count = MongoIdUtils.getIncrementIdFromMongoDefault("myDocument");
                }
                System.out.println(count);
            }
        }).start();
        System.out.println(count);

        ThreadUtils.sleep(Long.MAX_VALUE);
    }

}
