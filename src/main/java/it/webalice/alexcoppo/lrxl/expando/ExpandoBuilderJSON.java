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
package it.webalice.alexcoppo.lrxl.expando;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.expando.model.ExpandoTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//TODO: Farlo funzionare soltanto per una tabella, visto che tanto sara'
//inizializzato da Spring e pertanto ce ne potranno essere tanti?

/**
 *
 */
public class ExpandoBuilderJSON extends ExpandoBuilder {
    private String json;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public void process(long companyId) throws SystemException, PortalException {
        try {
            JSONArray commands = new JSONArray(json);

            for (int index = 0; ; index++) {
                JSONObject tableDesc = commands.getJSONObject(index);
                if (tableDesc == null) break;
                processTable(companyId, tableDesc);
            }
        } catch (JSONException je) {
            throw new SystemException("", je);
        }
    }
    
    private void processTable(long companyId, JSONObject tableDesc) throws SystemException, PortalException, JSONException {
        String className = tableDesc.optString("name");
        
        ExpandoTable et = ensurePresent(companyId, className);
        
        JSONArray columnsDesc = tableDesc.getJSONArray("columns");
        for (int index = 0; ; index++) {
            JSONObject columnDesc = columnsDesc.getJSONObject(index);
            if (columnDesc == null) break;
            processColumn(et, columnDesc);
        }
    }
    
    private void processColumn(ExpandoTable et, JSONObject columnDesc) throws JSONException, SystemException, PortalException {
        String columnName = columnDesc.getString("name");
        String columnType = columnDesc.getString("type");

        ensurePresent(et, columnName, columnType);
    }
}

/*
 * [
 *  { "name" : "qqq.pluto",
 *      columns :
 *      [
 *          { "name" : "a", "type" : "STRING" },
 *          { "name" : "b", "type" : "LONG" },
 *          { "name" : "c", "type" : "STRING" }
 *      ]
 *   },
 *  { "name" : "qqq.pippo",
 *      columns :
 *      [
 *          { "name" : "a1", "type" : "STRING" },
 *          { "name" : "b2", "type" : "LONG" },
 *          { "name" : "c1", "type" : "STRING" }
 *      ]
 *   }
 * ]
 */