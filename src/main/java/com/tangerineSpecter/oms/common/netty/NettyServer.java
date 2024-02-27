package com.tangerinespecter.oms.common.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    private static int NETTY_PORT;

    @Value("${netty.server.port}")
    public void setNettyPort(int nettyPort) {
        NETTY_PORT = nettyPort;
    }

    private static class SingletonWsServer {
        static final NettyServer INSTANCE = new NettyServer();
    }

    public static NettyServer getInstance() {
        return SingletonWsServer.INSTANCE;
    }

    private EventLoopGroup mainGroup;
    private EventLoopGroup sunGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    public NettyServer() {
        mainGroup = new NioEventLoopGroup();
        sunGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, sunGroup).channel(NioServerSocketChannel.class).childHandler(new WsServerInitalizer());
    }

    public void start() {
        this.future = server.bind(NETTY_PORT);
        log.info("[netty websocket server 启动完毕，端口号：{}]", NETTY_PORT);
    }
}
