package com.aurea.restapi.common.rest;

import com.aurea.restapi.common.domain.User;
import com.aurea.restapi.common.domain.User.Projections;
import com.aurea.restapi.common.domain.User.Projections.Details;
import com.aurea.restapi.common.domain.User.Projections.Name;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/users")
@RestController
public class UserController {

    private static final String PROJECTION_PARAM = "projection";

    @GetMapping("/op1")
    public User op1() {
        return user();
    }

    @JsonView(Name.class)
    @GetMapping("/op2")
    public User op2() {
        return user();
    }

    @JsonView(Details.class)
    @GetMapping("/op3")
    public User op3() {
        return user();
    }

    @GetMapping("/op4")
    public MappingJacksonValue op4(
            @RequestParam(name = PROJECTION_PARAM, required = false) final String projectionName) {
        val result = new MappingJacksonValue(user());

        val viewClass = resolveProjection(projectionName);
        result.setSerializationView(viewClass);

        return result;
    }

    private User user() {
        return new User().setId(1L).setName("Boris Yakovito").setAddress("Minsk, Belarus");
    }

    /**
     * Some logic to resolve Class by projection value, here I simply use switch/case...
     *
     * Personally I prefer the Matrix solution made in https://github.com/trilogy-group/ignite-sensage-analyzer/pull/1312/files
     */
    private Class<?> resolveProjection(String projectionName) {
        if (projectionName != null) {
            switch (projectionName) {
                case "NAME":
                    return Name.class;
                case "DETAILS":
                    return Projections.Details.class;
                default:
                    return null;
            }
        }

        return null;
    }
}
