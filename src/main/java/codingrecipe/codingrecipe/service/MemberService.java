package codingrecipe.codingrecipe.service;

import codingrecipe.codingrecipe.dto.MemberDto;
import codingrecipe.codingrecipe.entity.MemberEntity;
import codingrecipe.codingrecipe.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public void join(MemberDto memberDto) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDto);
        memberRepository.save(memberEntity);
    }

    public MemberDto login(MemberDto memberDto) {
        /*
        * 1. 회원이 입력한 이메일로 DB에서 조회를 함
        * 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
        * */
        Optional<MemberEntity> findMemberEmail = memberRepository.findMemberEntityByMemberEmail(memberDto.getMemberEmail());
        if(findMemberEmail.isPresent()) {
            // 조회 결과가 있다
            MemberEntity memberEntity = findMemberEmail.get();
            if(memberEntity.getMemberPassword().equals(memberDto.getMemberPassword())) {
                // 비밀번호 일치
                // entity -> dto 변환 후 리턴
                return MemberDto.toMemberDto(memberEntity);
            } else {
                // 비밀번호 불일치
                return null;
            }
        } else {
            // 조회 결과가 없다
            return null;
        }
    }

    public List<MemberDto> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(m -> new MemberDto(m.getId(), m.getMemberEmail()
                        , m.getMemberPassword(), m.getMemberName())).toList();
    }

    public MemberDto findById(Long id) {
        Optional<MemberEntity> member = memberRepository.findById(id);
        return member.map(MemberDto::toMemberDto).orElse(null);
    }

    public MemberDto updateForm(String myEmail) {
        Optional<MemberEntity> member = memberRepository.findMemberEntityByMemberEmail(myEmail);
        return member.map(MemberDto::toMemberDto).orElse(null);
    }

    @Transactional
    public void update(MemberDto memberDto) {
        Optional<MemberEntity> member = memberRepository.findMemberEntityByMemberEmail(memberDto.getMemberEmail());

        member.ifPresent(memberEntity -> memberEntity.updateMemberEntity(memberDto));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public boolean checkEmail(String memberEmail) {
        return memberRepository.findMemberEntityByMemberEmail(memberEmail).isPresent();
    }
}
