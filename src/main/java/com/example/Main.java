package com.example;

import com.optimizely.ab.Optimizely;
import com.optimizely.ab.OptimizelyFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class Main {

    @RequestMapping("/")
    String index() {
        return "Hello, world!";
    }

    public static void main(String[] args) throws Exception {
        Service.service(new Subscription[]{
                new Subscription("ad", "fetch-product-page", (body, sender) -> {
                    String[] params = body.split(",");
                    String userId = params[1];

                    FeatureToggle featureToggle = new OptimizelyFeatureToggle(userId);

                    AdifyService adify = new AdifyService(new Adify(new HerokuGetRequest("adify"), featureToggle), body, sender);
                    adify.execute();
                })
        });

        SpringApplication.run(Main.class, args);
    }

}
