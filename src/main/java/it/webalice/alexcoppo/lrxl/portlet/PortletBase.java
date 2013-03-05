package it.webalice.alexcoppo.lrxl.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceServingPortlet;

/**
 *
 */
public abstract class PortletBase extends GenericPortlet implements ResourceServingPortlet {
    public final static String COMMAND_KEY = "command";

    private static Log _log = LogFactoryUtil.getLog(PortletBase.class);
    
    private Map<String, Method> actionHandlers;
    private Map<String, Method> resourceHandlers;
    
    protected PortletBase() {
        actionHandlers = new HashMap<String, Method>();
        gatherMethods(actionHandlers, ActionRequest.class, ActionResponse.class);

        resourceHandlers = new HashMap<String, Method>();
        gatherMethods(resourceHandlers, ResourceRequest.class, ResourceResponse.class);
    }
    
    private void gatherMethods(Map<String, Method> repository, Class class0, Class class1) {
        Method methods[] = getClass().getDeclaredMethods();
        
        for (Method method : methods) {
            if (!checkMethodSignature(method, class0, class1))
                continue;

            Annotation ann = method.getAnnotation(Mapping.class);
            
            if (ann != null) {
                Mapping mapping = (Mapping)ann;
                repository.put(mapping.command(), method);
            }
        }
    }
    
    private boolean checkMethodSignature(Method method, Class class0, Class class1) {
        if (method.getReturnType() != Void.class) return false;
        
        Class parameterTypes[] = method.getParameterTypes();
        
        if (parameterTypes.length != 2) return false;
        if (parameterTypes[0] != class0) return false;
        if (parameterTypes[1] != class1) return false;
        
        return true;
    }
    
    @Override
    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {
        String command = request.getParameter(COMMAND_KEY);
        Method method = resourceHandlers.get(command);
        invokeHandler(command, method, request, response);
    }
    
    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
        String command = request.getParameter(COMMAND_KEY);
        Method method = resourceHandlers.get(command);
        invokeHandler(command, method, request, response);
    }
    
    private void invokeHandler(String command, Method method, Object arg0, Object arg1) throws PortletException, IOException {
        final String errorMessage = "Error invoking '%s' handler";

        if (method == null) {
            _log.error(String.format("No target for command '%s'", command));
            return;
        }
        
        try {
            method.invoke(this, arg0, arg1);
        } catch (IllegalAccessException ex) {
            _log.error(String.format(errorMessage, command), ex);
        } catch (IllegalArgumentException ex) {
            _log.error(String.format(errorMessage, command), ex);
        } catch (InvocationTargetException ex) {
            _log.error(String.format(errorMessage, command), ex);
        }
    }
}
