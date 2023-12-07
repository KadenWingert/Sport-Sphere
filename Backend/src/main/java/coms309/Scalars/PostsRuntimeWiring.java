package coms309.Scalars;

import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;
import graphql.schema.idl.RuntimeWiring;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostsRuntimeWiring implements RuntimeWiringConfigurer {
    
    /** 
     * @param builder
     */
    // private final DataFetchers dataFetchers;

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder
                //...
                .scalar(Scalars.localDateTimeType())
                //...
                .build();
    }
}