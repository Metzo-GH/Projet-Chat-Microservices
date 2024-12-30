
package alom;

import jakarta.jws.WebService;

@WebService(endpointInterface = "alom.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        return "Hello " + text;
    }
}

