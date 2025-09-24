package com.qsportfolio.backend.service.stock.response;

import lombok.Data;

import java.util.List;

@Data
public class FinnhubPriceResponse {
    float c;
    float d;
    float dp;
    float h;
    float l;
    float o;
    float pc;
    float t;
}