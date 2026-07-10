package com.Spark.AnimeAPI.Controller;

import com.Spark.AnimeAPI.DTO.MyAnimeData;
import com.Spark.AnimeAPI.Service.GetMyAnimeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/anime")
@CrossOrigin
public class AnimeAPIController {

    @Autowired
    GetMyAnimeData getMyAnimeData = new GetMyAnimeData();

    @GetMapping
    public List<MyAnimeData> getAnime(@RequestParam String title)
    {
        return getMyAnimeData.myAnimeData(title);
    }

    @GetMapping("/random")
    public Set<MyAnimeData> getRandom()
    {
        return getMyAnimeData.getRandom();
    }

}
