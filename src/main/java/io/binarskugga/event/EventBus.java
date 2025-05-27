package io.binarskugga.event;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;

public class EventBus {
    public static final EventBus global = new EventBus();
    private final MBassador<Object> bus;

    private EventBus() {
        IBusConfiguration busConfiguration = new BusConfiguration()
                .addFeature(Feature.SyncPubSub.Default())
                .addFeature(Feature.AsynchronousHandlerInvocation.Default())
                .addFeature(Feature.AsynchronousMessageDispatch.Default())
                .addPublicationErrorHandler(new IPublicationErrorHandler.ConsoleLogger());
        this.bus = new MBassador<>(busConfiguration);
    }

    public <T extends IEvent> void publish(T event) {
        this.bus.post(event).now();
    }

    public <T extends ISubscriber> void subscribe(T subscriber) {
        this.bus.subscribe(subscriber);
    }

    public <T extends ISubscriber> void unsubscribe(T subscriber) {
        this.bus.unsubscribe(subscriber);
    }
}
