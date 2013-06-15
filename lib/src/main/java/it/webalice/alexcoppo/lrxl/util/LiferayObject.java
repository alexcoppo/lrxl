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
package it.webalice.alexcoppo.lrxl.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.service.ClassNameLocalServiceUtil;

/**
 * A wrapper upon the class id/instance id pair.
 */
public class LiferayObject {
    private long classId;
    private long instanceId;

    /**
     * Create a null LiferayObject.
     */
    public LiferayObject() {
        this(0, 0);
    }

    /**
     * 
     * @param clazz
     * @param instanceId
     */
    public LiferayObject(Class clazz, long instanceId) {
        this(ClassNameLocalServiceUtil.fetchClassNameId(clazz), instanceId);
    }

    /**
     * 
     * @param className
     * @param instanceId
     */
    public LiferayObject(String className, long instanceId) {
        this(ClassNameLocalServiceUtil.fetchClassNameId(className), instanceId);
    }

    /**
     * 
     * @param classId
     * @param instanceId
     */
    public LiferayObject(long classId, long instanceId) {
        this.classId = classId;
        this.instanceId = instanceId;
    }

    /**
     * 
     * @return
     */
    public long getClassId() {
        return classId;
    }

    /**
     * 
     * @return
     */
    public String getClassName() {
        try {
            ClassName cn = ClassNameLocalServiceUtil.fetchClassName(classId);
            return cn.getClassName();
        } catch (SystemException ex) {
            return "";
        }
    }

    /**
     * 
     * @return
     */
    public long getInstanceId() {
    	return instanceId;
    }

    /**
     * 
     * @param lo
     * @return
     */
    public static boolean isNull(LiferayObject lo) {
    	return lo.classId == 0 || lo.instanceId == 0;
    }

    /**
     * Check whether two LiferayObjects wrap entities with same Java class.
     * 
     * @param lo1
     * @param lo2
     * @return the result of the comparison
     */
    public static boolean sameClass(LiferayObject lo1, LiferayObject lo2) {
        return lo1.classId == lo2.classId;
    }

    /**
     * Check whether two LiferayObjects wrap the same entity.
     * 
     * @param lo1
     * @param lo2
     * @return the result of the comparison
     */
    public static boolean areEqual(LiferayObject lo1, LiferayObject lo2) {
        return sameClass(lo1, lo2) && (lo1.instanceId == lo1.instanceId);
    }
}
