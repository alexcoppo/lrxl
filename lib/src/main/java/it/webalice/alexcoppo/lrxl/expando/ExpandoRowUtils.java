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
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.service.ExpandoRowLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

/**
 * Utility class for ExpandoRow-s.
 */
public class ExpandoRowUtils {
    /**
     * Add a row to an expando table.
     * 
     * @param tbl the ExpandoTable
     * @param oid the id of the Liferay object to enrich
     * @return
     * @throws PortalException
     * @throws SystemException
     */
    public static ExpandoRow insertRow(ExpandoTable tbl, long oid) throws PortalException, SystemException {
    	return ExpandoRowLocalServiceUtil.addRow(tbl.getTableId(), oid);
    }

    /**
     * Get, inserting if missing, a row.
     * 
     * @param tbl the ExpandoTable
     * @param oid the id of the Liferay object to enrich
     * @return
     * @throws PortalException
     * @throws SystemException
     */
    public static ExpandoRow insertGetRow(ExpandoTable tbl, long oid) throws PortalException, SystemException {
		ExpandoRow er = getRow(tbl, oid);

		if (er == null)
		    er = ExpandoRowLocalServiceUtil.addRow(tbl.getTableId(), oid);
	
		return er;
    }

    /**
     * Get a row.
     * 
     * @param tbl the ExpandoTable
     * @param oid the id of the Liferay object to enrich
     * @return
     * @throws PortalException
     * @throws SystemException
     */
    public static ExpandoRow getRow(ExpandoTable tbl, long oid) throws PortalException, SystemException {
		try {
		    return ExpandoRowLocalServiceUtil.getRow(tbl.getTableId(), oid);
		} catch (PortalException pe) {
		    return null;
		}
    }

    /**
     * Check whether a row exists.
     * 
     * @param tbl the ExpandoTable
     * @param oid the id of the Liferay object to enrich
     * @return the result of the check
     * @throws PortalException
     * @throws SystemException
     */
    public static boolean existsRow(ExpandoTable tbl, long oid) throws PortalException, SystemException {
    	return getRow(tbl, oid) != null;
    }

    /**
     * Delete all values of the given object
     * 
     * @param oid the id of the Liferay object
     * @throws SystemException
     */
    public static void deleteAllValues(long oid) throws SystemException {
		ExpandoValueLocalServiceUtil.deleteRowValues(oid);
    }

    /**
     * Delete a row.
     * 
     * @param tbl the ExpandoTable
     * @param oid the id of the Liferay object
     * @throws PortalException
     * @throws SystemException
     */
    public static void deleteRow(ExpandoTable tbl, long oid) throws PortalException, SystemException {
    	ExpandoRowLocalServiceUtil.deleteRow(tbl.getTableId(), oid);
    }
}
