package com.yahoo.vespa.searcher;

import com.yahoo.prelude.query.AndItem;
import com.yahoo.prelude.query.CompositeItem;
import com.yahoo.prelude.query.Item;
import com.yahoo.prelude.query.WordItem;
import com.yahoo.search.Query;
import com.yahoo.search.Result;
import com.yahoo.search.Searcher;
import com.yahoo.search.query.QueryTree;
import com.yahoo.search.searchchain.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultSearch extends Searcher {

  private static Logger logger = LoggerFactory.getLogger(DefaultSearch.class);

  @Override
  public Result search(Query query, Execution execution) {
    logger.info("start DefaultSearch, query:{}", query);
    return execution.search(query);
  }

}
