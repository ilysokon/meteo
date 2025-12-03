package com.meteo.adapter.reactivestreams;

import com.meteo.adapter.api.netatmo.NetatmoMeteoService;
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
public class ReactiveGeometeoService implements GeometeoService {
	private static final Logger LOG = LoggerFactory.getLogger(ReactiveGeometeoService.class);

	private final NetatmoMeteoService netatmoMeteoService;
	private final PersistenceService persistenceService;

	@Inject
	public ReactiveGeometeoService(final NetatmoMeteoService netatmoMeteoService, final PersistenceService persistenceService) {
		this.netatmoMeteoService = netatmoMeteoService;
		this.persistenceService = persistenceService;

		LOG.info("CoreMeteoService created");
	}

    @Override
	public void fetchMeasureAndPersist(final String deviceId, final String type, int requestNumber, int allRequestNumbers) {
        AtomicInteger countHowManyTimeTheResultWasNotEmpty = new AtomicInteger(0);
        Flux.from(netatmoMeteoService.fetchMeasure(deviceId, type, requestNumber, allRequestNumbers))
            .subscribe(new Subscriber<>() {
                @Override
                public void onSubscribe(Subscription subscription) {
                    subscription.request(1);
                }

                @Override
                public void onNext(Geometeo geometeo) {
                    if(!geometeo.getMeasure().isEmpty()) {
                        persistenceService.store(geometeo);
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

    public void fetchMeasureAndPersist(final String deviceId, final String moduleId, final String type, int requestNumber, int allRequestNumbers) {
        Flux.from(netatmoMeteoService.fetchMeasure(deviceId, moduleId, type, requestNumber, allRequestNumbers))
            .subscribe(new Subscriber<>() {
                @Override
                public void onSubscribe(Subscription subscription) {
                    subscription.request(1);
                }

                @Override
                public void onNext(Geometeo geometeo) {
                    if(!geometeo.getMeasure().isEmpty()) {
                        persistenceService.store(geometeo);
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
