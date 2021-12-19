package com.example.NetProjectBackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

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

    ArrayList<SortProps> sortProps;

    @Builder.Default
    Integer pageNo = 1;

    @Builder.Default
    Integer perPage = 20;
}
