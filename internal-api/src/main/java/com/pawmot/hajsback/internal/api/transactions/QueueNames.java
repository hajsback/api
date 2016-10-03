package com.pawmot.hajsback.internal.api.transactions;

public class QueueNames {
    private QueueNames() {
        throw new UnsupportedOperationException();
    }

    public static final String ADD_DEBT_QUEUE = "add_debt";

    public static final String REPAY_DEBT_QUEUE = "repay_debt";

    public static final String DEBT_GRAPH_QUEUE = "debt_graph";
}
