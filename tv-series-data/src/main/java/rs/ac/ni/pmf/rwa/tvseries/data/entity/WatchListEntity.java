package rs.ac.ni.pmf.rwa.tvseries.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "watchlist")
public class WatchListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    TvSeriesEntity tvSeries;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    UserEntity user;

    Integer rating;
    Integer episodesWatched;



}
