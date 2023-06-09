package github.middlewaremagic.redismagic.commandstruct.impl.connection;

import github.middlewaremagic.redismagic.api.ICache;
import github.middlewaremagic.redismagic.commandstruct.Command;
import github.middlewaremagic.redismagic.commandstruct.CommandType;
import github.middlewaremagic.redismagic.respstruct.BulkString;
import github.middlewaremagic.redismagic.respstruct.Resp;
import github.middlewaremagic.redismagic.respstruct.SimpleString;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Select implements Command {
    private Integer index;

    @Override
    public CommandType type() {
        return CommandType.select;
    }

    @Override
    public void setContent(Resp[] array) {
        index = Integer.parseInt(((BulkString) array[1]).getContent().toUtf8String());
    }

    @Override
    public void
    handle(ChannelHandlerContext ctx, ICache cache) {
        if (index > 0) {
            SimpleString ok = new SimpleString("-ERR invalid DB index");
            ctx.writeAndFlush(ok);
        } else {
            SimpleString ok = new SimpleString("OK");
            ctx.writeAndFlush(ok);
        }
    }
}