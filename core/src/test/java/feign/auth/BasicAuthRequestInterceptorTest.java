/*
 * Copyright 2013 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package feign.auth;

import feign.RequestTemplate;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Collections;

import static org.testng.Assert.assertEquals;

/**
 * Tests for {@link BasicAuthRequestInterceptor}.
 */
public class BasicAuthRequestInterceptorTest {
  /**
   * Tests that request headers are added as expected.
   */
  @Test public void testAuthentication() {
    RequestTemplate template = new RequestTemplate();
    BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor("Aladdin", "open sesame");
    interceptor.apply(template);
    Collection<String> actualValue = template.headers().get("Authorization");
    Collection<String> expectedValue = Collections.singletonList("Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
    assertEquals(actualValue, expectedValue);
  }

  /**
   * Tests that requests headers are added as expected when user and pass are too long
   */
  @Test public void testAuthenticationWhenUserPassAreTooLong() {
    RequestTemplate template = new RequestTemplate();
    BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor("IOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIOIO",
              "101010101010101010101010101010101010101010");
    interceptor.apply(template);
    Collection<String> actualValue = template.headers().get("Authorization");
    Collection<String> expectedValue = Collections.
        singletonList("Basic SU9JT0lPSU9JT0lPSU9JT0lPSU9JT0lPSU9JT0lPSU9JT0lPSU86MTAxMDEwMTAxMDEwMTAxMDEwMTAxMDEwMTAxMDEwMTAxMDEwMTAxMDEw");
    assertEquals(actualValue, expectedValue);
  }
}
