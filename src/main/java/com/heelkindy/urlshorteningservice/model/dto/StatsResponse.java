package com.heelkindy.urlshorteningservice.model.dto;

import java.time.Instant;

public record StatsResponse(String id, String url, String shortCode, Instant createdAt, Instant updatedAt,
                            long accessCount) {
}
