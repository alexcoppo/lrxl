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
package it.webalice.alexcoppo.lrxl.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import it.webalice.alexcoppo.lrxl.portlet.impl.ActionHandlersLookupTable;
import it.webalice.alexcoppo.lrxl.portlet.impl.ResourceHandlersLookupTable;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceServingPortlet;
import net.sf.jautl.utility.reflection.ClassUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 */
public abstract class PortletBase extends GenericPortlet implements ResourceServingPortlet {
    private static Log _log = LogFactoryUtil.getLog(PortletBase.class);
    
    private ActionHandlersLookupTable actionHandlers;
    private ResourceHandlersLookupTable resourceHandlers;
    private JspDefaults jspDefaults;

    @Override
    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {
        String command = request.getParameter(ActionRequest.ACTION_NAME);
        Method method = actionHandlers.lookup(command);
        invokeHandler(command, method, request, response);
    }
    
    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
        String command = request.getParameter(ActionRequest.ACTION_NAME);
        Method method = resourceHandlers.lookup(command);
        invokeHandler(command, method, request, response);
    }
    
    private void invokeHandler(String command, Method method, Object arg0, Object arg1) throws PortletException, IOException {
        if (method == null) {
            String msg = String.format("No target for command '%s'", command);
            _log.error(msg);
            throw new PortletException(msg);
        }
        
        String errorMessage = "Error invoking '%s' handler";

        try {
            method.invoke(this, arg0, arg1);
        } catch (IllegalAccessException ex) {
            _log.error(String.format(errorMessage, command), ex);
            throw new PortletException(ex);
        } catch (IllegalArgumentException ex) {
            _log.error(String.format(errorMessage, command), ex);
            throw new PortletException(ex);
        } catch (InvocationTargetException ex) {
            _log.error(String.format(errorMessage, command), ex);
            throw new PortletException(ex);
        }
    }

    private void initialize() throws PortletException {
        jspDefaults = (JspDefaults)ClassUtils.getAnnotation(this, JspDefaults.class);
        if (jspDefaults == null) {
            String msg = "Missing JspDefaults annotation";
            _log.error(msg);
            throw new PortletException(msg);
        }

        if (actionHandlers == null) {
            actionHandlers = new ActionHandlersLookupTable(this);
            resourceHandlers = new ResourceHandlersLookupTable(this);
            
            if (actionHandlers.isEmpty() && resourceHandlers.isEmpty()) {
                String msg = "No handlers found";
                _log.error(msg);
                throw new PortletException(msg);
            }
        }
    }
    
    @Override
    public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        if (jspDefaults == null) initialize();

        defaultInjections(request, response);
        
        PortletMode mode = doViewAux(request, response);

        dispatch(mode, request, response);
    }
    
    @Override
    public void doEdit(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        if (jspDefaults == null) initialize();

        defaultInjections(request, response);
        
        PortletMode mode = doEditAux(request, response);

        dispatch(mode, request, response);
    }
    
    private void dispatch(PortletMode mode, RenderRequest request, RenderResponse response) throws PortletException, IOException {
        String jspFullName = mode.createJspName(jspDefaults);

        getPortletContext().getRequestDispatcher(jspFullName).include(request, response);
    }
    
    private void defaultInjections(RenderRequest request, RenderResponse response) {
        InjectNamespace n = (InjectNamespace)ClassUtils.getAnnotation(this, InjectNamespace.class);
        if (n != null) request.setAttribute(n.value(), response.getNamespace());
        
        actionHandlers.inject(request, response);
        resourceHandlers.inject(request, response);
    }
    
    protected PortletMode doViewAux(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        return PortletMode.view;
    }
    
    protected PortletMode doEditAux(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        return PortletMode.edit;
    }
    
    protected void returnJSON(ResourceResponse response, String jsonString) throws IOException {
        response.setContentLength(jsonString.length());
        response.setContentType("application/json");
        OutputStream out = response.getPortletOutputStream();
        IOUtils.write(jsonString, out);
        out.close();
    }
}
