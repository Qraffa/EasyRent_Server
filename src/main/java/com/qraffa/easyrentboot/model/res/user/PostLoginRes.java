package com.qraffa.easyrentboot.model.res.user;

import com.qraffa.easyrentboot.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLoginRes {

    private User user;
    private String token;
}
