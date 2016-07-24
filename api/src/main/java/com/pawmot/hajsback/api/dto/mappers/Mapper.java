package com.pawmot.hajsback.api.dto.mappers;

public interface Mapper<T, R> {
    R map(T t);
}
