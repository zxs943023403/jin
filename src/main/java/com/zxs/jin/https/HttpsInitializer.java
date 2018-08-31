package com.zxs.jin.https;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLContext;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;

public class HttpsInitializer extends ChannelInitializer<SocketChannel> {
	
	private HttpsEngine https;
	
	public HttpsInitializer(HttpsEngine https) {
		this.https = https;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		SSLEngine sslEngine = SSLContextFactory.getSslContext().createSSLEngine();
        sslEngine.setUseClientMode(false);
        ch.pipeline().addLast(new SslHandler(sslEngine));
        ch.pipeline().addLast("http-decoder", new HttpServerCodec());
        ch.pipeline().addLast(new HttpObjectAggregator(2048));
        ch.pipeline().addLast(new HttpsSeverHandler(https));
	}

}
