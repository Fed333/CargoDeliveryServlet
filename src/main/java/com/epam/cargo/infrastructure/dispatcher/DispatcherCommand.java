package com.epam.cargo.infrastructure.dispatcher;

/**
 * The DispatcherCommand interface to dispatch http mapping between Commands in the infrastructure of servlet application.<br/>
 * This class dispatches requests considering url and http method.
 * The mean of obtaining corresponding Command to execute.
 * @since 05.03.2022
 * @see Command
 * @author Roman Kovalchuk
 * */
public interface DispatcherCommand {
    /**
     * Obtains Command object by corresponding mapping.
     * @param url http url of request.
     * @param method http method of request.
     * @return Command object to handle the http request.
     * */
    Command getCommand(String url, HttpMethod method);
}