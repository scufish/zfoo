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

package com.zfoo.net.core.gateway;

import com.zfoo.net.core.AbstractServer;
import com.zfoo.net.handler.GatewayDispatcherHandler;
import com.zfoo.net.handler.codec.tcp.TcpPacketCodecHandler;
import com.zfoo.net.handler.idle.ServerIdleHandler;
import com.zfoo.net.session.model.Session;
import com.zfoo.protocol.IPacket;
import com.zfoo.util.net.HostAndPort;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.util.function.BiFunction;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class GatewayServer extends AbstractServer {
    private static final Logger logger = LoggerFactory.getLogger(GatewayServer.class);

    private BiFunction<Session, IPacket, Boolean> packetFilter;

    public GatewayServer(HostAndPort host, @Nullable BiFunction<Session, IPacket, Boolean> packetFilter) {
        super(host);
        this.packetFilter = packetFilter;
    }

    @Override
    public ChannelInitializer<SocketChannel> channelChannelInitializer() {
        return new GatewayChannelHandler(packetFilter);
    }


    private static class GatewayChannelHandler extends ChannelInitializer<SocketChannel> {

        private BiFunction<Session, IPacket, Boolean> packetFilter;

        public GatewayChannelHandler(BiFunction<Session, IPacket, Boolean> packetFilter) {
            this.packetFilter = packetFilter;
        }

        @Override
        protected void initChannel(SocketChannel channel) {
            channel.pipeline().addLast(new IdleStateHandler(0, 0, 180));
            channel.pipeline().addLast(new ServerIdleHandler());
            channel.pipeline().addLast(new TcpPacketCodecHandler());
            channel.pipeline().addLast(new GatewayDispatcherHandler(packetFilter));
        }
    }
}
