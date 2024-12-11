package com.nutech.hometest.supports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BasicResponse<T> {
    private int status;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> datas;

    public BasicResponse(T data, String message){
        this.status = 0;
        this.message = message;
        this.data = data;
    }

    public BasicResponse(List<T> datas, String message){
        this.status = 0;
        this.message = message;
        this.datas = datas;
    }

    public BasicResponse(int status, String message){
        this.status = status;
        this.message = message;
    }

}
