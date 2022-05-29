package com.tangerinespecter.oms.common.netty;

import cn.hutool.core.map.MapUtil;
import com.tangerinespecter.oms.common.context.UserContext;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 处理消息的handler
 * TextWebSocketFrame 在netty中，是用于为websocket专门处理文本的对象，frame是消息的载体
 */
@Slf4j
@Component
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 用于记录和管理所有客户端的channel
     */
    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * 管理员的channelID
     */
    private static Map<String, ChannelId> userChannelMap = MapUtil.newHashMap();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        //获取客户端传递过来的消息
        String content = msg.text();
        log.info("接收到的数据：" + content);
        users.writeAndFlush(new TextWebSocketFrame("[服务器在]" + LocalDateTime.now() + "接收到消息，消息为：" + content));
    }

    /**
     * 推送当前用户
     *
     * @param content 消息内容
     * @return true:推送成功；false:推送失败
     */
    public boolean sendCurrentUser(String content) {
        try {
            Channel user = users.find(userChannelMap.get(UserContext.getUid()));
            user.writeAndFlush(new TextWebSocketFrame(content));
            return true;
        } catch (Exception e) {
            log.error("消息推送异常，异常用户ID[{}]", UserContext.getUid());
        }
        return false;
    }

    /**
     * 推送所有用户
     *
     * @param content
     */
    public void sendAllUser(String content) {
        log.info("接收到的数据：" + content);
        users.writeAndFlush(new TextWebSocketFrame(content));
    }

    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的channel，并且放到group中去进行管理
     *
     * @param ctx
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        //进行channel添加
        users.add(ctx.channel());
//        userChannelMap.put(SystemUtils.getSystemUserId(), ctx.channel().id());
    }

    /**
     * @param ctx
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        //当触发handlerRemoved，ChannelGroup会 "自动" 移除对应客户端的channel
        //clients.remove(ctx.channel());
        log.info("客户端断开长ID：" + ctx.channel().id().asLongText());
        log.info("客户端断开短ID：" + ctx.channel().id().asShortText());
//        userChannelMap.remove(SystemUtils.getSystemUserId());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        //发生异常后关闭连接，随后从channelGroup移除
        ctx.channel().close();
        users.remove(ctx.channel());
//        userChannelMap.remove(SystemUtils.getSystemUserId());
    }
}
