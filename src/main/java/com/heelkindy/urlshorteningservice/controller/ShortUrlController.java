package com.heelkindy.urlshorteningservice.controller;

import com.heelkindy.urlshorteningservice.model.dto.ShortUrlRequest;
import com.heelkindy.urlshorteningservice.model.dto.ShortUrlResponse;
import com.heelkindy.urlshorteningservice.model.dto.StatsResponse;
import com.heelkindy.urlshorteningservice.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorten")
public class ShortUrlController {
    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShortUrlResponse create(@Valid @RequestBody ShortUrlRequest request) {
        return shortUrlService.create(request);
    }

    @GetMapping("/{shortCode}")
    public ShortUrlResponse getByShortCode(@PathVariable String shortCode) {
        return shortUrlService.getByShortCode(shortCode);
    }

    @PutMapping("/{shortCode}")
    public ShortUrlResponse update(@PathVariable String shortCode, @Valid @RequestBody ShortUrlRequest request) {
        return shortUrlService.update(shortCode, request);
    }

    @DeleteMapping("/{shortCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String shortCode) {
        shortUrlService.delete(shortCode);
    }

    @GetMapping("/{shortCode}/stats")
    public StatsResponse getStats(@PathVariable String shortCode) {
        return shortUrlService.getStats(shortCode);
    }
}

