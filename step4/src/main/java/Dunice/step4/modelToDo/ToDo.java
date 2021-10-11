package Dunice.step4.modelToDo;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "content")
@DynamicUpdate

public class ToDo {
    public interface New{}
    public interface Exist{}
    public interface Update extends Exist{}
    public interface UpdateText extends Exist{}
    public interface UpdateStatus extends Exist{}

    @Null(groups = {ToDo.New.class}, message = "Id is generated automatically")
    @NotBlank
    @NotNull(groups = {ToDo.Update.class, ToDo.UpdateText.class, ToDo.UpdateStatus.class}, message = "Id is mandatory")
    @Id
    @Column(name="id" )
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Null(groups = {ToDo.UpdateStatus.class})
    @NotBlank
    @NotNull(groups = {ToDo.New.class, ToDo.UpdateText.class},message = "Text is mandatory")
    @Column(name="text")
    private String text;
    @NotNull(groups = {ToDo.UpdateStatus.class},message = "Status is mandatory")
    @Null(groups = {ToDo.UpdateText.class, ToDo.New.class})
    @Column(name="status")
    private boolean status;
    @Null(groups = {ToDo.UpdateText.class, ToDo.UpdateStatus.class, ToDo.New.class})
    @Column(name="created_At")
    private String createdAt;
    @Null(groups = {ToDo.UpdateText.class, ToDo.UpdateStatus.class, ToDo.New.class})
    @Column(name="updated_At")
    private String updatedAt;
}
