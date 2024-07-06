package com.malajmi.elm_wallet.transaction.models;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.malajmi.elm_wallet.transaction.enums.TransactionStatus;
import com.malajmi.elm_wallet.transaction.enums.TransactionType;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.beans.ConstructorProperties;
import java.time.Instant;

@Value
@ConfigurationProperties(prefix = "query")
public class TransactionsQueryParams {
     Integer page = 0;
     Integer size = 20;
    @JsonAlias("sort_by")
     String sortBy = "createdAt";
    @JsonAlias("sort_direction")
     String sortDirection = "DESC";
    @JsonAlias("user_id")
     Long userId;
     TransactionType type;
     TransactionStatus status;
    @JsonAlias("start_date")
     Instant startDate;
    @JsonAlias("end_date")
     Instant endDate;

    @ConstructorProperties({
            "page",
            "size",
            "sort_by",
            "sort_direction",
            "user_id",
            "type",
            "status",
            "start_date",
            "end_date"
    })
    public TransactionsQueryParams(
            @DefaultValue("0") Integer page,
            @DefaultValue("20") Integer size,
            @DefaultValue("createdAt") String sortBy,
            @DefaultValue("DESC") String sortDirection,
            Long userId,
            TransactionType type,
            TransactionStatus status,
            Instant startDate,
            Instant endDate) {
        this.userId = userId;
        this.type = type;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
