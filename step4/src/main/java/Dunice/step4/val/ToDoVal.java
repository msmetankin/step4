package Dunice.step4.val;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToDoVal {

    public interface New {
    }

    public interface Exist {
    }

    public interface Update extends Exist {
    }

    @Null(groups = {New.class})
    @NotNull(groups = {Update.class})
    private int id;
    @NotNull(groups = {New.class, Update.class},message = "CreatedDate is mandatory")
    private String createdat;
    @NotBlank@NotNull(groups = {New.class, Update.class},message = "Text is mandatory")
    private String text;
    @NotNull(groups = {New.class, Update.class},message = "Status is mandatory")
    private boolean status;
    @NotNull(groups = {New.class, Update.class},message = "UpdatedDate is mandatory")
    private String updatedat;
}