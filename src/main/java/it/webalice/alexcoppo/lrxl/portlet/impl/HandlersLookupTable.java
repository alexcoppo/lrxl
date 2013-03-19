/*
    Copyright (c) 2013 Alessandro Coppo
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:
    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.
    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.
    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package it.webalice.alexcoppo.lrxl.portlet.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 *
 */
public abstract class HandlersLookupTable {
    protected Map<String, Method> handlers;
    
    protected HandlersLookupTable(Class clazz) {
        handlers = new HashMap<String, Method>();
        
        Method methods[] = clazz.getMethods();
        
        for (Method method : methods) {
            String tag = match(method);
            if (tag != null)
                handlers.put(tag, method);
        }
    }
    
    public boolean isEmpty() {
        return handlers.isEmpty();
    }
    
    public Method lookup(String tag) {
        return handlers.get(tag);
    }
    
    protected abstract String match(Method method);
    
    public abstract void inject(RenderRequest request, RenderResponse response);

    protected static boolean checkMethodSignature(Method method, Class class0, Class class1) {
        Class rt = method.getReturnType();
        if (!rt.equals(Void.TYPE)) return false;

        Class parameterTypes[] = method.getParameterTypes();
        if (parameterTypes.length != 2) return false;
        if (!parameterTypes[0].equals(class0)) return false;
        if (!parameterTypes[1].equals(class1)) return false;

        return true;
    }
}
