package coms309.ServerManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@EnableWebMvc
public class ServerManagerController implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations(
                "file:/home/arie/Develop/COMS_309/hb_215/Backend/src/main/resources/");
    }

    @GetMapping(value = "/logs", produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody Resource getText() throws IOException, URISyntaxException {

        ByteArrayResource resource = null;
        try {

            String fileName = "/home/target/backend_logs.log";
            Path path = Paths.get(fileName);

            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resource;
    }
}
