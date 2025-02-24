package com.heima.model.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntryType{
    USER((short)1, "用户"),
    EQUIPMENT((short)0, "终端设备");
    private final short code;
    private final String desc;
}