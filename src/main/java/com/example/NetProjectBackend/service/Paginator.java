package com.example.NetProjectBackend.service;

import com.example.NetProjectBackend.models.dto.PaginatedResponse;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class Paginator {
    public PaginatedResponse paginate(List list, int pageNo, int perPage) {
        PaginatedResponse res = new PaginatedResponse(pageNo, perPage);

        if (list == null || pageNo <= 0 || perPage <= 0) return res;

        int pagesTotal = (int)Math.ceil((float)list.size() / perPage);
        res.setPagesTotal(pagesTotal);

        int lastIndex = list.size() - 1;
        int startIndex = perPage * (pageNo - 1);
        int endIndex = startIndex + perPage;

        if (startIndex > lastIndex) {
            return res;
        }
        else if (endIndex > lastIndex + 1) {
            endIndex = lastIndex + 1;
        }
        List pagList = list.subList(startIndex, endIndex);
        res.setList(pagList);
        return res;
    }
}
