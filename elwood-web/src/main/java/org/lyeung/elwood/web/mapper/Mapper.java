package org.lyeung.elwood.web.mapper;

/**
 * Created by lyeung on 4/08/2015.
 */
public interface Mapper<FROM, TO> {

    TO map(FROM from);

}
