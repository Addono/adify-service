package com.example;

import com.optimizely.ab.Optimizely;
import com.optimizely.ab.OptimizelyFactory;

public class OptimizelyFeatureToggle implements FeatureToggle {

    private String optimizelySdkKey = System.getenv("OPTIMIZELY_SDK_KEY");
    private Optimizely optimizelyClient = OptimizelyFactory.newDefaultInstance(optimizelySdkKey);
    private String userId;

    public OptimizelyFeatureToggle(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean featureA() {
        return optimizelyClient.isFeatureEnabled("feature-a", userId);
    }
}
