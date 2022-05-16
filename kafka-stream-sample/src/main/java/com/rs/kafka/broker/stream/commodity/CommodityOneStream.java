package com.rs.kafka.broker.stream.commodity;

import com.rs.kafka.broker.message.*;
import com.rs.kafka.util.*;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.*;
import org.springframework.kafka.support.serializer.*;

/**
 * created by rs 5/13/2022.
 */
//@Configuration
public class CommodityOneStream {

    @Bean
    public KStream<String, OrderMessage> kstreamCommodityTrading(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);

        var maskedCreditCardStream = builder.stream("t-commodity-order", Consumed.with(stringSerde, orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);

        var patternStream = maskedCreditCardStream.mapValues(CommodityStreamUtil::mapToOrderPattern);
        patternStream.to("t-commodity-pattern-one", Produced.with(stringSerde, orderPatternSerde));

        var rewardStream = maskedCreditCardStream.filter(CommodityStreamUtil.isLargeQuantity())
                .mapValues(CommodityStreamUtil::mapToOrderReward);
        rewardStream.to("t-commodity-reward-one", Produced.with(stringSerde, orderRewardSerde));

        maskedCreditCardStream.to("t-commodity-storage-one", Produced.with(stringSerde, orderSerde));

        return maskedCreditCardStream;
    }
}
