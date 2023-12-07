package coms309.ServerManager;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerManager {

    private String logFileName;
    private String logFileLocation;

    public Resource getLogFileResource() {
    return new ClassPathResource("/home/arie/Develop/COMS_309/hb_215/Backend/src/main/resources/backend_logs.txt");
}
}
