package ru.k0r0tk0ff.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.k0r0tk0ff.web.support.CachedBodyHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Специальный фильтр, позволяющий логировать тело POST запросов
 *
 * TODO: "/book-rest-service/api/author" убрать в константы
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "ContentCachingFilter", urlPatterns = "/book-rest-service/api/author")
@Slf4j
public class ContentCachingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(httpServletRequest);
        if (httpServletRequest.getMethod().equalsIgnoreCase("post")) {
            StringBuilder textBuilder = getRawDataFromRequest(wrappedRequest);
            saveRequestBodyToLog(wrappedRequest, textBuilder);
        }
        filterChain.doFilter(wrappedRequest, httpServletResponse);
    }

    private void saveRequestBodyToLog(CachedBodyHttpServletRequest wrappedRequest, StringBuilder textBuilder) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object object = mapper.readValue(textBuilder.toString(), Object.class);
        String pretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);

        log.info("[filter preprocess] rest request: method=[{}] url=[{}] payload=\n{}", wrappedRequest.getMethod(), wrappedRequest.getRequestURI(), pretty);
    }

    private StringBuilder getRawDataFromRequest(CachedBodyHttpServletRequest wrappedRequest) throws IOException {
        int c;
        StringBuilder textBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                wrappedRequest.getInputStream(), Charset.forName(StandardCharsets.UTF_8.name())));
        while ((c = bufferedReader.read()) != -1) {
            textBuilder.append((char) c);
        }

        return textBuilder;
    }
}
