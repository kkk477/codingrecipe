package codingrecipe.codingrecipe.entity;

import codingrecipe.codingrecipe.dto.MemberDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "member")
public class MemberEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String memberEmail;

    @Column(unique = true)
    private String memberPassword;

    @Column(unique = true)
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDto memberDto) {
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setId(memberDto.getId());
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberPassword(memberDto.getMemberPassword());
        memberEntity.setMemberName(memberDto.getMemberName());

        return memberEntity;
    }

    public void updateMemberEntity(MemberDto memberDto) {
        this.memberEmail = memberDto.getMemberEmail();
        this.memberPassword = memberDto.getMemberPassword();
        this.memberName = memberDto.getMemberName();
    }
}
