package com.careerresethub.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LaunchRedirectEnum {

    PRE_CAPTURE(true, "/sold-out.html"),
    CAPTURING(false, "/index.html"),
    SALES_OPEN(true, "/replay.html"),
    EXPIRED(true, "/sold-out.html");

    public final boolean shouldRedirect;
    public final String targetUrl;

    LaunchRedirectEnum(boolean shouldRedirect, String targetUrl) {
        this.shouldRedirect = shouldRedirect;
        this.targetUrl = targetUrl;
    }
}
