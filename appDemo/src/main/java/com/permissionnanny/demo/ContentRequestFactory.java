package com.permissionnanny.demo;

import com.permissionnanny.lib.request.content.ContentRequest;

/**
 *
 */
public interface ContentRequestFactory extends RequestFactory {
    ContentRequest getRequest(int position);
}
