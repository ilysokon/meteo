package com.meteo.adapter.api.netatmo;

import com.meteo.application.ports.out.persistence.PersistenceService;
import com.meteo.domain.model.Geometeo;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ReactiveStreamsSubscriber implements Subscriber<Geometeo> {
    private static final Logger LOG = LoggerFactory.getLogger(ReactiveStreamsSubscriber.class);
    private final PersistenceService persistenceService;
    private final String message;

    public ReactiveStreamsSubscriber(PersistenceService persistenceService, String message) {
        this.persistenceService = persistenceService;
        this.message = message;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(1);
    }

    @Override
    public void onNext(Geometeo geometeo) {
        if(!geometeo.getMeasure().isEmpty()) {
            persistenceService.store(geometeo);
        } else {
            LOG.warn(message);
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
}
