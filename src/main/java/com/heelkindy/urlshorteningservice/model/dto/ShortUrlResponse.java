package com.heelkindy.urlshorteningservice.model.dto;

import java.time.Instant;

public record ShortUrlResponse(String id, String url, String shortCode, Instant createdAt, Instant updatedAt) {
}
