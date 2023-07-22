package codingrecipe.codingrecipe.controller;

import codingrecipe.codingrecipe.dto.MemberDto;
import codingrecipe.codingrecipe.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    // 회원가입 페이지 출력 요청
    @GetMapping("/save")
    public String saveForm(@ModelAttribute MemberDto memberDto) {
        return "save";
    }

    // 회원가입 페이지 출력 요청
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDto memberDto) {
        memberService.join(memberDto);
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDto memberDto, BindingResult bindingResult, HttpServletRequest request) {

        MemberDto loginResult = memberService.login(memberDto);

        if(loginResult != null) {
            // login 성공
            HttpSession session = request.getSession();
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        } else {
            return "login";
        }
    }

    @GetMapping
    public String findAll(Model model) {

        List<MemberDto> memberDtos = memberService.findAll();
        model.addAttribute("memberList", memberDtos);

        return "list";
    }


    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        MemberDto memberDto = memberService.findById(id);
        model.addAttribute("member", memberDto);
        return "detail";
    }

    @GetMapping("/update")
    public String updateForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDto memberDto = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDto);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDto memberDto) {
        memberService.update(memberDto);
        return "redirect:/member/" + memberDto.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "index";
    }

    @PostMapping("/email-check")
    @ResponseBody
    public String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        return memberService.checkEmail(memberEmail) ? "사용할 수 없습니다." : "사용할 수 있습니다.";
    }
}
