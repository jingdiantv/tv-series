package rs.ac.ni.pmf.rwa.tvseries.data.entity;

import lombok.*;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "users")
/* ukoliko tabelu nazovemo user dodje do greske jer je user kljucna rec u h2 bazi*/
public class UserEntity {
    @Id
    String username;
    String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    List<WatchListEntity> watchedTvSeries;



}
