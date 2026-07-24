package com.heelkindy.urlshorteningservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ShortUrlRequest {

    @NotBlank(message = "URL cannot be left blank")
    @Pattern(regexp = "https?://.+",
            message = "URL must start with http:// or https://"
    )
    private String url;

    public ShortUrlRequest() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
