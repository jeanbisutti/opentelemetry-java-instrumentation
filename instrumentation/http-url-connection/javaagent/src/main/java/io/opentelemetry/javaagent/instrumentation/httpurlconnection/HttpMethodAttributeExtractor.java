/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.javaagent.instrumentation.httpurlconnection;

import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.context.Context;
import io.opentelemetry.instrumentation.api.instrumenter.AttributesExtractor;
import io.opentelemetry.semconv.trace.attributes.SemanticAttributes;
import java.net.HttpURLConnection;
import javax.annotation.Nullable;

public class HttpMethodAttributeExtractor<
        REQUEST extends HttpURLConnection, RESPONSE extends Integer>
    implements AttributesExtractor<REQUEST, RESPONSE> {

  private HttpMethodAttributeExtractor() {}

  public static AttributesExtractor<? super HttpURLConnection, ? super Integer> create() {
    return new HttpMethodAttributeExtractor<>();
  }

  @Override
  public void onStart(AttributesBuilder attributes, Context parentContext, HttpURLConnection o) {}

  @Override
  public void onEnd(
      AttributesBuilder attributes,
      Context context,
      HttpURLConnection connection,
      @Nullable Integer responseCode,
      @Nullable Throwable error) {

    GetOutputStreamContext getOutputStreamContext = GetOutputStreamContext.retrieveFrom(context);

    if (getOutputStreamContext.isOutputStreamMethodOfSunConnectionCalled()) {
      String requestMethod = connection.getRequestMethod();
      // The getOutputStream() has transformed "GET" into "POST"
      attributes.put(SemanticAttributes.HTTP_METHOD, requestMethod);
    }
  }
}
