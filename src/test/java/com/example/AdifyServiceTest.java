package com.example;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AdifyServiceTest {

  class SenderSpy implements Subscription.Sender {
    String event;
    String body;

    @Override
    public void send(String event, String body) {
      this.event = event;
      this.body = body;
    }
  }

  class FeatureToggleStub implements FeatureToggle {

    @Override
    public boolean featureA() {
      return true;
    }

  }

  @Test
  @Tag("slow")
  void endToEndTest() {
    SenderSpy spy = new SenderSpy();
    AdifyService a = new AdifyService(new Adify(new HerokuGetRequest("adify"), new FeatureToggleStub()), "SESSION_ID,USER_ID,PRODUCT_ID", spy);
    a.execute();

    assertEquals("display", spy.event);

    String[] buffer = spy.body.split(",");
    assertEquals(buffer[0], "SESSION_ID");
    assertEquals(buffer[1], "advert");
    assertEquals(buffer[2], "PRODUCT_ID");
    assertNotEquals(buffer[3], "null");
  }

  @Test
  @Tag("fast")
  void componentTest() {
    class HerokuGetRequestMock implements ExternalService {
      String productName;

      public HerokuGetRequestMock(String productName) {
        this.productName = productName;
      }

      @Override
      public String get(String _) {
        return String.format("{\"%s\":\"%s\"}", new FeatureToggleStub().featureA() ? "product-name" : "product", this.productName);
      }
    }

    SenderSpy spy = new SenderSpy();
    AdifyService a = new AdifyService(new Adify(new HerokuGetRequestMock("Paper"), new FeatureToggleStub()), "SESSION_ID,USER_ID,PRODUCT_ID", spy);
    a.execute();

    assertEquals("SESSION_ID,advert,PRODUCT_ID,Paper", spy.body);
  }

}