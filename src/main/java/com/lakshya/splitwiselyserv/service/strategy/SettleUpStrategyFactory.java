package com.lakshya.splitwiselyserv.service.strategy;

public class SettleUpStrategyFactory {
    public static SettleUpStrategy getSettleUpStrategy(SettleUpStrategies settleUpStrategy) {
        // TODO: convert to switch case when more strategies are added
        if (settleUpStrategy.equals(SettleUpStrategies.HEAP_BASED_SETTLE_UP_STRATEGY))
            return new HeapBasedSettleUpStrategy();
        return null;
    }
}
