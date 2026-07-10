package com.Spark.AnimeAPI.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Titles{
    @JsonProperty("en")
    public String en;

    public String en_jp;
    public String en_us;
    public String ja_jp;
}