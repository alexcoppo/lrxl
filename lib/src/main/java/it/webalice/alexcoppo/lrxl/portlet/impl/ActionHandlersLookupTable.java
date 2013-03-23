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

import it.webalice.alexcoppo.lrxl.portlet.ActionMapping;
import java.lang.reflect.Method;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.BaseURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import net.sf.jautl.utility.reflection.MethodUtils;

/**
 *
 */
public class ActionHandlersLookupTable extends HandlersLookupTable {
    public ActionHandlersLookupTable(Object obj) {
        super(obj.getClass());
    }
    
    @Override
    protected String match(Method method) {
        if (!checkMethodSignature(method, ActionRequest.class, ActionResponse.class))
            return null;

        if (!MethodUtils.hasAnnotation(method, ActionMapping.class))
            return null;

        ActionMapping map = (ActionMapping)method.getAnnotation(ActionMapping.class);
        return map.command();
    }

    public void inject(RenderRequest request, RenderResponse response) {
        for (String name : handlers.keySet()) {
            Method method = handlers.get(name);
            ActionMapping am = (ActionMapping)MethodUtils.getAnnotation(method, ActionMapping.class);
            inject(am, request, response);
        }
    }

    private void inject(ActionMapping am, RenderRequest request, RenderResponse response) {
        BaseURL url = response.createActionURL();
        
        url.setParameter(ActionRequest.ACTION_NAME, am.command());
        
        request.setAttribute(ActionMappingUtils.getDecoratedName(am), url);
    }
}
