package com.rs.kafka.broker.stream.commodity;

import com.rs.kafka.broker.message.*;
import com.rs.kafka.util.*;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.*;
import org.springframework.kafka.support.serializer.*;

/**
 * created by rs 5/15/2022.
 */
//@Configuration
public class CommodityTwoStream {

    @Bean
    public KStream<String, OrderMessage> kstreamCommodityTrading(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);

        var maskedCreditCardStream = builder.stream("t-commodity-order", Consumed.with(stringSerde, orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);

//		var patternStreams = maskedCreditCardStream.mapValues(CommodityStreamUtil::mapToOrderPattern)
//				.branch(CommodityStreamUtil.isPlastic(), (k, v) -> true);

//		var plasticIndex = 0;
//		var notPlasticIndex = 1;

        // plastic
//		patternStreams[plasticIndex].to("t-commodity-pattern-two-plastic",
//				Produced.with(stringSerde, orderPatternSerde));

        // not plastic
//		patternStreams[notPlasticIndex].to("t-commodity-pattern-two-notplastic",
//				Produced.with(stringSerde, orderPatternSerde));

        maskedCreditCardStream.mapValues(CommodityStreamUtil::mapToOrderPattern).split()
                .branch(CommodityStreamUtil.isPlastic(), Branched.withConsumer(
                        ks -> ks.to("t-commodity-pattern-two-plastic", Produced.with(stringSerde, orderPatternSerde))))
                .branch((k, v) -> true, Branched.withConsumer(ks -> ks.to("t-commodity-pattern-two-notplastic",
                        Produced.with(stringSerde, orderPatternSerde))));

        var rewardStream = maskedCreditCardStream.filter(CommodityStreamUtil.isLargeQuantity())
                .filterNot(CommodityStreamUtil.isCheap()).mapValues(CommodityStreamUtil::mapToOrderReward);
        rewardStream.to("t-commodity-reward-two", Produced.with(stringSerde, orderRewardSerde));

        var storageStream = maskedCreditCardStream.selectKey(CommodityStreamUtil.generateStorageKey());
        storageStream.to("t-commodity-storage-two", Produced.with(stringSerde, orderSerde));

        return maskedCreditCardStream;
    }
}
