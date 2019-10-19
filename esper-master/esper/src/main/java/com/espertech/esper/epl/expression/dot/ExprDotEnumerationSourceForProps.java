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
package com.espertech.esper.epl.expression.dot;

import com.espertech.esper.epl.expression.core.ExprEnumerationEval;
import com.espertech.esper.epl.expression.core.ExprEnumerationGivenEvent;
import com.espertech.esper.epl.rettype.EPType;

public class ExprDotEnumerationSourceForProps extends ExprDotEnumerationSource {
    private final ExprEnumerationGivenEvent enumerationGivenEvent;

    public ExprDotEnumerationSourceForProps(ExprEnumerationEval enumeration, EPType returnType, Integer streamOfProviderIfApplicable, ExprEnumerationGivenEvent enumerationGivenEvent) {
        super(returnType, streamOfProviderIfApplicable, enumeration);
        this.enumerationGivenEvent = enumerationGivenEvent;
    }

    public ExprEnumerationGivenEvent getEnumerationGivenEvent() {
        return enumerationGivenEvent;
    }
}
