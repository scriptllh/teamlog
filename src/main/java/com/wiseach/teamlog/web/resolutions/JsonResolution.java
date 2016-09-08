package com.wiseach.teamlog.web.resolutions;

import com.google.gson.Gson;
import net.sourceforge.stripes.action.Resolution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Arlen Tan
 * 12-8-17 上午9:57
 */
public class JsonResolution<T> implements Resolution {
    private T data;
    public JsonResolution(T data) {
        this.data = data;
    }


    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json");
        response.getWriter().append(new Gson().toJson(data));
        response.flushBuffer();
    }
}
