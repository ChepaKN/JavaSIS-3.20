package pro.sisit.unit9.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String address;
}
