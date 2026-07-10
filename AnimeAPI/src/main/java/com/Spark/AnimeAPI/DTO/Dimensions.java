package com.Spark.AnimeAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dimensions{
    public Tiny tiny;
    public Large large;
    public Small small;
    public Medium medium;
}