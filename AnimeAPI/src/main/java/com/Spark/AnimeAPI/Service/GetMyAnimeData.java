package com.Spark.AnimeAPI.Service;

import com.Spark.AnimeAPI.DTO.Datum;
import com.Spark.AnimeAPI.DTO.MyAnimeData;
import com.Spark.AnimeAPI.DTO.Root;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetMyAnimeData {

    @Value("${anime.api.url}")
    private String url;

    RestTemplate template = new RestTemplate();

    public List<MyAnimeData> myAnimeData(String title) {
        String apiUrl = url + "?filter[text]=" + title;

//        Root root = template.getForObject(apiUrl, Root.class); //header issue

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.api+json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Root> response = template.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                Root.class
        );

        System.out.println(response.getBody());
        System.out.println(apiUrl);

        Root root = response.getBody();


        MyAnimeData myAnimeData = new MyAnimeData();

        String[] tileComponent = title.toLowerCase().split("\\s");

        List<Datum> datumList = root.data.stream().filter(d-> d.getAttributes().getTitles().getEn()!=null)
                .filter(
                        d -> {
                    System.out.println(d.getAttributes().getTitles().getEn());
                    String ogTitle = d.getAttributes().getTitles().getEn().toLowerCase();
                    return Arrays.stream(tileComponent).allMatch(ogTitle::contains);
                }
        ).collect(Collectors.toList());

        List<MyAnimeData> myAnimeDataList = new ArrayList<>();

        for (Datum datum : datumList)
        {
            MyAnimeData o1 = new MyAnimeData();

            o1.setTitle(datum.getAttributes().getTitles().getEn());
            o1.setTrailerUrl("https://www.youtube.com/watch?v="+datum.getAttributes().getYoutubeVideoId());
            o1.setPosterImage(datum.attributes.getPosterImage().medium);
            o1.setDescription(datum.attributes.getDescription());

            try {
                o1.setRating(String.format("%.2f",Float.parseFloat(datum.getAttributes().getAverageRating()) / 10) + "/10");

            }
            catch (Exception e) {
                o1.setRating("N/A");
            }

            myAnimeDataList.add(o1);
        }

        return myAnimeDataList;

    }

    public Set<MyAnimeData> getRandom()
    {
        String apiUrl = url;

//        Root root = template.getForObject(apiUrl, Root.class); //header issue

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.api+json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Root> response = template.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                Root.class
        );

        Root root = response.getBody();

        List<Datum> list = new ArrayList<>(root.getData());
        Collections.shuffle(list);

        Set<MyAnimeData> randomList = new HashSet<>();

        for (Datum datum : list) {

            MyAnimeData anime = new MyAnimeData();

            anime.setPosterImage(datum.getAttributes().getPosterImage().medium);
            anime.setTrailerUrl("https://www.youtube.com/watch?v=" +
                    datum.getAttributes().getYoutubeVideoId());
            anime.setTitle(datum.getAttributes().canonicalTitle);
            anime.setDescription(datum.getAttributes().getDescription());

            try {
                anime.setRating(String.format("%.2f",Float.parseFloat(datum.getAttributes().getAverageRating()) / 10) + "/10");
            } catch (Exception e) {
                anime.setRating("N/A");
            }

            randomList.add(anime);

            if (randomList.size() == 10)
                break;
        }

        return randomList;
    }

}
