package com.example;

import com.optimizely.ab.Optimizely;
import com.optimizely.ab.OptimizelyFactory;
import org.springframework.beans.factory.annotation.Value;

public class OptimizelyFeatureToggle implements FeatureToggle {

    @Value("${optimizelySdkKey}")
    private String optimizelySdkKey;
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
