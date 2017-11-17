package com.yahoo.vespa.searcher;

import com.yahoo.processing.request.CompoundName;
import com.yahoo.search.Query;
import com.yahoo.search.Result;
import com.yahoo.search.Searcher;
import com.yahoo.search.searchchain.Execution;
import com.yahoo.tensor.Tensor;

public class ItemTensorSearch extends Searcher{

  @Override
  public Result search(Query query, Execution execution) {

    Tensor userVector = (Tensor)query.properties().get("user_vector");
    query.getModel().setRestrict("item");

    query.properties().set(new CompoundName("ranking"), "tensor");

    query.getRanking().getFeatures().put("query(user_vector)", userVector);

    return execution.search(query);
  }

}
