//package github.middlewaremagic.redismagic.channel.study.netty;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.*;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//
///**
// * @program: `redisRewrite
// * @description: 学习 netty 开发
// * @author: gaoxiang
// * @email: 630268696@qq.com
// * @create: 2023-05-05 10:36
// **/
//public class TimeServerTest {
//}
//
//class TimeServer {
//
//    public void bind(int port) throws Exception {
//        // 配置服务端的NIO线程组
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try {
//            ServerBootstrap b = new ServerBootstrap();
//            b.group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 1024)
//                    .childHandler(new ChildChannelHandler());
//            // 绑定端口，同步等待成功
//            ChannelFuture f = b.bind(port).sync();
//
//            // 等待服务端监听端口关闭
//            f.channel().closeFuture().sync();
//        } finally {
//            // 优雅退出，释放线程池资源
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }
//    }
//
//    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
//        @Override
//        protected void initChannel(SocketChannel arg0) throws Exception {
//            arg0.pipeline().addLast(new TimeServerHandler());
//        }
//
//    }
//
//    /**
//     * @param args
//     * @throws Exception
//     */
//    public static void main(String[] args) throws Exception {
//        int port = 8080;
//        if (args != null && args.length > 0) {
//            try {
//                port = Integer.valueOf(args[0]);
//            } catch (NumberFormatException e) {
//                // 采用默认值
//            }
//        }
//        new TimeServer().bind(port);
//    }
//}
//
//class TimeServerHandler extends ChannelHandlerAdapter {
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg)
//            throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req, "UTF-8");
//        System.out.println("The time server receive order : " + body);
//        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(
//                System.currentTimeMillis()).toString() : "BAD ORDER";
//        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
//        ctx.write(resp);
//    }
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush();
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        ctx.close();
//    }
//}