package com.serotonin.bacnet4j;

import org.junit.After;
import org.junit.Before;

import com.serotonin.bacnet4j.npdu.test.TestNetwork;
import com.serotonin.bacnet4j.npdu.test.TestNetworkMap;
import com.serotonin.bacnet4j.transport.DefaultTransport;

import lohbihler.warp.WarpClock;

/**
 * Common base class for tests that use real local devices and a warp clock.
 *
 * @author Matthew
 */
abstract public class AbstractTest {
    private final TestNetworkMap map = new TestNetworkMap();
    protected final WarpClock clock = new WarpClock();
    protected final LocalDevice d1 = new LocalDevice(1,
            new DefaultTransport(new TestNetwork(map, 1, 0).withTimeout(500))).withClock(clock);
    protected final LocalDevice d2 = new LocalDevice(2,
            new DefaultTransport(new TestNetwork(map, 2, 0).withTimeout(500))).withClock(clock);
    protected final LocalDevice d3 = new LocalDevice(3, new DefaultTransport(new TestNetwork(map, 3, 0)))
            .withClock(clock);
    protected final LocalDevice d4 = new LocalDevice(4, new DefaultTransport(new TestNetwork(map, 4, 0)))
            .withClock(clock);
    protected RemoteDevice rd1;
    protected RemoteDevice rd2;
    protected RemoteDevice rd3;

    @Before
    public void abstractBefore() throws Exception {
        beforeInit();

        d1.initialize();
        d2.initialize();
        d3.initialize();
        d4.initialize();

        // Get d1 as a remote object.
        rd1 = d2.getRemoteDevice(1).get();
        rd2 = d1.getRemoteDevice(2).get();
        rd3 = d1.getRemoteDevice(3).get();

        afterInit();
    }

    public void beforeInit() throws Exception {
        // Override as required
    }

    public void afterInit() throws Exception {
        // Override as required
    }

    @After
    public void abstractAfter() {
        // Shut down
        d1.terminate();
        d2.terminate();
        d3.terminate();
        d4.terminate();
    }
}
