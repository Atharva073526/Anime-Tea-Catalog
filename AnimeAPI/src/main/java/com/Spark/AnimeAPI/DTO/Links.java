package com.Spark.AnimeAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Links{
    public String self;
    public String related;
    public String first;
    public String next;
    public String last;
}