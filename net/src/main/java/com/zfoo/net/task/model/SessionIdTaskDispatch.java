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

package com.zfoo.net.task.model;

import com.zfoo.net.task.TaskManager;
import com.zfoo.util.math.HashUtils;

import java.util.concurrent.ExecutorService;

/**
 * 同一个session总是分配到同一个线程池执行
 *
 * @author jaysunxiao
 * @version 3.0
 */
public class SessionIdTaskDispatch extends AbstractTaskDispatch {

    private static final SessionIdTaskDispatch INSTANCE = new SessionIdTaskDispatch();

    public static SessionIdTaskDispatch getInstance() {
        return INSTANCE;
    }

    @Override
    public ExecutorService getExecutor(ReceiveTask receiveTask) {
        var session = receiveTask.getSession();
        return TaskManager.getInstance().getExecutorByConsistentHash(HashUtils.fnvHash(session.getSid()));
    }

}
