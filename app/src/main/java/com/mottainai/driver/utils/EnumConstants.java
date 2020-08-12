/*
 *  Copyright (c) 2020. Knitter Social networking private limited. All rights reserved.
 *  Created by Naveen Kumar BV
 */

package com.mottainai.driver.utils;

public final class EnumConstants {

    private EnumConstants() {}

    public enum ParentComponentType{
        NON,
        ACTIVITY,
        FRAGMENT
    }

    public enum TransactionType {
        NON,
        SELECT_COUNTRY,
        SELECT_STATE,
        SELECT_CITY,
        SELECT_VEHICLE_TYPE,
        SELECT_CASE_CATEGORY,
        SELECT_MONTH
    }
}
