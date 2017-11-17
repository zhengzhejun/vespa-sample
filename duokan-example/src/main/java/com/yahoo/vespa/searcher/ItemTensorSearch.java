package com.yahoo.vespa.searcher;

import com.yahoo.processing.request.CompoundName;
import com.yahoo.search.Query;
import com.yahoo.search.Result;
import com.yahoo.search.Searcher;
import com.yahoo.search.searchchain.Execution;
import com.yahoo.tensor.Tensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemTensorSearch extends Searcher{

  private static final Logger logger = LoggerFactory.getLogger(ItemTensorSearch.class);

  @Override
  public Result search(Query query, Execution execution) {

    logger.info("start ItemTensorSearch");

    Tensor userVector = (Tensor)query.properties().get("user_vector");

    logger.info("userVector is {}", Tensor.toStandardString(userVector));

    query.getModel().setRestrict("item");

    query.properties().set(new CompoundName("ranking"), "tensor");

    query.getRanking().getFeatures().put("query(user_vector)", userVector);

    return execution.search(query);
  }

}
