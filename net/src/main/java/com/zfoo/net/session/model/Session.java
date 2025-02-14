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

package com.zfoo.net.session.model;

import com.zfoo.net.packet.model.IPacketAttachment;
import com.zfoo.net.packet.model.SignalPacketAttachment;
import com.zfoo.protocol.util.StringUtils;
import io.netty.channel.Channel;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class Session {

    private static final AtomicLong ATOMIC_LONG = new AtomicLong(0);

    /**
     * session的id
     */
    private long sid;

    private Channel channel;

    /**
     * Session附带的属性参数
     */
    private Map<AttributeType, Object> attributes = new EnumMap<>(AttributeType.class);

    /**
     * 客户端Session控制同步或异步的附加包，key：packetId
     */
    private Map<Integer, SignalPacketAttachment> clientSignalPacketAttachmentMap = new ConcurrentHashMap<>();


    public Session(Channel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("channel不能为空");
        }
        this.sid = ATOMIC_LONG.getAndIncrement();
        this.channel = channel;
    }


    public void addClientSignalAttachment(SignalPacketAttachment packetAttachment) {
        clientSignalPacketAttachmentMap.put(packetAttachment.getPacketId(), packetAttachment);
    }

    public IPacketAttachment removeClientSignalAttachment(SignalPacketAttachment packetAttachment) {
        return clientSignalPacketAttachmentMap.remove(packetAttachment.getPacketId());
    }


    @Override
    public String toString() {
        return StringUtils.format("[sid:{}] [channel:{}] [attributes:{}]", sid, channel, attributes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return sid == session.sid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid);
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public synchronized void putAttribute(AttributeType key, Object value) {
        attributes.put(key, value);
    }

    public synchronized void removeAttribute(AttributeType key) {
        attributes.remove(key);
    }


    public Object getAttribute(AttributeType key) {
        return attributes.get(key);
    }

    public Map<Integer, IPacketAttachment> getClientSignalPacketAttachmentMap() {
        return Collections.unmodifiableMap(clientSignalPacketAttachmentMap);
    }

    public Channel getChannel() {
        return channel;
    }

    public void close() {
        channel.close();
        clientSignalPacketAttachmentMap.clear();
    }
}
