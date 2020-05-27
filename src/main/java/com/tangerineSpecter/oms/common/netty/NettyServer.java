package com.tangerinespecter.oms.common.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * netty服务
 *
 * @author TangerineSpecter
 * @version 0.4.0
 * @date 2020年05月28日00:46:28
 */
@Slf4j
@Component
public class NettyServer {

    private static class SingletonWsServer {
        static final NettyServer instance = new NettyServer();
    }

    public static NettyServer getInstance() {
        return SingletonWsServer.instance;
    }

    private EventLoopGroup mainGroup;
    private EventLoopGroup sunGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    public NettyServer() {
        mainGroup = new NioEventLoopGroup();
        sunGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, sunGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WsServerInitalizer());
    }

    public void start() {
        this.future = server.bind(8632);
        log.info("netty websocket server 启动完毕...");
    }
}
