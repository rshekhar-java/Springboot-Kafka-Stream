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
public class MaskOrderStream {

    @Bean
    public KStream<String, OrderMessage> kstreamCommodityTrading(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var maskedCreditCardStream = builder.stream("t-commodity-order", Consumed.with(stringSerde, orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);

        maskedCreditCardStream.to("t-commodity-order-masked",Produced.with(stringSerde, orderSerde));

        maskedCreditCardStream.print(Printed.toSysOut());

        return maskedCreditCardStream;
    }
}
