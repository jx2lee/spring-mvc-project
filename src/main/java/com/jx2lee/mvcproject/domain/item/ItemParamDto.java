package com.jx2lee.mvcproject.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemParamDto {
    private String updatedName;
    private Integer updatedPrice;
    private Integer updatedQuantity;

    public ItemParamDto() {
    }

    public ItemParamDto(String updatedName, Integer updatedPrice, Integer updatedQuantity) {
        this.updatedName = updatedName;
        this.updatedPrice = updatedPrice;
        this.updatedQuantity = updatedQuantity;
    }
}
