package com.meteo.netatmo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteo.core.geometeo.GeometeoService;
import com.meteo.core.model.Geometeo;

import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Singleton
public class NetatmoMeteoService implements GeometeoService {
	private static final Logger LOG = LoggerFactory.getLogger(NetatmoMeteoService.class);

    private final NetatmoApiClient netatmoApiClient;
    private final NetatmoLowLevelClient netatmoLowLevelClient;

	public NetatmoMeteoService(final NetatmoApiClient netatmoApiClient, final NetatmoLowLevelClient netatmoLowLevelClient) {
        this.netatmoApiClient = netatmoApiClient;
        this.netatmoLowLevelClient = netatmoLowLevelClient;

        LOG.info("NetatmoMeteoService is created");
	}

	@Override
	public Geometeo getMeteo() {
		LOG.info("Getting meteo data from Netatmo serveer ...");
//        netatmoApiClient.retrieve(request, String.class)
//                .doOnNext(json -> {
//                    System.out.println("RAW JSON = " + json);
//                })
//                .subscribe();
        netatmoApiClient.fetchMeasurement().subscribe(new Subscriber<>() {
              @Override
              public void onSubscribe(Subscription subscription) {
                  subscription.request(5);
              }

              @Override
              public void onNext(String stringObjectMap) {
                  LOG.info("onNext: " + stringObjectMap);
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

        netatmoLowLevelClient.fetchMeasure()
        .subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(1);
            }

            @Override
            public void onNext(List<String> strings) {
                strings.forEach(LOG::info);
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

		return new Geometeo("Netatmo");
	}
}
