package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lesson on 2017/10/6.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Integer id;
    private String name;
}
