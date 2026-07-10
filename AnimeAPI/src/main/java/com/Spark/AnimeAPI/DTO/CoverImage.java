package com.Spark.AnimeAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoverImage{
    public String tiny;
    public String large;
    public String small;
    public String original;
    public Meta meta;
}