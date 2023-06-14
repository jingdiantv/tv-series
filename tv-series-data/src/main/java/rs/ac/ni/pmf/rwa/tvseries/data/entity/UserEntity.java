package rs.ac.ni.pmf.rwa.tvseries.data.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
