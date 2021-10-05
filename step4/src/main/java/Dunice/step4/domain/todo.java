package Dunice.step4.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "content")
public class todo {

    private String text;
    private String tag;




}
