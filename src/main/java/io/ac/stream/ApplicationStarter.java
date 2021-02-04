package io.ac.stream;

import common.rest.RestResult;
import io.swagger.annotations.Api;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import java.util.Map;

@SpringBootApplication
//@EnableJpaAuditing
@EnableScheduling
@RestController
@Api("测试")
public class ApplicationStarter extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApplicationStarter.class);
    }

    CommandManager manager = new CommandManagerImpl();
//    @Component
    public class ApplicationRunnerImpl implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) {

            // -rtsp_transport tcp
            //测试多个任何同时执行和停止情况
            //默认方式发布任务
            manager.start("animation'", "ffmpeg -re  -rtsp_transport tcp -i rtsp://admin:HuaWei123@120.224.2.211:36690/LiveMedia/ch1/Media1  -f flv -r 15 -s 1920*1080 -an rtmp://124.70.137.103:1935/live/camera", true);
        }
    }

    @PostMapping("reboot")
    public RestResult listen(@RequestBody Map<String, String> map) {

        manager.stopAll();
        if (map != null && map.size() > 0) {

            map.forEach(manager::start);
        }
        return new RestResult();
    }

    @PostMapping("stop")
    public RestResult stop(@RequestBody Map<String, String> map) {

        for (String value : map.values()) {

            manager.stop(value);
        }
        return new RestResult();
    }

    @PostMapping("start")
    public RestResult add(@RequestBody Map<String, String> map) {

        if (map != null && map.size() > 0) {

            map.forEach(manager::start);
        }
        return new RestResult();
    }

    @PreDestroy
    public void destroy(){
        manager.stopAll();
    }
}
