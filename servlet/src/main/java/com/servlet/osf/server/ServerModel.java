package com.servlet.osf.server;

import com.servlet.exception.OSFException;
import com.servlet.osf.OSFContext;

import javax.servlet.ServletConfig;
import java.io.InputStream;
import java.io.OutputStream;

public interface ServerModel {

    void load(ServletConfig config) throws OSFException;

    void service(InputStream in, OutputStream out, OSFContext context) throws OSFException;

    void distroy() throws OSFException;
}
