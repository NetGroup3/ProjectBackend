package com.example.NetProjectBackend.models;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserListRequest {

    @Builder.Default
    String searchFirstname = "";

    @Builder.Default
    String searchLastname = "";

    @Builder.Default
    String searchEmail = "";

    @Builder.Default
    String searchRole = "";

    OffsetDateTime filterTimestamp;
    Boolean filterTimestampAfter;

    SortProps[] sortProps;

    @Builder.Default
    Integer pageNo = 1;

    @Builder.Default
    Integer perPage = 20;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SortProps {
        String column;      // first_name/last_name/email/timestamp
        Boolean asc;
    }

    public void setFilterTimestamp(String isoString) {
        final String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSxx";
        DateTimeFormatter dtfB = DateTimeFormatter.ofPattern(pattern);
        this.filterTimestamp = OffsetDateTime.parse(isoString, dtfB);
    }
}
