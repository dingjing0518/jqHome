package by.test

import javax.persistence.EntityManager

import org.springframework.test.util.ReflectionTestUtils

import spock.lang.Specification

import com.by.model.Member
import com.by.model.ParkingCoupon
import com.by.model.ParkingCouponMember
import com.by.repository.ParkingCouponRepository
import com.by.service.ParkingCouponMemberService
import com.by.service.impl.ParkingCouponServiceImpl

class ParkingCouponServiceSpec extends Specification {
	def "parkingCouponMember findWhich to delete"(){
		given:"a parking coupon with total 1"
		ParkingCouponMember pcm1=new ParkingCouponMember();
		pcm1.with {
			setMember(new Member(1l))
			setCoupon(new ParkingCoupon(1))
			setTotal(1)
		}
		
		and:"a parking coupon with total 3"
		ParkingCouponMember pcm2=new ParkingCouponMember();
		pcm2.with {
			setMember(new Member(1l))
			setCoupon(new ParkingCoupon(2))
			setTotal(3)
		}

		and:"a parking coupon with total 2"
		ParkingCouponMember pcm3=new ParkingCouponMember();
		pcm3.with {
			setMember(new Member(1l))
			setCoupon(new ParkingCoupon(3))
			setTotal(2)
		}
		
		when:"parking couponMember findwhichtodelete"
		def lists=[pcm1,pcm2,pcm3]
		
		then:""
		[pcm1]==ParkingCouponMember.findWhichToDelete(1,lists)
		[pcm1,pcm2]==ParkingCouponMember.findWhichToDelete(2,lists)
		[pcm1,pcm2]==ParkingCouponMember.findWhichToDelete(3,lists)
		[pcm1,pcm2,pcm3]==ParkingCouponMember.findWhichToDelete(5,lists)
	}

	def "parkingCouponservice deleteresults"(){
		given:"a parking coupon with total 1"
		ParkingCouponMember pcm1=new ParkingCouponMember();
		pcm1.with {
			setMember(new Member(1l))
			setCoupon(new ParkingCoupon(1))
			setTotal(1)
		}
		
		and:"a parking coupon with total 3"
		ParkingCouponMember pcm2=new ParkingCouponMember();
		pcm2.with {
			setMember(new Member(1l))
			setCoupon(new ParkingCoupon(2))
			setTotal(3)
		}

		and:"a parking coupon with total 2"
		ParkingCouponMember pcm3=new ParkingCouponMember();
		pcm3.with {
			setMember(new Member(1l))
			setCoupon(new ParkingCoupon(3))
			setTotal(2)
		}
		
		and:"a parkingCouponServiceImpl"
		ParkingCouponServiceImpl i=new ParkingCouponServiceImpl()
		ParkingCouponRepository repository=Mock(ParkingCouponRepository)
		ParkingCouponMemberService parkingCouponMemberService=Mock(ParkingCouponMemberService )
		EntityManager em=Mock(EntityManager)
		ReflectionTestUtils.setField(i,"repository",repository)
		ReflectionTestUtils.setField(i,"parkingCouponMemberService",parkingCouponMemberService)
		ReflectionTestUtils.setField(i,"em",em)
		def lists=[pcm1,pcm2,pcm3]

		expect:""
		6==Member.findSumParkingCouponCount(lists)

		when:"member use 5 parkingCoupon"
		i.deleteResult(5,lists)
		
		then:""
		[pcm1,pcm2,pcm3]==ParkingCouponMember.findWhichToDelete(5,lists)
		1 * parkingCouponMemberService.delete([pcm1,pcm2])
		pcm3.getTotal()==1
	}

	def "parkingCouponservice 1 deleteresults"(){
		given:"a parking coupon with total 1"
		ParkingCouponMember pcm1=new ParkingCouponMember();
		pcm1.with {
			setMember(new Member(1l))
			setCoupon(new ParkingCoupon(1))
			setTotal(1)
		}
		
		and:"a parking coupon with total 3"
		ParkingCouponMember pcm2=new ParkingCouponMember();
		pcm2.with {
			setMember(new Member(1l))
			setCoupon(new ParkingCoupon(2))
			setTotal(3)
		}

		and:"a parking coupon with total 2"
		ParkingCouponMember pcm3=new ParkingCouponMember();
		pcm3.with {
			setMember(new Member(1l))
			setCoupon(new ParkingCoupon(3))
			setTotal(2)
		}
		
		and:"a parkingCouponServiceImpl"
		ParkingCouponServiceImpl i=new ParkingCouponServiceImpl()
		ParkingCouponRepository repository=Mock(ParkingCouponRepository)
		ParkingCouponMemberService parkingCouponMemberService=Mock(ParkingCouponMemberService )
		EntityManager em=Mock(EntityManager)
		ReflectionTestUtils.setField(i,"repository",repository)
		ReflectionTestUtils.setField(i,"parkingCouponMemberService",parkingCouponMemberService)
		ReflectionTestUtils.setField(i,"em",em)
		def lists=[pcm1,pcm2,pcm3]

		when:"member use 5 parkingCoupon"
		[pcm1]==ParkingCouponMember.findWhichToDelete(1,lists)
		i.deleteResult(1,lists)
		
		then:""
		1 * em.merge(_)
		pcm1.getTotal()==0
	}
}