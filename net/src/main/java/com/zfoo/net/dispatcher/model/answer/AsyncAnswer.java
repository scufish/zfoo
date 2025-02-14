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

package com.zfoo.net.dispatcher.model.answer;

import com.zfoo.net.packet.model.SignalPacketAttachment;
import com.zfoo.protocol.IPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class AsyncAnswer<T extends IPacket> implements IAsyncAnswer<T> {

    private T futurePacket;
    private SignalPacketAttachment futureAttachment;

    private List<Consumer<T>> consumerList = new ArrayList<>(2);

    private Runnable askCallback;

    @Override
    public IAsyncAnswer<T> thenAccept(Consumer<T> consumer) {
        consumerList.add(consumer);
        return this;
    }

    @Override
    public void whenComplete(Consumer<T> consumer) {
        thenAccept(consumer);
        askCallback.run();
    }

    public void consume() {
        consumerList.forEach(it -> it.accept(futurePacket));
    }

    public T getFuturePacket() {
        return futurePacket;
    }

    public void setFuturePacket(T futurePacket) {
        this.futurePacket = futurePacket;
    }

    public SignalPacketAttachment getFutureAttachment() {
        return futureAttachment;
    }

    public void setFutureAttachment(SignalPacketAttachment futureAttachment) {
        this.futureAttachment = futureAttachment;
    }

    public Runnable getAskCallback() {
        return askCallback;
    }

    public void setAskCallback(Runnable askCallback) {
        this.askCallback = askCallback;
    }
}
