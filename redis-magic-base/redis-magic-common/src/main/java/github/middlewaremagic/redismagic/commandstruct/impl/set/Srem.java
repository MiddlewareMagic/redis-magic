package github.middlewaremagic.redismagic.commandstruct.impl.set;

import github.middlewaremagic.redismagic.api.ICache;
import github.middlewaremagic.redismagic.commandstruct.CommandType;
import github.middlewaremagic.redismagic.commandstruct.WriteCommand;
import github.middlewaremagic.redismagic.datastruct.BytesWrapper;
import github.middlewaremagic.redismagic.datastruct.RedisData;
import github.middlewaremagic.redismagic.datastruct.impl.RedisSet;
import github.middlewaremagic.redismagic.respstruct.BulkString;
import github.middlewaremagic.redismagic.respstruct.Resp;
import github.middlewaremagic.redismagic.respstruct.RespInt;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Srem implements WriteCommand {
    private BytesWrapper key;
    private List<BytesWrapper> members;

    @Override
    public CommandType type() {
        return CommandType.srem;
    }

    @Override
    public void setContent(Resp[] array) {
        key = ((BulkString) array[1]).getContent();
        members = Stream.of(array).skip(2).map(resp -> ((BulkString) resp).getContent()).collect(Collectors.toList());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, ICache cache) {
        Object obj = cache.get(key);
        if (!(obj instanceof RedisData)) {
            log.error("ICache type error. Please check out. {}", cache.toString());
            return;
        }
        RedisData redisData = (RedisData) obj;
        RedisSet redisSet = (RedisSet) redisData;
        int srem = redisSet.srem(members);
        ctx.writeAndFlush(new RespInt(srem));
    }

    @Override
    public void handle(ICache cache) {
        Object obj = cache.get(key);
        if (!(obj instanceof RedisData)) {
            log.error("ICache type error. Please check out. {}", cache.toString());
            return;
        }
        RedisData redisData = (RedisData) obj;
        RedisSet redisSet = (RedisSet) redisData;
        redisSet.srem(members);
    }
}
