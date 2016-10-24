package ru.highcode.web.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.playstat.agent.Transaction;
import org.playstat.crawler.DOPWrapper;
import org.playstat.crawler.WebClient;

public class EktoplasmMusic {
    private final WebClient web = new WebClient();

    public EktoplasmMusic() {
    }

    public static void main(String[] args) throws Exception {
        new EktoplasmMusic().run();
    }

    private void run() throws Exception {
        final DOPWrapper dop = new DOPWrapper(web);
        final AlbumListPage albumsListPage = dop.get(AlbumListPage.class, "0");
        final List<String> albumUrls = new ArrayList<>();
        albumUrls.addAll(albumsListPage.getAlbumsUrlList());

        for (int page = 1; page <= albumsListPage.getLastPage(); page++) {
            albumUrls.addAll(dop.get(AlbumListPage.class, String.valueOf(page)).getAlbumsUrlList());
            System.out.println("Page: " + page + "/" + albumsListPage.getLastPage());
        }
        List<AlbumPage> albums = albumUrls.parallelStream().map(url -> dop.get(AlbumPage.class, url))
                .peek(System.out::println).collect(Collectors.toList());
        albums.parallelStream().forEach(album -> {
            final String outFileName = new File(album.getMP3FileName()).getAbsolutePath();
            try {
                web.download(Transaction.create(album.getMP3Url()), outFileName);
                System.out.println(album.getMP3FileName()  + " downloaded.");
            } catch (Exception e) {
                if (new File(outFileName).exists()) {
                    new File(outFileName).delete();
                }
                e.printStackTrace();
            }
        });
        System.out.println("Total: " + albums.size());
    }
}
