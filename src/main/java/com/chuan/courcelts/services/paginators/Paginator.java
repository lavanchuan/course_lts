package com.chuan.courcelts.services.paginators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
public class Paginator<T> {
    List<T> data;

    public Paginator(List<T> data){
        this.data = data;
    }

    public List<T> pageOf(int pageNum, int itemNum){
        int startIndex = (pageNum - 1) * itemNum;
        int endIndex = Math.min(startIndex + itemNum, data.size());
        int totalPage = (int)Math.ceil((double)data.size()/itemNum);
        if(pageNum > totalPage) return null;
        System.out.printf("StartIndex(%d)\nEndIndex(%d)\nTotalPage(%d)", startIndex, endIndex, totalPage);
        return data.subList(startIndex, endIndex);
    }


}
