package io.github.vishalmysore;

import io.github.vishalmysore.a2a.domain.JsonRpcRequest;
import io.github.vishalmysore.a2a.server.JsonRpcController;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Log
@Service
@RestController
@RequestMapping("/")
public class GrafanaRPCController extends JsonRpcController {
    @Autowired
    private MeterRegistry meterRegistry;

    private ConcurrentHashMap<String, Timer.Sample> timerSamples = new ConcurrentHashMap<>();

    @Override
    public void preProcessing(String method, Object params) {
        super.preProcessing(method, params);

        // Record method invocation count
        meterRegistry.counter("rpc.calls", "method", method).increment();

        // Start timing the RPC call
        Timer.Sample sample = Timer.start(meterRegistry);
        timerSamples.put(method, sample);
    }

    @Override
    public void postProcessing(String method, Object params) {
        super.postProcessing(method, params);

        // Stop timing and record duration
        Timer.Sample sample = timerSamples.remove(method);
        if (sample != null) {
            sample.stop(meterRegistry.timer("rpc.duration", "method", method));
        }
    }

    @PostMapping
    public Object handleRpc(@RequestBody JsonRpcRequest request) {
        log.info(request.toString());
        return super.handleRpc(request);
    }
}
