package coms309.Scalars;

import graphql.schema.GraphQLScalarType;

public class Scalars {

    /**
     * @return GraphQLScalarType
     */
    public static GraphQLScalarType localDateTimeType() {
        return GraphQLScalarType.newScalar().name("LocalDateTime").description("LocalDateTime type")
                .coercing(new LocalDateTimeScalar()).build();
    }
}
