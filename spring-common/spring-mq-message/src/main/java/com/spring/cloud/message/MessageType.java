package com.spring.cloud.message;

public enum  MessageType {
    MenuChange("router.user.menu_change"),
    MenuDelete("router.user.menu_delete"),
    UnbindResource("router.user.unbind_resource"),
    BindResource("router.user.bind_resource"),
    AuthenticationChange("router.geteway.authencation_change"),
    ResourceRegister("router.resource.register");

    private final String routerKey;

    MessageType(String routerKey) {
        this.routerKey = routerKey;
    }

    public String getRouterKey() {
        return routerKey;
    }
}
