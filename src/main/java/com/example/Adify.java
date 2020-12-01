package com.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class Adify {
  private final ExternalService service;

  private final FeatureToggle featureToggle;

  Adify(ExternalService service, FeatureToggle featureToggle) {
    this.service = service;
    this.featureToggle = featureToggle;
  }

  String fetch(String productId) {
    try {
      String content = service.get("?product=" + productId);
      JSONObject obj = (JSONObject) new JSONParser().parse(content);

      return (String) obj.get(featureToggle.featureA() ? "product-name" : "product");
    } catch (Exception e) {
      return "";
    }
  }
}
