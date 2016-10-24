package ru.highcode.web.parser;

import java.util.List;

import org.playstat.crawler.Page;
import org.playstat.crawler.Selector;

@Page("http://www.ektoplazm.com/section/free-music/page/{0}")
public class AlbumListPage {
    @Selector(value = ".post > h1 > a", attr = "href")
    private List<String> albumsList;

    @Selector(value = "#main > div.navigation > div.alignleft > div > a.last", attr = "href")
    private String lastPageUrl;

    public int getLastPage() {
        return Integer.parseInt(lastPageUrl.substring(lastPageUrl.lastIndexOf('/') + 1));
    }

    public List<String> getAlbumsUrlList() {
        return albumsList;
    }

    @Override
    public String toString() {
        return "AlbumListPage [albumsList=" + albumsList + ", lastPage=" + lastPageUrl + "]";
    }
}
