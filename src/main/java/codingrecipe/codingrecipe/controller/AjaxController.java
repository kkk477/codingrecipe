package codingrecipe.codingrecipe.controller;

import codingrecipe.codingrecipe.dto.AjaxDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AjaxController {

    @GetMapping("/ex01")
    public String ex01() {
        System.out.println("AjaxController.ex01");
        return "index";
    }

    @PostMapping("/ex02")
    public @ResponseBody String ex02() {
        System.out.println("AjaxController.ex02");
        return "안녕하세요";
    }

    @GetMapping("/ex03")
    public @ResponseBody String ex03(@RequestParam("param1") String param1,
                                     @RequestParam("param2") String param2) {
        System.out.println("param1 = " + param1);
        System.out.println("param2 = " + param2);

        return "ex03메서드 호출 완료";
    }

    @PostMapping("/ex04")
    public @ResponseBody String ex04(@RequestParam("param1") String param1,
                                     @RequestParam("param2") String param2) {
        System.out.println("param1 = " + param1);
        System.out.println("param2 = " + param2);

        return "ex04메서드 호출 완료";
    }

    @GetMapping("/ex05")
    public @ResponseBody AjaxDto ex05(@ModelAttribute AjaxDto ajaxDto) {
        System.out.println("ajaxDto = " + ajaxDto);

        return ajaxDto;
    }

    @PostMapping("/ex06")
    public @ResponseBody AjaxDto ex06(@ModelAttribute AjaxDto ajaxDto) {
        System.out.println("ajaxDto = " + ajaxDto);

        return ajaxDto;
    }

    @PostMapping("/ex07")
    public @ResponseBody AjaxDto ex07(@RequestBody AjaxDto ajaxDto) {
        System.out.println("ajaxDto = " + ajaxDto);

        return ajaxDto;
    }

    private List<AjaxDto> DTOList() {
        List<AjaxDto> dtoList = new ArrayList<>();
        dtoList.add(new AjaxDto("data1", "data1"));
        dtoList.add(new AjaxDto("data2", "data2"));
        return dtoList;
    }

    @PostMapping("/ex08")
    public @ResponseBody List<AjaxDto> ex08(@RequestBody AjaxDto ajaxDto) {
        System.out.println("ajaxDto = " + ajaxDto);
        List<AjaxDto> dtoList = DTOList();
        dtoList.add(ajaxDto);

        return dtoList;
    }

    @PostMapping("/ex09")
    public ResponseEntity<?> ex09(@RequestBody AjaxDto ajaxDto) {
        System.out.println("ajaxDto = " + ajaxDto);

        return new ResponseEntity<>(ajaxDto, HttpStatus.OK);
    }

    @PostMapping("/ex10")
    public ResponseEntity<?> ex10(@RequestBody AjaxDto ajaxDto) {
        System.out.println("ajaxDto = " + ajaxDto);
        List<AjaxDto> dtoList = DTOList();
        dtoList.add(ajaxDto);

        return ResponseEntity.ok().body(dtoList);
    }
}
