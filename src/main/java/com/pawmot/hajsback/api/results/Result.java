package com.pawmot.hajsback.api.results;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result<T> {
    private ResultKind resultKind;

    private T data;
}
