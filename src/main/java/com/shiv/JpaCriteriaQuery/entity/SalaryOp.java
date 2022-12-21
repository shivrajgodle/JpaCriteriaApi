package com.shiv.JpaCriteriaQuery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryOp {
    private String count;
    private String max;
    private String min;
    private String avg;
    private String sum;

}
