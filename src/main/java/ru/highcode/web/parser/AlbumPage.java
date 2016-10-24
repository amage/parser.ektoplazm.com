package ru.highcode.web.parser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.playstat.crawler.Page;
import org.playstat.crawler.Selector;

@Page("{0}")
public class AlbumPage {
    @Selector(".post > h1 > a")
    private String title;

    @Selector(value = ".dll > a[href$=MP3.zip]", attr = "href")
    public String hrefMp3;

    public String getMP3Url() {
        return hrefMp3;
    }

    public String getMP3FileName() {
        try {
            return URLDecoder.decode(hrefMp3.substring("http://www.ektoplazm.com/files/".length()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return title + ".zip";
        }
    }

    @Override
    public String toString() {
        return "Album: " + title + " url: " + hrefMp3;
    }
}
