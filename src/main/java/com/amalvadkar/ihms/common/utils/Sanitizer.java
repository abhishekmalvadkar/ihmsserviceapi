package com.amalvadkar.ihms.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.util.StringUtils;

import static java.util.Objects.requireNonNull;

@Slf4j
public class Sanitizer {

    public static String sanitizeHtml(String unsafeHtml){
        String safeHtml = Jsoup.clean(unsafeHtml, Safelist.relaxed());
        log.info("Html sanitized successfully");
        return safeHtml;
    }

    private Sanitizer(){
        throw new IllegalStateException("You can't create object for HtmlSanitizer utility class");
    }

    public static String sanitizeFileName(String unsafeFileName) {
        String safeFileName = StringUtils.cleanPath(requireNonNull(unsafeFileName));
        log.info("File name sanitized successfully");
        return safeFileName;
    }
}
