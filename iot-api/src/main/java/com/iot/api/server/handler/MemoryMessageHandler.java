package com.iot.api.server.handler;

import com.google.common.collect.Lists;
import com.iot.api.RsocketMessageHandler;
import com.iot.common.connection.ReatinMessage;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryMessageHandler implements RsocketMessageHandler {

    private ConcurrentHashMap<String ,List<ReatinMessage>> messages = new ConcurrentHashMap();

    @Override
    public void saveRetain(boolean dup, boolean retain, int qos, String topicName, byte[] copyByteBuf) {
        List<ReatinMessage> reatinMessages=messages.computeIfAbsent(topicName,tc->Lists.newArrayList());
        reatinMessages.add(new ReatinMessage(dup,retain,qos,topicName,copyByteBuf));
    }

    @Override
    public Optional<List<ReatinMessage>> getRetain(String topicName) {
        return Optional.ofNullable(messages.get(topicName));
    }
}
