package com.permissionnanny.demo;

import com.permissionnanny.lib.request.PermissionRequest;

/**
 *
 */
public interface SimpleRequestFactory extends RequestFactory {
    PermissionRequest getRequest(int position, DataAdapter adapter);
}
