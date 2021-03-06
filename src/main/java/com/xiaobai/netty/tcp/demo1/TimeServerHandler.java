package com.xiaobai.netty.tcp.demo1;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class TimeServerHandler extends ChannelHandlerAdapter{
	
	private static Logger logger = Logger.getLogger(TimeServerHandler.class.getName());
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		
		logger.info("body : " + body);
		
		String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes("UTF-8"));
		ctx.write(resp);
		logger.info("server send : " + currentTime);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
}
