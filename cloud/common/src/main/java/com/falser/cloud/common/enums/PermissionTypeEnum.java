package com.falser.cloud.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionTypeEnum {

    CONTENTS("目录", 1),
    MENU("菜单", 2),
    BUTTON("按钮", 3),
    ;


    private final String value;
    private final Integer code;
}
