package com.Spark.AnimeAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Relationships{
    public Genres genres;
    public Categories categories;
    public Castings castings;
    public Installments installments;
    public Mappings mappings;
    public Reviews reviews;
    public MediaRelationships mediaRelationships;
    public Characters characters;
    public Staff staff;
    public Productions productions;
    public Quotes quotes;
    public Episodes episodes;
    public StreamingLinks streamingLinks;
    public AnimeProductions animeProductions;
    public AnimeCharacters animeCharacters;
    public AnimeStaff animeStaff;
}