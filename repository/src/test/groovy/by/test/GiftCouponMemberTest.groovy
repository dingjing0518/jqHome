package by.test

import org.springframework.test.util.ReflectionTestUtils

import spock.lang.Specification

import com.by.exception.NotValidException
import com.by.model.GiftCoupon
import com.by.model.GiftCouponMember
import com.by.model.Member
import com.by.model.Sequence
import com.by.repository.GiftCouponMemberRepository
import com.by.service.GiftCouponService
import com.by.service.MemberService
import com.by.service.SequenceService
import com.by.service.impl.GiftCouponMemberServiceImpl
import com.by.service.impl.GiftCouponServiceImpl
import com.by.service.impl.MemberServiceImpl
import com.by.typeEnum.DuplicateEnum
import com.by.typeEnum.ScoreHistoryEnum
import com.by.typeEnum.ValidEnum


class GiftCouponMemberTest extends Specification {
	def "duplicateExchange"(){
		given:"a giftCoupon"
		def GiftCoupon coupon=new GiftCoupon(1)
		coupon.with{
			setDuplicate(DuplicateEnum.ISDUPLICATE)
			setAmount(10)
			setScore(1)
			setValid(ValidEnum.VALID)
			setTotal(0)
		}

		and: "a member"
		def member=new Member()
		member.with{ setScore(100) }

		and: "a memberService"
		MemberService memberService=Mock(MemberServiceImpl)
		memberService.findOne(_)>>member
		memberService.minusScore(_,_,_,_)>>{ Member m,int total,String reason,ScoreHistoryEnum e->
			member.setScore(member.getScore()-total)
		}

		and: "a gitcouponService"
		GiftCouponService giftCouponService=Mock(GiftCouponServiceImpl)
		giftCouponService.findOne(_)>>coupon

		and: "a giftCouponMember"
		GiftCouponMember couponMember=new GiftCouponMember(member,coupon)

		and: "a GiftCouponMemberRepository "
		GiftCouponMemberRepository repository=Mock(GiftCouponMemberRepository )
		repository.findByCouponAndMember(_,_)>>[couponMember]

		and: "a sequenceService"
		SequenceService sequenceService=Mock(SequenceService)
		Sequence s=Mock(Sequence)
		s.getId()>>1
		sequenceService.save(_)>>s

		and:"a GiftCouponMemberService"
		GiftCouponMemberServiceImpl impl=new GiftCouponMemberServiceImpl()
		ReflectionTestUtils.setField(impl,"giftCouponService",giftCouponService)
		ReflectionTestUtils.setField(impl,"memberService",memberService)
		ReflectionTestUtils.setField(impl,"repository",repository)
		ReflectionTestUtils.setField(impl,"sequenceService",sequenceService)

		when:"user exchange coupon"
		impl.exchangeCoupon(coupon,member,total)

		then:""
		member.getScore()==totalScore

		where:""
		total||totalScore
		1    ||99
		2    ||98
	}

	def "exchange an invalid coupon throw invalid exception"(){
		given: "an invalid giftCoupon"
		GiftCoupon coupon=new GiftCoupon()
		coupon.with { setValid(ValidEnum.INVALID) }

		and: "a member"
		Member member=new Member()

		and: "a memberService"
		MemberService memberService=Stub(MemberService)
		memberService.findOne(_)>>member

		and: "a giftCouponService"
		GiftCouponService giftCouponService=Stub(GiftCouponService)
		giftCouponService.findOne(_)>>coupon

		and: "a GiftCouponMemberRepository "
		GiftCouponMemberRepository repository=Mock(GiftCouponMemberRepository)
		repository.findByCouponAndMember(_,_)>>[]

		when: "member exchange invalid coupon"
		GiftCouponMemberServiceImpl impl=new GiftCouponMemberServiceImpl()
		ReflectionTestUtils.setField(impl,"giftCouponService",giftCouponService)
		ReflectionTestUtils.setField(impl,"memberService",memberService)
		ReflectionTestUtils.setField(impl,"repository",repository)
		impl.exchangeCoupon(coupon,member,1)

		then: "throw exception"
		thrown(NotValidException)
	}
}
