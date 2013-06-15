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

import com.liferay.portlet.expando.model.ExpandoColumnConstants;

/**
 * Some utilities on Liferay column types.
 */
public class ExpandoColumnTypeUtils {
    /**
     * Return the numeric tag for a String type label.
     * 
     * @param typeLabel the type label (see ExpandoColumnConstants source)
     * @return the numeric id, 0 if not found
     */
    public static int typeLabelToInt(String typeLabel) {
		typeLabel = typeLabel.toUpperCase();
		boolean isArray = typeLabel.endsWith("_ARRAY");
	
		if (typeLabel.startsWith("BOOLEAN_"))
		    return (isArray) ? ExpandoColumnConstants.BOOLEAN_ARRAY : ExpandoColumnConstants.BOOLEAN;
	
		if (typeLabel.startsWith("DATE_"))
		    return (isArray) ? ExpandoColumnConstants.DATE_ARRAY : ExpandoColumnConstants.DATE;
	
		if (typeLabel.startsWith("DOUBLE_"))
		    return (isArray) ? ExpandoColumnConstants.DOUBLE_ARRAY : ExpandoColumnConstants.DOUBLE;
	
		if (typeLabel.startsWith("FLOAT_"))
		    return (isArray) ? ExpandoColumnConstants.FLOAT_ARRAY : ExpandoColumnConstants.FLOAT;
	
		if (typeLabel.startsWith("INTEGER_"))
		    return (isArray) ? ExpandoColumnConstants.INTEGER_ARRAY : ExpandoColumnConstants.INTEGER;
	
		if (typeLabel.startsWith("LONG_"))
		    return (isArray) ? ExpandoColumnConstants.LONG_ARRAY : ExpandoColumnConstants.LONG;
	
		if (typeLabel.startsWith("SHORT_"))
		    return (isArray) ? ExpandoColumnConstants.SHORT_ARRAY : ExpandoColumnConstants.SHORT;
	
		if (typeLabel.startsWith("STRING_"))
		    return (isArray) ? ExpandoColumnConstants.STRING_ARRAY : ExpandoColumnConstants.STRING;
	
		return 0;
    }

    /**
     * Return the type label given the numeric id.
     * 
     * @param id the id of the type
     * @return the type label
     */
    public static String intToTypeLabel(int id) {
		return ExpandoColumnConstants.getTypeLabel(id);
    }
}
