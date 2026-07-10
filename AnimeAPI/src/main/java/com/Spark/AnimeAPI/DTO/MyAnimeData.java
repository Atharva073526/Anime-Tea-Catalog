package com.Spark.AnimeAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.runtime.ObjectMethods;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyAnimeData {

    private String posterImage;
    private String trailerUrl;
    private String title;
    private String description;
    private String rating;
}
