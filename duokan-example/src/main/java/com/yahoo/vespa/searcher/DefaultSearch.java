package com.yahoo.vespa.searcher;

import com.yahoo.search.Query;
import com.yahoo.search.Result;
import com.yahoo.search.Searcher;
import com.yahoo.search.searchchain.Execution;

public class DefaultSearch extends Searcher {

  @Override
  public Result search(Query query, Execution execution) {

//    return execution.search(query);
      return null;
  }

}
