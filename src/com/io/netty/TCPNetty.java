package com.io.netty;

import java.net.InetSocketAddress;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ThreadFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.DefaultThreadFactory;


/**
 * 
 * 服务端TCP：netty
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月26日 上午11:05:00
 */
public class TCPNetty {

	public static void main(String[] args) {
		// 服务启动器
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		// 设置线程池
		EventLoopGroup boosLoopGroup = new NioEventLoopGroup(1);
		// work线程池：说明Netty的线程组是怎么工作的
		ThreadFactory threadFactory = new DefaultThreadFactory("work thread pool");
		// CPU个数
		int processprsNumber = Runtime.getRuntime().availableProcessors();
		EventLoopGroup workLoopGroup = new NioEventLoopGroup(processprsNumber * 2, threadFactory, SelectorProvider.provider());
		// 指定Netty的Boos线程和work线程
		serverBootstrap.group(boosLoopGroup, workLoopGroup);
		// Boos线程和work线程共享一个线程池  serverBootstrap.group(workLoopGroup);
		
		// 设置服务的通道类型
		// 实现了ServerChannel接口的 "服务器" 通道类
		serverBootstrap.channel(NioServerSocketChannel.class);
		// 也可以这样创建
		//		serverBootstrap.channelFactory(new ChannelFactory<NioServerSocketChannel>() {
		//			public NioServerSocketChannel newChannel() {
		//				return new NioServerSocketChannel(SelectorProvider.provider());
		//			}
		//		});
		
		// 设置处理器
		serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
			protected void initChannel(NioSocketChannel channel) throws Exception {
				channel.pipeline().addLast(new ByteArrayEncoder());
				channel.pipeline().addLast(new TCPServerHandler());
				channel.pipeline().addLast(new ByteArrayDecoder());
			}
		});
		
		// 设置netty服务器绑定的ip和端口
		serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
		serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		serverBootstrap.bind(new InetSocketAddress("0.0.0.0", 83));
		// 可以监控多个端口
		serverBootstrap.bind(new InetSocketAddress("0.0.0.0", 84));
	}
	
}

class TCPServerHandler extends ChannelInboundHandlerAdapter {
	
	/**
	 * 每一个channel，都有一个独立的handle，CHannelHandlerContext，ChannelPipeline，Attribute
	 * 不需要担心多个channel中的这些对象相互影响
	 * 这里使用content这个key，记录这个handler中已经接收到的客户端信息
	 */
	private static AttributeKey<StringBuffer> CONTENT = AttributeKey.valueOf("content");
	
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    	System.out.println();
        System.out.println("super.channelRegistered(ctx)");
    }

    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("super.channelUnregistered(ctx)");
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("super.channelActive(ctx) = " + ctx.toString());
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("super.channelInactive(ctx)");
    }

    @SuppressWarnings("deprecation")
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead(ChannelHandlerContext ctx, Object msg)");
        // 使用IDE工具模拟长连接中的数据缓慢提交。
        // 由read方法负责接收数据，但只是进行数据累加，不进行任何处理
        ByteBuf byteBuf = (ByteBuf)msg;
        try {
            StringBuffer contextBuffer = new StringBuffer();
            while(byteBuf.isReadable()) {
                contextBuffer.append((char)byteBuf.readByte());
            }

            //加入临时区域
            StringBuffer content = ctx.attr(TCPServerHandler.CONTENT).get();
            if(content == null) {
                content = new StringBuffer();
                ctx.attr(TCPServerHandler.CONTENT).set(content);
            }
            content.append(contextBuffer);
        } catch(Exception e) {
            throw e;
        } finally {
            byteBuf.release();
        }
    } 

    @SuppressWarnings("deprecation")
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("super.channelReadComplete(ChannelHandlerContext ctx)");
        // 由readComplete方法负责检查数据是否接收完了。
        // 和之前的文章一样，我们检查整个内容中是否有“over”关键字
        StringBuffer content = ctx.attr(TCPServerHandler.CONTENT).get();
        //如果条件成立说明还没有接收到完整客户端信息
        if(content.indexOf("over") == -1) {
            return;
        }

        //当接收到信息后，首先要做的的是清空原来的历史信息
        ctx.attr(TCPServerHandler.CONTENT).set(new StringBuffer());

        //准备向客户端发送响应
        ByteBuf byteBuf = ctx.alloc().buffer(1024);
        byteBuf.writeBytes("回发响应信息！".getBytes());
        ctx.writeAndFlush(byteBuf);

        // 关闭，正常终止这个通道上下文，就可以关闭通道了
        // 如果不关闭，这个通道的回话将一直存在，只要网络是稳定的，服务器就可以随时通过这个回话向客户端发送信息
        // 关闭通道意味着TCP将正常断开，其中所有的
        // handler、ChannelHandlerContext、ChannelPipeline、Attribute等信息都将注销
        ctx.close();
    } 

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("super.userEventTriggered(ctx, evt)");
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("super.channelWritabilityChanged(ctx)");
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("super.exceptionCaught(ctx, cause)");
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("super.handlerAdded(ctx)");
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("super.handlerRemoved(ctx)");
    }

}
