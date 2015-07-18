package org.lyeung.elwood.common.conveter;

import org.lyeung.elwood.common.SystemException;

/**
 * Created by lyeung on 18/07/2015.
 */
public class TypeConverterException extends SystemException {

    public TypeConverterException(String message) {
        super(message);
    }

    public TypeConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}
