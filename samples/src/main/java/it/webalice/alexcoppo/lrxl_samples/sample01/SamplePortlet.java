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
package it.webalice.alexcoppo.lrxl_samples.sample01;

import it.webalice.alexcoppo.lrxl.portlet.PortletBase;
import java.io.IOException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import it.webalice.alexcoppo.lrxl.portlet.ActionMapping;
import it.webalice.alexcoppo.lrxl.portlet.InjectNamespace;
import it.webalice.alexcoppo.lrxl.portlet.JspDefaults;
import it.webalice.alexcoppo.lrxl.portlet.ResourceMapping;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

@JspDefaults(path = "/WEB-INF/jsp/sample01/")
@InjectNamespace
public class SamplePortlet extends PortletBase {
    private static Log _log = LogFactoryUtil.getLog(SamplePortlet.class);

    @ActionMapping(command="onAction1")
    public void onCommand1(ActionRequest request, ActionResponse response) throws PortletException, IOException {
        _log.info("Inside action 1");
    }
    
    @ActionMapping(command="onAction2")
    public void onCommand2(ActionRequest request, ActionResponse response) throws PortletException, IOException {
        _log.info("Inside action 2");
    }
    
    @ResourceMapping(command="onResource1")
    public void onResource1(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
        _log.info("Inside resource 1");
    }
    
    @ResourceMapping(command="onResource2")
    public void onResource2(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
        _log.info("Inside resource 1");
    }
}
