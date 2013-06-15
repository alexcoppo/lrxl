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
package it.webalice.alexcoppo.lrxl_samples.samplemd;

import it.webalice.alexcoppo.lrxl.portlet.JspDefaults;
import it.webalice.alexcoppo.lrxl.portlet.PortletBase;
import it.webalice.alexcoppo.lrxl.portlet.ResourceMapping;
import java.io.IOException;
import java.util.List;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import net.sf.jautl.json.JSONObjectBuilder;
import net.sf.jautl.md.DigestEngine;
import net.sf.jautl.md.DigestEngineFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

@JspDefaults(path = "/WEB-INF/jsp/samplemd/")
public class MessageDigestPortlet extends PortletBase {
    private static Log _log = LogFactoryUtil.getLog(MessageDigestPortlet.class);

    @ResourceMapping(command = "listKnownEngines")
    public void listKnownEngines(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
        List<String> engines = DigestEngineFactory.enumerate();

        JSONArray arr = new JSONArray();
        int index = 0;

        for (String engine : engines) {
            try {
                JSONObject eng = new JSONObject();
                eng.put("name", engine);
                arr.put(index++, eng);
            } catch (JSONException ex) {
                _log.error("JSON Error", ex);
                throw new PortletException(ex);
            }
        }

        returnJSON(response, arr.toString());
    }

    @ResourceMapping(command = "computeHash")
    public void computeHash(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
        String engineName = request.getParameter("engine");
        String message = request.getParameter("message");

        DigestEngine de = DigestEngineFactory.create(engineName);
        de.initiate();
        de.add(message);
        de.terminate();

        String jsonString = new JSONObjectBuilder().put("digest", de.getAsHex()).toString();

        returnJSON(response, jsonString);
    }
}
