package com.qsportfolio.backend.service.stock;

import lombok.Data;

@Data
public class PolygonTypeResponse {
    String request_id;
    Results results;
    String status;

    @Data
    public static class Results {
        String ticker;
        String name;
        String market;
        String locale;
        String primary_exchange;
        String type;
        boolean active;
        String currency_name;
        String cik;
        String composite_figi;
        String share_class_figi;
        long market_cap;
        String phone_number;
        Address address;
        String description;
        String sic_code;
        String sic_description;
        String ticker_root;
        String homepage_url;
        int total_employees;
        String list_date;
        Branding branding;
        long share_class_shares_outstanding;
        long weighted_shares_outstanding;
        int round_lot;
    }

    @Data
    public static class Address {
        String address1;
        String city;
        String state;
        String postal_code;
    }

    @Data
    public static class Branding {
        String logo_url;
        String icon_url;
    }
}