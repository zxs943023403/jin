package com.zxs.jin.https;

import com.zxs.jin.init.NetConfig;
import com.zxs.jin.util.Util;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class StartHttps {
	
	private static NetConfig config;
	
	static{
		config = NetConfig.getConfig();
	}
	
	public static void Start(HttpsEngine engine) {
		String portStr = config.getValue("jin.port");
		if (!Util.isNumber(portStr)) {
			throw new RuntimeException("端口号必须为整数!!");
		}
		final int port = Integer.valueOf(portStr);
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap server = new ServerBootstrap();
		try {
			server.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024)
					.group(boss, worker)
					.childHandler(new HttpsInitializer(engine));
			ChannelFuture future = server.bind(port).sync();
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}
	
}
