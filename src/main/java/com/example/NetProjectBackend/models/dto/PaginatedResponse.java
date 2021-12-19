package com.example.NetProjectBackend.models.dto;

import lombok.Data;
import java.util.List;

@Data
public class PaginatedResponse {

    List list;
    int pageNo;
    int perPage;
    int pagesTotal;

    public PaginatedResponse(int pageNo, int perPage) {
        this.pageNo = pageNo;
        this.perPage = perPage;
        this.pagesTotal = 0;
        this.list = null;
    }
}
