package alom;

import jakarta.jws.WebService;

@WebService
public interface HelloWorld {
    String sayHi(String text);
}

