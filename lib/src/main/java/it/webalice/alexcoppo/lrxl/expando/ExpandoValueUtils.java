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
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

/**
 * Utility class for ExpandoValue-s.
 */
public class ExpandoValueUtils {
    /**
     * Insert a new value.
     * 
     * @param et the expando table
     * @param ec the expando colum
     * @param oid the id of the Liferay object to enrich
     * @param value the value to insert
     * @return
     * @throws PortalException
     * @throws SystemException 
     */
    public static ExpandoValue insert(ExpandoTable tbl, ExpandoColumn ec, long oid, String value) throws PortalException, SystemException {
        ExpandoValue ev = ExpandoValueLocalServiceUtil.addValue(
            tbl.getCompanyId(),
            tbl.getClassName(),
            tbl.getName(),
            ec.getName(),
            oid,
            value);
        return ev;
    }
    
    /**
     * 
     * @param et the expando table
     * @param ec the expando colum
     * @param oid the id of the Liferay object
     * @return the specific ExpandoValue
     * @throws SystemException 
     */
    public static ExpandoValue fetch(ExpandoTable tbl, ExpandoColumn ec, long oid) throws SystemException {
        ExpandoValue ev = ExpandoValueLocalServiceUtil.getValue(
            tbl.getTableId(),
            ec.getColumnId(),
            oid
            );

        return ev;
    }
    
    /**
     * Update a value.
     * 
     * @param et the expando table
     * @param ec the expando colum
     * @param oid the id of the Liferay object to enrich
     * @param ev the value to update
     * @throws SystemException 
     */
    public static void update(ExpandoTable tbl, ExpandoColumn ec, long oid, ExpandoValue ev) throws SystemException {
        ev.setColumn(ec);
        ev.setPrimaryKey(oid);
        ExpandoValueLocalServiceUtil.updateExpandoValue(ev);
    }
    
    /**
     * Delete a given ExpandoValue
     * 
     * @param ev the ExpandoValue to delete
     * @throws SystemException 
     */
    public static void delete(ExpandoValue ev) throws SystemException {
        ExpandoValueLocalServiceUtil.deleteValue(ev);
    }
}
