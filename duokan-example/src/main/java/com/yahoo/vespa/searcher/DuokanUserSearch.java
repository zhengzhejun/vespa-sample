package com.yahoo.vespa.searcher;

import com.yahoo.search.Query;
import com.yahoo.search.Result;
import com.yahoo.search.Searcher;
import com.yahoo.search.searchchain.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DuokanUserSearch extends Searcher{

  private static final Logger logger = LoggerFactory.getLogger(DuokanUserSearch.class);

  public Result search(Query query, Execution execution) {
    return null;
  }

}
