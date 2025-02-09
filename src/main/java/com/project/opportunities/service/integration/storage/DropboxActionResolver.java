package com.project.opportunities.service.integration.storage;

@FunctionalInterface
public interface DropboxActionResolver<T> {

    T perform() throws Exception;

}
