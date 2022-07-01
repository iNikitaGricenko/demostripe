package com.faceit.demostripe.model;

import lombok.Data;

@Data
public class ChargeResult {

    private String id;
    private String status;
    private String chargeId;
    private String balanceTransaction;

}
