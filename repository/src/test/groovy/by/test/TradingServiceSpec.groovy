package by.test

import org.springframework.test.util.ReflectionTestUtils

import spock.lang.Specification

import com.by.form.ManualForm
import com.by.model.Member
import com.by.model.Shop
import com.by.model.Trading
import com.by.repository.TradingRepository
import com.by.service.MemberService
import com.by.service.ShopService
import com.by.service.impl.TradingServiceImpl

class TradingServiceSpec extends Specification {
	def "manual add socre"(){
		given:"a manual form"
		ManualForm form=new ManualForm()
		
		and:"a memberService"
		MemberService memberService=Stub(MemberService)
		memberService.findByName(_)>>new Member(1l)
		
		and: "a shop service"
		ShopService shopService=Stub(ShopService)
		shopService.findByName(_)>>new Shop(1)
		
		and:"a tradingRepository"
		TradingRepository repository=Stub(TradingRepository)
		repository.findByCode(_)>>new Trading(1l)
		
		and:"autowired to TradingService"
		TradingServiceImpl service=new TradingServiceImpl()
		ReflectionTestUtils.setField(service,"memberService",memberService)
		ReflectionTestUtils.setField(service,"shopService",shopService)
		ReflectionTestUtils.setField(service,"repository",repository)
		
		when:"tradingService manualadd"
		service.manualAddScore(form)
		
		then:""
		
	}
}
