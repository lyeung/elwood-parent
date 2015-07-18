package org.lyeung.elwood.common.conveter.impl;

import org.lyeung.elwood.common.EncodingConstants;
import org.lyeung.elwood.common.conveter.TypeConverter;
import org.lyeung.elwood.common.conveter.TypeConverterException;

import java.io.UnsupportedEncodingException;

/**
 * Created by lyeung on 18/07/2015.
 */
public class ByteArrayConverterImpl implements TypeConverter<char[], byte[]> {

    @Override
    public byte[] convert(char[] chars) {
        try {
            return new String(chars).getBytes(EncodingConstants.UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new TypeConverterException("unable to convert char array to byte array");
        }
    }
}
