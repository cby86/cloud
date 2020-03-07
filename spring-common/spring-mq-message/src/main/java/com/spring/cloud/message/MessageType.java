package com.spring.cloud.message;

public enum  MessageType {
    MenuChange("router.user.menu_change"), MenuDelete("router.user.menu_delete"),AuthencationChange("router.geteway.menu_delete");

    private final String routerKey;

    MessageType(String routerKey) {
        this.routerKey = routerKey;
    }

    public String getRouterKey() {
        return routerKey;
    }
}
