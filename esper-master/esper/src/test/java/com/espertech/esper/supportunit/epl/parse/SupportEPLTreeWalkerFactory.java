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
package com.espertech.esper.supportunit.epl.parse;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.core.support.SupportEngineImportServiceFactory;
import com.espertech.esper.core.support.SupportEventAdapterService;
import com.espertech.esper.epl.core.engineimport.EngineImportService;
import com.espertech.esper.epl.declexpr.ExprDeclaredServiceImpl;
import com.espertech.esper.epl.parse.EPLTreeWalkerListener;
import com.espertech.esper.epl.spec.SelectClauseStreamSelectorEnum;
import com.espertech.esper.epl.table.mgmt.TableServiceImpl;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.epl.variable.VariableServiceImpl;
import com.espertech.esper.pattern.PatternNodeFactoryImpl;
import org.antlr.v4.runtime.CommonTokenStream;

public class SupportEPLTreeWalkerFactory {
    public static EPLTreeWalkerListener makeWalker(CommonTokenStream tokenStream, EngineImportService engineImportService, VariableService variableService) {
        return new EPLTreeWalkerListener(tokenStream, engineImportService, variableService, SelectClauseStreamSelectorEnum.ISTREAM_ONLY, "uri", new Configuration(), new PatternNodeFactoryImpl(), null, null, new ExprDeclaredServiceImpl(), new TableServiceImpl());
    }

    public static EPLTreeWalkerListener makeWalker(CommonTokenStream tokenStream) {
        return makeWalker(tokenStream, SupportEngineImportServiceFactory.make(), new VariableServiceImpl(0, null, SupportEventAdapterService.getService(), null));
    }
}
