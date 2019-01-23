package com.aurea.restapi.common.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {

    @JsonView(Projections.Identifier.class)
    private Long id;

    @JsonView(Projections.Name.class)
    private String name;

    @JsonView({Projections.Name.class, Projections.Details.class})
    private String address;

    public static class Projections {

        public interface Identifier {

        }

        public interface Name extends Identifier {

        }

        public interface Details extends Identifier {

        }
    }
}
