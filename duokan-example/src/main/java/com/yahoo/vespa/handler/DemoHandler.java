package com.yahoo.vespa.handler;

import com.google.inject.Inject;
import com.yahoo.container.jdisc.HttpRequest;
import com.yahoo.container.jdisc.HttpResponse;
import com.yahoo.container.jdisc.LoggingRequestHandler;
import com.yahoo.container.logging.AccessLog;
import com.yahoo.search.handler.SearchHandler;
import com.yahoo.search.query.Model;
import java.util.concurrent.Executor;

public class DemoHandler extends LoggingRequestHandler {

  private final SearchHandler searchHandler;

  @Inject
  public DemoHandler(Executor executor, AccessLog accessLog, SearchHandler searchHandler) {
    super(executor, accessLog, null, true);
    this.searchHandler = searchHandler;
  }

  @Override
  public HttpResponse handle(HttpRequest request) {
    String searchChain = request.getProperty("chain");
    HttpRequest searchRequest = new HttpRequest.Builder(request)
        .put("yql", "select * from sources * where user_id contains \"67758960\";")
        .put("searchChain", searchChain).createDirectRequest();
    return searchHandler.handle(searchRequest);

  }

}
