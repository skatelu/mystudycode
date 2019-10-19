/*
 ***************************************************************************************
 *  Copyright (C) 2006 EsperTech, Inc. All rights reserved.                            *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 ***************************************************************************************
 */
package com.espertech.esper.epl.core.resultset.codegen;

import com.espertech.esper.epl.agg.service.common.AggregationServiceFactory;
import com.espertech.esper.epl.core.orderby.OrderByProcessorFactory;
import com.espertech.esper.epl.core.resultset.core.ResultSetProcessorFactory;

public interface ResultSetProcessorFactoryProvider {
    ResultSetProcessorFactory getResultSetProcessorFactory();
    AggregationServiceFactory getAggregationServiceFactory();
    OrderByProcessorFactory getOrderByProcessorFactory();
}
