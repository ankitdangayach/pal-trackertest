package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private String port;
    private String memorylimit;
    private String cfInstanceIndex;
    private String cfInstanceAddr;

    public EnvController(@Value("${port:NOT SET}")String port, @Value("${memory.limit:NOT SET}")String memorylimit, @Value("${cf.instance.index:NOT SET}")String cfInstanceIndex,
                         @Value("${cf.instance.addr:NOT SET}")String cfInstanceAddr) {
        this.port=port;
        this.memorylimit=memorylimit;
        this.cfInstanceIndex=cfInstanceIndex;
        this.cfInstanceAddr=cfInstanceAddr;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        Map<String, String> envVariable = new HashMap<>();
        envVariable.put("PORT",port);
        envVariable.put("MEMORY_LIMIT",memorylimit);
        envVariable.put("CF_INSTANCE_INDEX",cfInstanceIndex);
        envVariable.put("CF_INSTANCE_ADDR",cfInstanceAddr);
        return envVariable;
    }
}
