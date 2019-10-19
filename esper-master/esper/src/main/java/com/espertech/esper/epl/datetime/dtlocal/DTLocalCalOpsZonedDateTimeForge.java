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
package com.espertech.esper.epl.datetime.dtlocal;

import com.espertech.esper.codegen.base.CodegenClassScope;
import com.espertech.esper.codegen.base.CodegenMethodScope;
import com.espertech.esper.codegen.model.expression.CodegenExpression;
import com.espertech.esper.epl.datetime.calop.CalendarForge;
import com.espertech.esper.epl.expression.codegen.ExprForgeCodegenSymbol;

import java.util.List;

import static com.espertech.esper.epl.datetime.dtlocal.DTLocalUtil.getCalendarOps;

public class DTLocalCalOpsZonedDateTimeForge extends DTLocalForgeCalOpsCalBase implements DTLocalForge {
    public DTLocalCalOpsZonedDateTimeForge(List<CalendarForge> calendarForges) {
        super(calendarForges);
    }

    public DTLocalEvaluator getDTEvaluator() {
        return new DTLocalCalOpsZonedDateTimeEval(getCalendarOps(calendarForges));
    }

    public CodegenExpression codegen(CodegenExpression inner, Class innerType, CodegenMethodScope codegenMethodScope, ExprForgeCodegenSymbol exprSymbol, CodegenClassScope codegenClassScope) {
        return DTLocalCalOpsZonedDateTimeEval.codegen(this, inner, codegenMethodScope, exprSymbol, codegenClassScope);
    }
}
