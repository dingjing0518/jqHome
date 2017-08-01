package by.test

import org.springframework.test.util.ReflectionTestUtils

import spock.lang.Specification

import com.by.model.Member
import com.by.model.ScoreAddHistory
import com.by.repository.MemberRepository
import com.by.service.ScoreAddHistoryService
import com.by.service.ScoreHistoryService
import com.by.service.impl.MemberServiceImpl
import com.by.typeEnum.ScoreHistoryEnum

class MemberServiceSpec extends Specification {
	def "minus all score"(){
		given:"a member with 2000 score"
		Member member=new Member()
		member.with { setScore(2000) }

		and:"a list of scoreHistory"
		ScoreAddHistory scoreAddHistory1=new ScoreAddHistory()
		scoreAddHistory1.with{
			setMember(member)
			setTotal(500)
		}
		ScoreAddHistory scoreAddHistory2=new ScoreAddHistory()
		scoreAddHistory2.with{
			setMember(member)
			setTotal(1000)
		}
		ScoreAddHistory scoreAddHistory3=new ScoreAddHistory()
		scoreAddHistory3.with{
			setMember(member)
			setTotal(500)
		}
		def lists=[
			scoreAddHistory1,
			scoreAddHistory2,
			scoreAddHistory3
		]

		and:"a memberRepository"
		MemberRepository repository=Stub(MemberRepository)
		repository.findByName(_)>>member
		repository.findOne(_)>>member

		and: "a scoreaddhistory"
		ScoreAddHistoryService scoreAddHistoryService=Mock(ScoreAddHistoryService)
		scoreAddHistoryService.findByMember(_)>>lists

		and: "scoreHistoryService"
		ScoreHistoryService scoreHistoryService=Mock(ScoreHistoryService)

		when: "manual minus 1000 score"
		MemberServiceImpl impl=new MemberServiceImpl()
		ReflectionTestUtils.setField(impl,"repository",repository)
		ReflectionTestUtils.setField(impl,"scoreAddHistoryService",scoreAddHistoryService)
		ReflectionTestUtils.setField(impl,"scoreHistoryService",scoreHistoryService)
		Member m=impl.minusScoreAll(member,1000,"",ScoreHistoryEnum.ADMIN)

		then: ""
		0 * scoreAddHistoryService.deleteByMember(_)
		m.getScore()==1000
	}
}
