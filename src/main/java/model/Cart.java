package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private AutoUser user;

    @ManyToMany(fetch=FetchType.EAGER)
    private List<Confectionery> confectList = new ArrayList<>();
}
