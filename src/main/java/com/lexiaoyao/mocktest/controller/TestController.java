package com.lexiaoyao.mocktest.controller;

import com.lexiaoyao.mocktest.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    //通过正则限时id只能为数字
    @GetMapping("/person/{id:\\d+}")
    public ResponseEntity getPerson(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(new Person(id, "tom", 22));
    }

    @PostMapping("/person")
    public ResponseEntity postPerson(@RequestBody Person person) {
        return ResponseEntity.ok(person);
    }

}
