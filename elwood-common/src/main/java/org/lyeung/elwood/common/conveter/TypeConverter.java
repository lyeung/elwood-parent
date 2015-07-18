package org.lyeung.elwood.common.conveter;

/**
 * Created by lyeung on 18/07/2015.
 */
public interface TypeConverter<IN, OUT> {

   OUT convert(IN in);
}
