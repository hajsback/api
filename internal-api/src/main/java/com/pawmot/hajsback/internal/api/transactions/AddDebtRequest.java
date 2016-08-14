package com.pawmot.hajsback.internal.api.transactions;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddDebtRequest implements Serializable {
    private static final long serialVersionUID = -547809747622379675L;

    private String debtorEmail;

    private String debteeEmail;

    private int amount;
}
