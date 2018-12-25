package com.bonaparte.constants;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

public class ConstantsProps {

    public static Map<Long, DeferredResult<Map>> mapDeferredResults = new HashedMap();
}
