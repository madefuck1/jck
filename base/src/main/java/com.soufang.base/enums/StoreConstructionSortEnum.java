package com.soufang.base.enums;

public enum  StoreConstructionSortEnum {
    collection_number(1, "order by collection_number desc"),
    deal_number(2, "order by deal_number desc"),
    browse_number(3, "order by browse_number desc"),
    product_priceD(4, "order by product_price desc"),
    product_price(5, "order by product_price");

    private Integer value;
    private String message;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    StoreConstructionSortEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }


    public static String getByKey(Integer value){
        for (StoreConstructionSortEnum storeConstructionSortEnum : StoreConstructionSortEnum.values()) {
            if(storeConstructionSortEnum.value == value){
                return storeConstructionSortEnum.message;
            }
        }
        return  null ;
    }
}
