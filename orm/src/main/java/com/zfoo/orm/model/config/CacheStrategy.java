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

package com.zfoo.orm.model.config;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class CacheStrategy {

    private String strategy;
    private int size;
    private long expireMillisecond;

    public CacheStrategy(String strategy, int cacheSize, long expireMillisecond) {
        this.strategy = strategy;
        this.size = cacheSize;
        this.expireMillisecond = expireMillisecond;
    }


    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getExpireMillisecond() {
        return expireMillisecond;
    }

    public void setExpireMillisecond(long expireMillisecond) {
        this.expireMillisecond = expireMillisecond;
    }
}
