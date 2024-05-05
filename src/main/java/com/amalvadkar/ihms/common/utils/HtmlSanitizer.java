package com.amalvadkar.ihms.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

@Slf4j
public class HtmlSanitizer {

    private HtmlSanitizer() {
        throw new IllegalStateException("You can't create object for HtmlSanitizer utility class");
    }

    public static String sanitize(String unsafeHtml) {
        String safeHtml = Jsoup.clean(unsafeHtml, Safelist.relaxed());
        log.info("Html sanitized successfully");
        return safeHtml;
    }

}
