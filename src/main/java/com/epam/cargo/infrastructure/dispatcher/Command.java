package com.epam.cargo.infrastructure.dispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    void execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException;
}
