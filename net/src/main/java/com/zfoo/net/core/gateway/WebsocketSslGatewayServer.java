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
import com.zfoo.net.handler.codec.websocket.WebSocketCodecHandler;
import com.zfoo.net.handler.idle.ServerIdleHandler;
import com.zfoo.net.session.model.Session;
import com.zfoo.protocol.IPacket;
import com.zfoo.protocol.exception.ExceptionUtils;
import com.zfoo.util.net.HostAndPort;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.InputStream;
import java.util.function.BiFunction;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class WebsocketSslGatewayServer extends AbstractServer {

    private static final Logger logger = LoggerFactory.getLogger(WebsocketSslGatewayServer.class);

    private SslContext sslContext;

    private BiFunction<Session, IPacket, Boolean> packetFilter;

    public WebsocketSslGatewayServer(HostAndPort host, InputStream pem, InputStream key, BiFunction<Session, IPacket, Boolean> packetFilter) {
        super(host);
        try {
            this.sslContext = SslContextBuilder.forServer(pem, key).build();
        } catch (SSLException e) {
            logger.error(ExceptionUtils.getMessage(e));
        }
        this.packetFilter = packetFilter;
    }

    @Override
    public ChannelInitializer<SocketChannel> channelChannelInitializer() {
        return new GatewayChannelHandler(sslContext, packetFilter);
    }


    private static class GatewayChannelHandler extends ChannelInitializer<SocketChannel> {

        private SslContext sslContext;
        private BiFunction<Session, IPacket, Boolean> packetFilter;

        public GatewayChannelHandler(SslContext sslContext, BiFunction<Session, IPacket, Boolean> packetFilter) {
            this.sslContext = sslContext;
            this.packetFilter = packetFilter;
        }

        @Override
        protected void initChannel(SocketChannel channel) {
            channel.pipeline().addLast(new IdleStateHandler(0, 0, 180));
            channel.pipeline().addLast(new ServerIdleHandler());

            channel.pipeline().addLast(sslContext.newHandler(channel.alloc()));
            channel.pipeline().addLast(new HttpServerCodec());
            channel.pipeline().addLast(new ChunkedWriteHandler());
            channel.pipeline().addLast(new HttpObjectAggregator(64 * 1024));
            channel.pipeline().addLast(new WebSocketServerProtocolHandler("/"));
            channel.pipeline().addLast(new WebSocketCodecHandler());
            channel.pipeline().addLast(new GatewayDispatcherHandler(packetFilter));
        }
    }
}
