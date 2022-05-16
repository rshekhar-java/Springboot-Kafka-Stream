package com.rs.kafka.broker.stream.commodity;

import com.rs.kafka.broker.message.*;
import com.rs.kafka.util.*;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.*;
import org.springframework.context.annotation.*;
import org.springframework.kafka.support.*;
import org.springframework.kafka.support.serializer.*;

/**
 * created by rs 5/15/2022.
 */
//@Configuration
public class CommodityFiveStream {

    private static final Logger LOG = LoggerFactory.getLogger(CommodityFiveStream.class);

    @Bean
    public KStream<String, OrderMessage> kstreamCommodityTrading(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);

        var maskedCreditCardStream = builder.stream("t-commodity-order", Consumed.with(stringSerde, orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);

        final var branchProducer = Produced.with(stringSerde, orderPatternSerde);

        new KafkaStreamBrancher<String, OrderPatternMessage>()
                .branch(CommodityStreamUtil.isPlastic(),
                        kstream -> kstream.to("t-commodity-pattern-five-plastic", branchProducer))
                .defaultBranch(kstream -> kstream.to("t-commodity-pattern-five-notplastic", branchProducer))
                .onTopOf(maskedCreditCardStream.mapValues(CommodityStreamUtil::mapToOrderPattern));

        var rewardStream = maskedCreditCardStream.filter(CommodityStreamUtil.isLargeQuantity())
                .filterNot(CommodityStreamUtil.isCheap()).map(CommodityStreamUtil.mapToOrderRewardChangeKey());
        rewardStream.to("t-commodity-reward-five", Produced.with(stringSerde, orderRewardSerde));

        var storageStream = maskedCreditCardStream.selectKey(CommodityStreamUtil.generateStorageKey());
        storageStream.to("t-commodity-storage-five", Produced.with(stringSerde, orderSerde));

        // 4th sink
        maskedCreditCardStream.filter((k, v) -> v.getOrderLocation().toUpperCase().startsWith("C"))
                .foreach((k, v) -> this.reportFraud(v));

        return maskedCreditCardStream;
    }

    private void reportFraud(OrderMessage v) {
        LOG.info("Reporting fraud : {}", v);
    }

}
