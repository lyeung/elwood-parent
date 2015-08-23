package org.lyeung.elwood.web.controller.runbuild;

/**
 * Created by lyeung on 13/08/2015.
 */
public class ContentResponse {

    public static ContentResponse EMPTY = new ContentResponse();

    private String content;

    private ContentResponse() {
        // do-nothing
    }

    public ContentResponse(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
