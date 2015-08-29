/*
 *
 *  Copyright (C) 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
