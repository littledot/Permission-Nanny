package com.permissionnanny.lib.request.simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.permissionnanny.lib.request.simple.SimpleRequest;

/**
 * Listener to attach to {@link SimpleRequest}s to receive responses from Permission Nanny.
 */
public interface SimpleListener {
    /**
     * Callback when Permission Nanny returns a response.
     *
     * @param response Response from Permission Nanny about the request
     */
    void onResponse(@NonNull Bundle response);
}
