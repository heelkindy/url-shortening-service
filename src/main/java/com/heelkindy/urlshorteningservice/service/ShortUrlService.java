package com.heelkindy.urlshorteningservice.service;

import com.heelkindy.urlshorteningservice.exception.ShortUrlNotFoundException;
import com.heelkindy.urlshorteningservice.model.dto.ShortUrlRequest;
import com.heelkindy.urlshorteningservice.model.dto.ShortUrlResponse;
import com.heelkindy.urlshorteningservice.model.dto.StatsResponse;
import com.heelkindy.urlshorteningservice.model.entity.ShortUrl;
import com.heelkindy.urlshorteningservice.repository.ShortUrlRepository;
import com.heelkindy.urlshorteningservice.util.Base62Encoder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @Transactional
    public ShortUrlResponse create(ShortUrlRequest request) {
        ShortUrl shortUrl = new ShortUrl(request.getUrl());
        ShortUrl saved = shortUrlRepository.save(shortUrl);

        String code = Base62Encoder.encode(saved.getId());
        saved.setShortCode(code);
        ShortUrl updated = shortUrlRepository.save(saved);

        return toResponse(updated);
    }

    @Transactional
    public ShortUrlResponse getByShortCode(String shortCode) {
        ShortUrl shortUrl = findByShortCodeOrThrow(shortCode);

        shortUrl.setAccessCount(shortUrl.getAccessCount() + 1);
        ShortUrl updated = shortUrlRepository.save(shortUrl);

        return toResponse(updated);
    }

    @Transactional
    public ShortUrlResponse update(String shortCode, ShortUrlRequest request) {
        ShortUrl shortUrl = findByShortCodeOrThrow(shortCode);
        shortUrl.setOriginalUrl(request.getUrl());

        ShortUrl updated = shortUrlRepository.save(shortUrl);
        return toResponse(updated);
    }

    public void delete(String shortCode) {
        ShortUrl shortUrl = findByShortCodeOrThrow(shortCode);
        shortUrlRepository.delete(shortUrl);
    }


    public StatsResponse getStats(String shortCode) {
        ShortUrl shortUrl = findByShortCodeOrThrow(shortCode);

        return new StatsResponse(
                String.valueOf(shortUrl.getId()),
                shortUrl.getOriginalUrl(),
                shortUrl.getShortCode(),
                shortUrl.getCreatedAt(),
                shortUrl.getUpdatedAt(),
                shortUrl.getAccessCount()
        );
    }

    private ShortUrl findByShortCodeOrThrow(String shortCode) {

        return shortUrlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ShortUrlNotFoundException(shortCode));
    }

    private ShortUrlResponse toResponse(ShortUrl shortUrl) {
        return new ShortUrlResponse(
                String.valueOf(shortUrl.getId()),
                shortUrl.getOriginalUrl(),
                shortUrl.getShortCode(),
                shortUrl.getCreatedAt(),
                shortUrl.getUpdatedAt()
        );
    }
}
