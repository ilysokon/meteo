package com.meteo.core;

import com.meteo.core.model.Geometeo;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteo.core.geometeo.GeometeoService;
import com.meteo.core.persistence.PersistenceService;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;

@Singleton
public class CoreMeteoService {
	private static final Logger LOG = LoggerFactory.getLogger(CoreMeteoService.class);

	private final GeometeoService geometeoService;
	private final PersistenceService persistenceService;

	@Inject
	public CoreMeteoService(final GeometeoService geometeoService, final PersistenceService persistenceService) {
		this.geometeoService = geometeoService;
		this.persistenceService = persistenceService;

		LOG.info("CoreMeteoService created");
	}

	public void fetchMeasure(final String deviceId, final String type, int requestNumber, int allRequestNumbers) {
        Flux.from(geometeoService.fetchMeasure(deviceId, type, requestNumber, allRequestNumbers))
                .retryWhen(
                    Retry.backoff(3, Duration.ofSeconds(2)) // 3 попытки, пауза 2 секунды
                            .maxBackoff(Duration.ofSeconds(30)) // максимум 30 секунд
                            .filter(ex -> {
                                LOG.error("Retrying because of error: {}", ex.getMessage());
                                return true; // retry on all errors
                            })
                ).subscribe(new Subscriber<>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subscription.request(1);
                    }

                    @Override
                    public void onNext(Map<Long, Double> measure) {
                        if(!measure.isEmpty()) {
                            persistenceService.store(new Geometeo(deviceId, type, measure));
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LOG.error("onError " + throwable);
                    }

                    @Override
                    public void onComplete() {
                        LOG.info("completed");
                    }
                });

	}
}
