package by.test

import org.springframework.test.util.ReflectionTestUtils

import com.by.exception.CardNotValidException
import com.by.json.MemberDetailJson
import com.by.model.Card
import com.by.model.CardRule
import com.by.model.Member
import com.by.model.MemberDetail
import com.by.repository.MemberRepository
import com.by.service.CardService
import com.by.service.MemberService
import com.by.service.impl.MemberDetailServiceImpl
import com.by.service.impl.MemberServiceImpl
import com.by.typeEnum.ValidEnum

class MemberTest extends spock.lang.Specification{

	def "用户注册有效的卡"(){
		given: "一个新用户"
		Member member=new Member()

		and:"a validcard"
		Card card=new Card()
		card.setValid(ValidEnum.VALID)
		member.setCard(card)

		and: "a cardService"
		CardService cardService=Stub(CardService)
		cardService.findByIdAndValid(_,_)>>card

		and:"a memberService"
		MemberRepository repository=Mock(MemberRepository)
		MemberServiceImpl service=new MemberServiceImpl()
		ReflectionTestUtils.setField(service,"repository",repository)
		ReflectionTestUtils.setField(service,"cardService",cardService)

		when: "member signup"
		service.save(member)

		then: ""
		1* repository.save(member)
	}

	def "用户注册无效的卡"(){
		given: "一个新用户"
		Member member=new Member()

		and:"a validcard"
		Card card=new Card()
		card.setValid(ValidEnum.INVALID)
		member.setCard(card)

		and: "a cardService"
		CardService cardService=Stub(CardService)
		cardService.findByIdAndValid(_,_)>>null

		and:"a memberService"
		MemberRepository repository=Mock(MemberRepository)
		MemberServiceImpl service=new MemberServiceImpl()
		ReflectionTestUtils.setField(service,"repository",repository)
		ReflectionTestUtils.setField(service,"cardService",cardService)

		when: "member signup"
		service.save(member)

		then: ""
		thrown(CardNotValidException)
	}

	def "长期有效规则以及范围内规则"(){
		given: "两个规则，在时间范围内的规则1，长期有效规则2"
		def beginTime=Calendar.getInstance();
		def endTime=Calendar.getInstance();
		beginTime.set(2016,1,1)
		endTime.set(2016,12,31)

		def rule1=new CardRule()
		rule1.with {
			setValid(ValidEnum.VALID)
			setScore(10)
			setBeginTime(beginTime)
			setEndTime(endTime)
		}
		def rule2=new CardRule()
		rule2.with{
			setValid(ValidEnum.VALID)
			setScore(5)
		}
		def rules=[rule1, rule2]

		expect: "采用规则2的规则"
		Card.getMaxScore(rules)==10;
	}


	def "update member password"(){
		given:"member repository"
		MemberRepository repository=Mock(MemberRepository)

		and:"and a member"
		Member m=new Member();
		m.setMemberDetail(new MemberDetail());

		and: "memberDetail service"
		MemberDetailServiceImpl service=new MemberDetailServiceImpl()
		MemberService memberService =Stub(MemberService)
		memberService.findOne(_)>>m
		ReflectionTestUtils.setField(service,"memberService",memberService)

		when:"member update password"
		MemberDetailJson json=new MemberDetailJson()
		json.setPassword("123")
		service.updatePassword(m.getId(),json)

		then: "assert member password change"
		m.getMemberDetail().getPassword()=="123"
	}
}
