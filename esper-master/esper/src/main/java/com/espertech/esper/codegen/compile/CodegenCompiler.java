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
package com.espertech.esper.codegen.compile;

import java.util.function.Supplier;

public interface CodegenCompiler {
    String getPackageName();
    <T> Class<T> compileClass(String code, String fullyQualifiedClassName, ClassLoader classLoader, Supplier<String> debugInformation) throws CodegenCompilerException;
}
