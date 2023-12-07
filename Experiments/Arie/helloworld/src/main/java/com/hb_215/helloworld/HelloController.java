package com.hb_215.helloworld;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



/*
 * 
@RestController. This is known as a stereotype annotation. 
It provides hints for people reading the code and for Spring 
that the class plays a specific role. In this case, our class 
is a web @Controller, so Spring considers it when handling 
incoming web requests.

The @RequestMapping annotation provides “routing” information. 
It tells Spring that any HTTP request with the / path should 
be mapped to the home method. The @RestController annotation 
tells Spring to render the resulting string directly back to 
the caller.
 */

@RestController
@RequestMapping("/")
public class HelloController {

    String value = "some value";
    PostClass post;

    HelloController() {
        post = new PostClass();
    }

    
    /** 
     * @return String
     */
    @GetMapping("/")
    public String welcome() {
        return "Hello World! Welcome to Arie's springboot app";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        System.out.println("name is: " + name);
        return "Hello World! Welcome: " + name;
    }

    // Get
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/welcome")
    public String bigWelcome() {
        return "<html>\n" + //
                "<head><title>Home Page</title></head>\n" + //
                "<body>\n" + //
                "<h1>Hello World!</h1>\n" +
                "<p>Welcome to this springboot app</span></p>\n" + //
                "</body>\n" + //
                "</html>";
    }

    @GetMapping("/getPost")
    public PostClass getPost() {
        System.out.println(post);
        return post;
    }

    // Post
    @PostMapping(value = "/post", 
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody String postDemo(@RequestBody PostClass newPost) {
        this.post = newPost;
        return "Created new Post";
    }
}
