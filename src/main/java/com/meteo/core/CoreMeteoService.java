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

	public void handleMeteo() {
		geometeoService.getMeteo().subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(5);
            }

            @Override
            public void onNext(Map<Long, Double> measure) {
                persistenceService.store(new Geometeo(measure));
                // LOG.info("onNext: " + measure);
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
