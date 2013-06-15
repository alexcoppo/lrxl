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

import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.expando.model.ExpandoTable;

/**
 * ExpandoTableBuilder configured as bean, to be used inside a Spring
 * configuration file.
 */
public class ExpandoTableBuilderBean implements ExpandoBuilder {
    private String className;
    private List<String> columnDefs;

    /**
     * 
     * 
     * @return
     */
    public String getClassName() {
		return className;
    }

    /**
     * 
     * 
     * @param className
     */
    public void setClassName(String className) {
		this.className = className;
    }

    /**
     * 
     * 
     * @return
     */
    public List<String> getColumnDefs() {
		return columnDefs;
    }

    /**
     * 
     * 
     * @param columnDefs
     */
    public void setColumnDefs(List<String> columnDefs) {
		this.columnDefs = columnDefs;
    }

    @Override
    public void process(long companyId) throws SystemException, PortalException {
		ExpandoTable et = ExpandoTableUtils.createIfMissing(companyId, className);
	
		for (String columDef : columnDefs) {
		    String items[] = columDef.split(":");
	
		    String columnName = items[0];
		    String columnType = (items.length > 1) ? items[1] : "STRING";
		    int liferayColumnType = ExpandoColumnTypeUtils .typeLabelToInt(columnType);
	
		    if (liferayColumnType != 0) {
		    	ExpandoColumnUtils.createIfMissing(et, columnName, liferayColumnType);
		    } else {
			throw new PortalException(
				String.format("Unrecognized type '%s' for column '%s'", columnType,columnName));
		    }
		}
    }
}
