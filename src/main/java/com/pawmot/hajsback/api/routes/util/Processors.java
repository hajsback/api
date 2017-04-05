package com.pawmot.hajsback.api.routes.util;

import com.pawmot.hajsback.api.exceptions.HttpStatusException;
import com.pawmot.hajsback.api.results.Result;
import com.pawmot.hajsback.api.results.ResultKind;
import org.apache.camel.Processor;
import org.springframework.http.HttpStatus;

public class Processors {
    private Processors() {}

    public final static Processor RESULT_PROCESSOR = ex -> {
        Result result = ex.getIn().getBody(Result.class);

        if (result.getResultKind() == ResultKind.ValidationError) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST);
        }

        ex.getIn().setBody(null);
    };
}
