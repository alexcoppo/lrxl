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
import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;

/**
 * 
 */
public class ExpandoTableUtils {
    /**
     * Check whether an expando table exists.
     * 
     * @return the result of the operation
     * @throws PortalException
     * @throws SystemException 
     */
    public static boolean exists(long companyId, String className) throws PortalException, SystemException {
        try {
            ExpandoTableLocalServiceUtil.getDefaultTable(companyId, className);
            return true;
        } catch (NoSuchTableException ex) {
            return false;
        }
    }

    /**
     * Create a new expando table.
     * 
     * @throws SystemException
     * @throws PortalException 
     */
    public static void create(long companyId, String className) throws PortalException, SystemException {
        ExpandoTableLocalServiceUtil.addDefaultTable(companyId, className);
    }

    /**
     * 
     * @throws SystemException
     * @throws PortalException 
     */
    public static void createIfMissing(long companyId, String className) throws PortalException, SystemException {
        if (get(companyId, className) == null)
            ExpandoTableLocalServiceUtil.addDefaultTable(companyId, className);
    }

    public static void createForced(long companyId, String className) throws PortalException, SystemException {
        ExpandoTable et = get(companyId, className);
        if (get(companyId, className) != null)
            drop(et);
        create(companyId, className);
    }
    
    /**
     * Gets an expando table.
     * 
     * @return the result of the operation
     * @throws PortalException
     * @throws SystemException 
     */
    public static ExpandoTable get(long companyId, String className) throws PortalException, SystemException {
        try {
            return ExpandoTableLocalServiceUtil.getDefaultTable(companyId, className);
        } catch (NoSuchTableException ex) {
            return null;
        }
    }

    /**
     * Drop an existing expando table.
     * 
     * @param et the ExpandoTable to drop
     * @throws SystemException 
     */
    public static void drop(ExpandoTable et) throws SystemException {
        ExpandoTableLocalServiceUtil.deleteExpandoTable(et);
    }

    /**
     * Drop a table if exists.
     * 
     * @param companyId the company id
     * @param className the name of the class
     * @throws SystemException
     * @throws PortalException 
     */
    public static void dropSafe(long companyId, String className) throws SystemException, PortalException {
        ExpandoTable et = get(companyId, className);
        if (et != null)
            ExpandoTableLocalServiceUtil.deleteExpandoTable(et);
    }
}
