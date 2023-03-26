package com.authenticationserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RedirectController {

    @RequestMapping("/**")
    public void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //make http request
            //set query string
        String queryString = request.getQueryString();
        if(queryString == null) queryString = "";
        else queryString = "?" + queryString;
            //make url
        URL url = new URL("http://localhost:12345" + request.getRequestURI() + queryString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //set logic server request from client request
        connection.setRequestMethod(request.getMethod());
        connection.setRequestProperty("Content-Type", request.getContentType());
        connection.setRequestProperty("Content-Length", String.valueOf(request.getContentLength()));
            //if client request method is POST
        if (request.getMethod().equals("POST")) {
            //set logic server request body
            connection.setDoOutput(true);
            String clientRequestBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
            connection.getOutputStream().write(clientRequestBody.getBytes("UTF-8"));
        }

        //set client response body from logic server request
        response.setStatus(connection.getResponseCode());
        if (connection.getResponseCode() < 400) { //if logic server response is success
            response.setContentType(connection.getContentType());
            response.setContentLength(connection.getContentLength());
            String clientResonseBody = StreamUtils.copyToString(connection.getInputStream(), StandardCharsets.UTF_8);
            response.getWriter().write(clientResonseBody);
        }
    }
}
