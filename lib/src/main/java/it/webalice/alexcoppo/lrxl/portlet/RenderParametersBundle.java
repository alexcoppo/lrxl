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

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;

/**
 *
 */
public class RenderParametersBundle {
    private Map<String, String> parameters;

    protected RenderParametersBundle() {
        parameters = new HashMap<String, String>();
    }

    protected void register(String tag) {
        parameters.put(tag, null);
    }

    public boolean exists(String tag) {
        return parameters.containsKey(tag);
    }

    public void set(String tag, String value) {
        if (exists(tag))
            parameters.put(tag, value);
    }

    public String get(String tag) {
        if (exists(tag))
            return parameters.get(tag);
        else
            return null;
    }

    public void sendToRenderPhase(RenderRequest request) {
        for (String tag : parameters.keySet())
            request.setAttribute(tag, parameters.get(tag));
    }
}
