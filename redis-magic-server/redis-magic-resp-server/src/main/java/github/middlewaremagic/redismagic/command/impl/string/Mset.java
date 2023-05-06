//package github.middlewaremagic.redismagic.command.impl.string;
//
//import github.middlewaremagic.redismagic.command.CommandType;
//import github.middlewaremagic.redismagic.command.WriteCommand;
//import github.middlewaremagic.redismagic.core.RedisCore;
//import github.middlewaremagic.redismagic.datastruct.BytesWrapper;
//import github.middlewaremagic.redismagic.datastruct.impl.RedisString;
//import io.netty.channel.ChannelHandlerContext;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class Mset implements WriteCommand {
//    private List<BytesWrapper> kvList;
//
//    @Override
//    public CommandType type() {
//        return CommandType.mset;
//    }
//
//    @Override
//    public void setContent(Resp[] array) {
//        kvList = Stream.of(array).skip(1).map(resp -> ((BulkString) resp).getContent()).collect(Collectors.toList());
//    }
//
//    @Override
//    public void handle(ChannelHandlerContext ctx, RedisCore redisCore) {
//        for (int i = 0; i < kvList.size(); i += 2) {
//
//            redisCore.put(kvList.get(i), new RedisString(kvList.get(i + 1)));
//        }
//        ctx.writeAndFlush(SimpleString.OK);
//    }
//
//    @Override
//    public void handle(RedisCore redisCore) {
//        for (int i = 0; i < kvList.size(); i += 2) {
//            redisCore.put(kvList.get(i), new RedisString(kvList.get(i + 1)));
//        }
//    }
//}