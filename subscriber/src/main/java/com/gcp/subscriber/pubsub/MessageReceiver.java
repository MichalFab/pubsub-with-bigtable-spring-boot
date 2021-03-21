package com.gcp.subscriber.pubsub;

import com.gcp.subscriber.bigtable.repo.BigTableRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiver {

    private static final Log LOGGER = LogFactory.getLog(MessageReceiver.class);


    private final BigTableRepository bigTableRepository;

    @Autowired
    public MessageReceiver(BigTableRepository bigTableRepository) {
        this.bigTableRepository = bigTableRepository;
    }

    @Bean
    public MessageChannel pubsubInputChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("pubsubInputChannel") MessageChannel inputChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "messages-sub");
        adapter.setOutputChannel(inputChannel);

        return adapter;
    }

    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public void messageReceiver(String payload) {
        LOGGER.info("Message arrived! Payload: " + payload);
        bigTableRepository.insert(payload);
    }
}