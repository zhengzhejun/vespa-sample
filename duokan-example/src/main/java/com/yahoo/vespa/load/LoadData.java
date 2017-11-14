package com.yahoo.vespa.load;

import com.yahoo.documentapi.AsyncParameters;
import com.yahoo.documentapi.AsyncSession;
import com.yahoo.documentapi.DocumentAccess;

public class LoadData {

  private final DocumentAccess access = DocumentAccess.createDefault();
  private final AsyncSession session = access.createAsyncSession(new AsyncParameters());



}
