package com.falser.cloud.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单dto
 *
 * @author 10235
 * @date 2021/11/20
 */
@Data
public class ContentDTO implements Serializable {

    private Long serialVersionUID = 596167959173178262L;

    private Long id;

    private String routerName;

    private List<MenuDTO> menus;

    @Data
    public static class MenuDTO implements Serializable {

        private Long id;

        private String routerName;

        private List<ButtonDTO> buttons;

        @Data
        public static class ButtonDTO implements Serializable {

            private Long id;

            private String perms;

            private String routerName;

        }
    }

}




