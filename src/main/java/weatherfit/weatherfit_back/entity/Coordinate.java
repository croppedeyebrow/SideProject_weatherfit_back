package weatherfit.weatherfit_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "coordinate")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Coordinate {
    @Id
    @Column(name = "coordinate_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tpo", nullable = false)
    private String tpo;

    @Column(name = "coordinateImg", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'default.jpg'")
    private String coordinateImg;


    @Column(name = "targetAgeGroup", nullable = false)
    private String targetAgeGroup;
 
    @Column(name ="preference", nullable = false)
    private String preference;



    @Builder
    public Coordinate(String tpo, String coordinateImg, String targetAgeGroup, String preference) {
        this.tpo = tpo;
        this.coordinateImg = coordinateImg;
        this.targetAgeGroup = targetAgeGroup;
        this.preference = preference;
    }

    
   
}

