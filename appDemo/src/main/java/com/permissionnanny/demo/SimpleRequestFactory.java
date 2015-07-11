package com.permissionnanny.demo;

import com.permissionnanny.lib.request.simple.SimpleRequest;

/**
 *
 */
public interface SimpleRequestFactory extends RequestFactory {
    SimpleRequest getRequest(int position);
}
