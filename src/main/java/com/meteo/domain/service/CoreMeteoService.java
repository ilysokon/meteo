package com.meteo.domain.service;

import com.meteo.domain.model.Geometeo;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteo.application.ports.out.geometeo.GeometeoService;
import com.meteo.application.ports.out.persistence.PersistenceService;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
        AtomicInteger countHowManyTimeTheResultWasNotEmpty = new AtomicInteger(0);
        Flux.from(geometeoService.fetchMeasure(deviceId, type, requestNumber, allRequestNumbers))
            .subscribe(new Subscriber<>() {
                @Override
                public void onSubscribe(Subscription subscription) {
                    subscription.request(1);
                }

                @Override
                public void onNext(Map<Long, Double> measure) {
                    if(!measure.isEmpty()) {
                        persistenceService.store(new Geometeo(deviceId, type, measure));
                    } else {
                        LOG.warn("no data in request: " + requestNumber + ", for deviceId " + deviceId);
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

    public void fetchMeasure(final String deviceId, final String moduleId, final String type, int requestNumber, int allRequestNumbers) {
        Flux.from(geometeoService.fetchMeasure(deviceId, moduleId, type, requestNumber, allRequestNumbers))
            .subscribe(new Subscriber<>() {
                @Override
                public void onSubscribe(Subscription subscription) {
                    subscription.request(1);
                }

                @Override
                public void onNext(Map<Long, Double> measure) {
                    if(!measure.isEmpty()) {
                        persistenceService.store(new Geometeo(deviceId, moduleId, type, measure));
                    } else {
                        LOG.warn("no data in request: " + requestNumber + ", for deviceId " + deviceId);
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
